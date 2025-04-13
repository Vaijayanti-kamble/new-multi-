package com.example.profiledocument.service;

import com.example.profiledocument.utility.Utility;
import com.example.profiledocument.entity.ProfileResumeDocument;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.StorageClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;

@Service
public class ProfileResumeDocumentService {
    private static final String BUCKET_NAME = "documentdoct";
    private static final String COLLECTION_NAME = "profile_doc";

    private final Firestore firestore;
    private final StorageClient storageClient;

    public ProfileResumeDocumentService(Firestore firestore, StorageClient storageClient) {
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
        ProfileResumeDocument document = new ProfileResumeDocument();
        document.setResumeDocumentId(docRef.getId());
        document.setProfileDocUrl(fileUrl);
        document.setCreatedDate(formattedDate);
        document.setUpdatedDate(formattedDate);

        docRef.set(document);

        return docRef.getId();
    }

    public String updateProfessionalDocument(String resumeDocumentId, MultipartFile newFile) throws Exception {
        DocumentReference docRef = firestore.collection(COLLECTION_NAME).document(resumeDocumentId);
        DocumentSnapshot snapshot = docRef.get().get();

        if (!snapshot.exists()) {
            throw new Exception("Document not found!");
        }

        ProfileResumeDocument document = snapshot.toObject(ProfileResumeDocument.class);
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

    public ProfileResumeDocument getProfessionalDocument(String resumeDocumentId) throws ExecutionException, InterruptedException {
        DocumentSnapshot snapshot = firestore.collection(COLLECTION_NAME).document(resumeDocumentId).get().get();

        if (snapshot.exists()) {
            return snapshot.toObject(ProfileResumeDocument.class);
        }
        return null;
    }

    public String deleteProfessionalDocument(String resumeDocumentId) throws Exception {
        DocumentReference docRef = firestore.collection(COLLECTION_NAME).document(resumeDocumentId);
        DocumentSnapshot snapshot = docRef.get().get();

        if (!snapshot.exists()) {
            throw new Exception("Document not found!");
        }

        ProfileResumeDocument document = snapshot.toObject(ProfileResumeDocument.class);
        String fileUrl = document.getProfileDocUrl();

        if (fileUrl != null && !fileUrl.isEmpty()) {
            String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
            storageClient.bucket(BUCKET_NAME).get(fileName).delete();
        }

        docRef.delete();
        return "File deleted successfully.";
    }
}
