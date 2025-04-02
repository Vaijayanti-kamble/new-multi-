package com.example.profiledocument.service;

import com.example.profiledocument.utility.Utility;
import com.example.profiledocument.entity.ProfileDocument;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.StorageClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;

@Service
public class ProfileDocumentService {
    private static final String BUCKET_NAME = "documentdoc";
    private static final String COLLECTION_NAME = "profile_doc";

    private final Firestore firestore;
    private final StorageClient storageClient;

    public ProfileDocumentService(Firestore firestore, StorageClient storageClient) {
        this.firestore = firestore;
        this.storageClient = storageClient;
    }

    public String addProfessionalDocument(MultipartFile file) throws Exception {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        InputStream fileInputStream = file.getInputStream();
        storageClient.bucket(BUCKET_NAME).create(fileName, fileInputStream, file.getContentType());

        String fileUrl = "https://storage.googleapis.com/" + BUCKET_NAME + "/" + fileName;
        String formattedDate = Utility.getTime(LocalDateTime.now());

        DocumentReference docRef = firestore.collection(COLLECTION_NAME).document();
        ProfileDocument document = new ProfileDocument();
        document.setId(docRef.getId());
        document.setProfileDocUrl(fileUrl);
        document.setCreatedDate(formattedDate);
        document.setUpdatedDate(formattedDate);

        docRef.set(document);

        return docRef.getId();
    }

    public String updateProfessionalDocument(String documentId, MultipartFile newFile) throws Exception {
        DocumentReference docRef = firestore.collection(COLLECTION_NAME).document(documentId);
        DocumentSnapshot snapshot = docRef.get().get();

        if (!snapshot.exists()) {
            throw new Exception("Document not found!");
        }

        ProfileDocument document = snapshot.toObject(ProfileDocument.class);
        String oldFileUrl = document.getProfileDocUrl();

        if (oldFileUrl != null && !oldFileUrl.isEmpty()) {
            String oldFileName = oldFileUrl.substring(oldFileUrl.lastIndexOf("/") + 1);
            storageClient.bucket(BUCKET_NAME).get(oldFileName).delete();
        }

        String newFileName = System.currentTimeMillis() + "_" + newFile.getOriginalFilename();
        InputStream newFileInputStream = newFile.getInputStream();
        storageClient.bucket(BUCKET_NAME).create(newFileName, newFileInputStream, newFile.getContentType());

        String newFileUrl = "https://storage.googleapis.com/" + BUCKET_NAME + "/" + newFileName;
        document.setProfileDocUrl(newFileUrl);
        document.setUpdatedDate(Utility.getTime(LocalDateTime.now()));
        docRef.set(document);

        return "File updated successfully, new URL: " + newFileUrl;
    }

    public ProfileDocument getProfessionalDocument(String id) throws ExecutionException, InterruptedException {
        DocumentSnapshot snapshot = firestore.collection(COLLECTION_NAME).document(id).get().get();

        if (snapshot.exists()) {
            return snapshot.toObject(ProfileDocument.class);
        }
        return null;
    }

    public String deleteProfessionalDocument(String documentId) throws Exception {
        DocumentReference docRef = firestore.collection(COLLECTION_NAME).document(documentId);
        DocumentSnapshot snapshot = docRef.get().get();

        if (!snapshot.exists()) {
            throw new Exception("Document not found!");
        }

        ProfileDocument document = snapshot.toObject(ProfileDocument.class);
        String fileUrl = document.getProfileDocUrl();

        if (fileUrl != null && !fileUrl.isEmpty()) {
            String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
            storageClient.bucket(BUCKET_NAME).get(fileName).delete();
        }

        docRef.delete();
        return "File deleted successfully.";
    }
}
