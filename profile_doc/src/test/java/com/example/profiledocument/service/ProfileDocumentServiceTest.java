package com.example.profiledocument.service;

import com.example.profiledocument.entity.ProfileResumeDocument;
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
class ProfileDocumentServiceTest {

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
    private ProfileResumeDocumentService service;

    private MultipartFile mockFile;

    @BeforeEach
    void setUp() {
        mockFile = new MockMultipartFile(
                "file",
                "test-document.pdf",
                "application/pdf",
                "Dummy Document Content".getBytes()

        );
    }

    @Test
    void testUploadDocument() throws Exception {
        lenient().when(firestore.collection(anyString())).thenReturn(collectionReference);
        lenient().when(collectionReference.document()).thenReturn(documentReference);
        lenient().when(documentReference.getId()).thenReturn("test-id");

        lenient().when(storageClient.bucket(anyString())).thenReturn(bucket);
        lenient().when(bucket.create(anyString(), any(InputStream.class), anyString())).thenReturn(blob);
        lenient().when(blob.getMediaLink()).thenReturn("https://storage.googleapis.com/test-bucket/test-document.pdf");

        try (MockedStatic<Utility> mockedUtility = mockStatic(Utility.class)) {
            mockedUtility.when(() -> Utility.getTime(any())).thenReturn("2025-03-20");

            // Run the actual service method
            String result = service.addProfessionalDocument(mockFile);

            // Validate the returned ID
            assertEquals("test-id", result);
        }

        // Verify interactions only for methods actually called
        verify(collectionReference).document();
        verify(documentReference).set(any(ProfileResumeDocument.class));
        verify(bucket).create(anyString(), any(InputStream.class), anyString());

    }


    @Test
    void testUpdateDocument() throws Exception {
        String documentId = "test-id";
        ProfileResumeDocument document = new ProfileResumeDocument();
        document.setProfileDocUrl("https://storage.googleapis.com/documentdoc/old-document.pdf");

        when(firestore.collection(anyString())).thenReturn(collectionReference);
        when(collectionReference.document(eq(documentId))).thenReturn(documentReference);
        when(documentReference.get()).thenReturn(ApiFutures.immediateFuture(documentSnapshot));
        when(documentSnapshot.exists()).thenReturn(true);
        when(documentSnapshot.toObject(ProfileResumeDocument.class)).thenReturn(document);

        when(storageClient.bucket(anyString())).thenReturn(bucket);
        when(bucket.get(anyString())).thenReturn(blob);
        when(blob.delete()).thenReturn(true);

        when(bucket.create(anyString(), any(InputStream.class), anyString())).thenReturn(blob);

        long fixedTimestamp = 1742893930278L; // Fixed timestamp for testing
        String expectedNewFileName = fixedTimestamp + "_test-document.pdf";
        String expectedNewFileUrl = "https://storage.googleapis.com/documentdoc/" + expectedNewFileName;

        try (MockedStatic<Utility> mockedUtility = mockStatic(Utility.class)) {
            mockedUtility.when(() -> Utility.getTime(any())).thenReturn(String.valueOf(fixedTimestamp));

            // Call the service method
            String result = service.updateProfessionalDocument(documentId, mockFile);

            // Debug output
            System.out.println("Service Response: " + result);

            // Validate that the response contains the correct new URL using regex for timestamp
            assertTrue(result.matches("File updated successfully, new URL: https://storage.googleapis.com/documentdoc/\\d+_test-document.pdf"),
                    "The response should contain a dynamically generated file URL.");
        }

        // Ensure interactions with mocks
        verify(documentReference).set(any(ProfileResumeDocument.class));
        verify(blob).delete();
        verify(bucket).create(anyString(), any(InputStream.class), anyString());
    }


    @Test
    void testGetDocument() throws Exception {
        String documentId = "test-id";
        ProfileResumeDocument document = new ProfileResumeDocument();
        document.setResumeDocumentId(documentId);
        document.setProfileDocUrl("https://storage.googleapis.com/test-bucket/test-document.pdf");

        when(firestore.collection(any())).thenReturn(collectionReference);
        when(collectionReference.document(documentId)).thenReturn(documentReference);
        when(documentReference.get()).thenReturn(ApiFutures.immediateFuture(documentSnapshot));
        when(documentSnapshot.exists()).thenReturn(true);
        when(documentSnapshot.toObject(ProfileResumeDocument.class)).thenReturn(document);

        ProfileResumeDocument result = service.getProfessionalDocument(documentId);

        assertNotNull(result);
        assertEquals(documentId, result.getResumeDocumentId());
        assertEquals("https://storage.googleapis.com/test-bucket/test-document.pdf", result.getProfileDocUrl());
    }

    @Test
    void testDeleteDocument() throws Exception {
        String documentId = "test-id";
        ProfileResumeDocument document = new ProfileResumeDocument();
        document.setProfileDocUrl("https://storage.googleapis.com/test-bucket/old-document.pdf");

        when(firestore.collection(any())).thenReturn(collectionReference);
        when(collectionReference.document(documentId)).thenReturn(documentReference);
        when(documentReference.get()).thenReturn(ApiFutures.immediateFuture(documentSnapshot));
        when(documentSnapshot.exists()).thenReturn(true);
        when(documentSnapshot.toObject(ProfileResumeDocument.class)).thenReturn(document);

        when(storageClient.bucket(any())).thenReturn(bucket);
        when(bucket.get(any(String.class))).thenReturn(blob);
        when(blob.delete()).thenReturn(true);

        String result = service.deleteProfessionalDocument(documentId);
        assertEquals("File deleted successfully.", result);

        verify(documentReference).delete();
        verify(blob).delete();
    }
}
