package com.example.profiledocument.service;

import com.example.profiledocument.entity.ProfileSkillVideo;
import com.example.profiledocument.utility.Utility;
import com.google.cloud.firestore.*;
import com.google.cloud.storage.Blob;
import com.google.firebase.cloud.StorageClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;

@Service
public class ProfileSkillVideoService {
    private static final String BUCKET_NAME = "documentdoc";
    private static final String COLLECTION_NAME = "profile_and_skills";
    private static final String FOLDER_NAME = "profile_videos/";

    private final Firestore firestore;
    private final StorageClient storageClient;

    public ProfileSkillVideoService(Firestore firestore, StorageClient storageClient) {
        this.firestore = firestore;
        this.storageClient = storageClient;
    }

    public String addVideo(MultipartFile file) throws Exception {
        String fileName = FOLDER_NAME + System.currentTimeMillis() + "_" + file.getOriginalFilename();
        InputStream fileInputStream = file.getInputStream();

        Blob blob = storageClient.bucket(BUCKET_NAME).create(fileName, fileInputStream, file.getContentType());
        String fileUrl = blob.getMediaLink(); // Using getMediaLink() to get direct download link

        String formattedDate = Utility.getTime(LocalDateTime.now());

        DocumentReference docRef = firestore.collection(COLLECTION_NAME).document();
        ProfileSkillVideo video = new ProfileSkillVideo();
        video.setId(docRef.getId());
        video.setProfileSkillVideoUrl(fileUrl);
        video.setCreatedDate(formattedDate);
        video.setUpdatedDate(formattedDate);

        docRef.set(video);

        return docRef.getId();
    }

    public String updateVideo(String videoId, MultipartFile newFile) throws Exception {
        DocumentReference docRef = firestore.collection(COLLECTION_NAME).document(videoId);
        DocumentSnapshot snapshot = docRef.get().get();

        if (!snapshot.exists()) {
            throw new Exception("Video not found!");
        }

        ProfileSkillVideo video = snapshot.toObject(ProfileSkillVideo.class);
        String oldFileUrl = video.getProfileSkillVideoUrl();

        if (oldFileUrl != null && !oldFileUrl.isEmpty()) {
            String oldFileName = oldFileUrl.substring(oldFileUrl.lastIndexOf("/") + 1);
            Blob oldBlob = storageClient.bucket(BUCKET_NAME).get(FOLDER_NAME + oldFileName);
            if (oldBlob != null) {
                oldBlob.delete();
            }
        }

        String newFileName = FOLDER_NAME + System.currentTimeMillis() + "_" + newFile.getOriginalFilename();
        InputStream newFileInputStream = newFile.getInputStream();

        Blob newBlob = storageClient.bucket(BUCKET_NAME).create(newFileName, newFileInputStream, newFile.getContentType());
        String newFileUrl = newBlob.getMediaLink(); // Using getMediaLink()

        video.setProfileSkillVideoUrl(newFileUrl);
        video.setUpdatedDate(Utility.getTime(LocalDateTime.now()));
        docRef.set(video);

        return "Video updated successfully, new URL: " + newFileUrl;
    }

    public ProfileSkillVideo getVideo(String id) throws ExecutionException, InterruptedException {
        DocumentSnapshot snapshot = firestore.collection(COLLECTION_NAME).document(id).get().get();

        if (snapshot.exists()) {
            return snapshot.toObject(ProfileSkillVideo.class);
        }
        return null;
    }

    public String deleteVideo(String videoId) throws Exception {
        DocumentReference docRef = firestore.collection(COLLECTION_NAME).document(videoId);
        DocumentSnapshot snapshot = docRef.get().get();

        if (!snapshot.exists()) {
            throw new Exception("Video not found!");
        }

        ProfileSkillVideo video = snapshot.toObject(ProfileSkillVideo.class);
        String fileUrl = video.getProfileSkillVideoUrl();

        if (fileUrl != null && !fileUrl.isEmpty()) {
            String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
            Blob blob = storageClient.bucket(BUCKET_NAME).get(FOLDER_NAME + fileName);
            if (blob != null) {
                blob.delete();
            }
        }

        docRef.delete();
        return "Video deleted successfully.";
    }
}
