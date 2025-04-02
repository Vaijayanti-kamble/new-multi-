package com.example.profiledocument.service;

import com.example.profiledocument.entity.ProfileSkillVideo;
import com.example.profiledocument.utility.Utility;
import com.google.api.core.ApiFutures;
import com.google.cloud.firestore.*;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProfileSkillVideoServiceTest {

    @Mock
    private Firestore firestore;

    @Mock
    private StorageClient storageClient;

    @Mock
    private Bucket bucket;

    @Mock
    private Blob blob;

    @Mock
    private CollectionReference collectionReference;

    @Mock
    private DocumentReference documentReference;

    @Mock
    private DocumentSnapshot documentSnapshot;

    @InjectMocks
    private ProfileSkillVideoService service;

    private MultipartFile mockFile;

    @BeforeEach
    void setUp() {
        // Mock file setup
        mockFile = new MockMultipartFile(
                "file",
                "test-video.mp4",
                "video/mp4",
                "Dummy Video Content".getBytes()
        );
    }

    // ==================== TEST ADD VIDEO ====================
    @Test
    void testAddVideo() throws Exception {
        // ✅ Correctly mock Firestore collection and document
        when(firestore.collection(anyString())).thenReturn(collectionReference);
        when(collectionReference.document()).thenReturn(documentReference);
        when(documentReference.getId()).thenReturn("test-id");

        // ✅ Correctly mock Blob Storage
        when(storageClient.bucket(anyString())).thenReturn(bucket);
        when(bucket.create(anyString(), any(InputStream.class), anyString())).thenReturn(blob);
        when(blob.getMediaLink()).thenReturn("https://storage.googleapis.com/test-bucket/test-video.mp4");

        // ✅ Mock static Utility method
        try (MockedStatic<Utility> mockedUtility = mockStatic(Utility.class)) {
            mockedUtility.when(() -> Utility.getTime(any())).thenReturn("2025-03-20");

            // 🔥 Call the service method
            String result = service.addVideo(mockFile);

            // ✅ Assert expected result
            assertEquals("test-id", result);
        }

        // ✅ Verify interactions
        verify(collectionReference).document();
        verify(documentReference).set(any(ProfileSkillVideo.class));
        verify(bucket).create(anyString(), any(InputStream.class), anyString());
    }

    // ==================== TEST UPDATE VIDEO ====================
    @Test
    void testUpdateVideo() throws Exception {
        String videoId = "test-id";
        ProfileSkillVideo video = new ProfileSkillVideo();
        video.setProfileSkillVideoUrl("https://storage.googleapis.com/test-bucket/old-video.mp4");

        // ✅ Correctly mocking Firestore collection and document
        when(firestore.collection(anyString())).thenReturn(collectionReference);
        when(collectionReference.document(eq(videoId))).thenReturn(documentReference);
        when(documentReference.get()).thenReturn(ApiFutures.immediateFuture(documentSnapshot));
        when(documentSnapshot.exists()).thenReturn(true);
        when(documentSnapshot.toObject(ProfileSkillVideo.class)).thenReturn(video);

        // ✅ Correctly mocking old Blob for deletion
        when(storageClient.bucket(anyString())).thenReturn(bucket);
        when(bucket.get(anyString())).thenReturn(blob);
        when(blob.delete()).thenReturn(true);

        // ✅ Correctly mocking new Blob creation
        when(bucket.create(anyString(), any(InputStream.class), anyString())).thenReturn(blob);
        when(blob.getMediaLink()).thenReturn("https://storage.googleapis.com/test-bucket/new-video.mp4");

        // ✅ Using a static mock for Utility class
        try (MockedStatic<Utility> mockedUtility = mockStatic(Utility.class)) {
            mockedUtility.when(() -> Utility.getTime(any())).thenReturn("2025-03-20");

            // 🔥 Call the service method
            String result = service.updateVideo(videoId, mockFile);

            // ✅ Assert expected result
            assertEquals("Video updated successfully, new URL: https://storage.googleapis.com/test-bucket/new-video.mp4", result);
        }

        // ✅ Verify interactions
        verify(documentReference).set(any(ProfileSkillVideo.class));
        verify(blob).delete();
        verify(bucket).create(anyString(), any(InputStream.class), anyString());
    }

    // ==================== TEST GET VIDEO ====================
    @Test
    void testGetVideo() throws Exception {
        String videoId = "test-id";
        ProfileSkillVideo video = new ProfileSkillVideo();
        video.setId(videoId);
        video.setProfileSkillVideoUrl("https://storage.googleapis.com/test-bucket/test-video.mp4");

        // Mock Firestore collection and document
        when(firestore.collection(any())).thenReturn(collectionReference);
        when(collectionReference.document(videoId)).thenReturn(documentReference);
        when(documentReference.get()).thenReturn(ApiFutures.immediateFuture(documentSnapshot));
        when(documentSnapshot.exists()).thenReturn(true);
        when(documentSnapshot.toObject(ProfileSkillVideo.class)).thenReturn(video);

        // Call the service method
        ProfileSkillVideo result = service.getVideo(videoId);

        // Assert
        assertNotNull(result);
        assertEquals(videoId, result.getId());
        assertEquals("https://storage.googleapis.com/test-bucket/test-video.mp4", result.getProfileSkillVideoUrl());
    }

    // ==================== TEST DELETE VIDEO ====================
    @Test
    void testDeleteVideo() throws Exception {
        String videoId = "test-id";
        ProfileSkillVideo video = new ProfileSkillVideo();
        video.setProfileSkillVideoUrl("https://storage.googleapis.com/test-bucket/old-video.mp4");

        // Mock Firestore collection and document
        when(firestore.collection(any())).thenReturn(collectionReference);
        when(collectionReference.document(videoId)).thenReturn(documentReference);
        when(documentReference.get()).thenReturn(ApiFutures.immediateFuture(documentSnapshot));
        when(documentSnapshot.exists()).thenReturn(true);
        when(documentSnapshot.toObject(ProfileSkillVideo.class)).thenReturn(video);

        // Mock Blob Storage
        when(storageClient.bucket(any())).thenReturn(bucket);
        when(bucket.get(any(String.class))).thenReturn(blob);
        when(blob.delete()).thenReturn(true);

        // Call the service method
        String result = service.deleteVideo(videoId);

        // Assert
        assertEquals("Video deleted successfully.", result);

        // Verify interactions
        verify(documentReference).delete();
        verify(blob).delete();
    }
}
