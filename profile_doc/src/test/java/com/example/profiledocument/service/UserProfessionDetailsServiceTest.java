package com.example.profiledocument.service;

import com.example.profiledocument.entity.UserProfessionDetails;
import com.example.profiledocument.utility.Utility;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserProfessionDetailsServiceTest {

    private static final String COLLECTION_NAME = "user_profession_details";

    @Mock
    private Firestore firestore;

    @Mock
    private CollectionReference collectionReference;

    @Mock
    private DocumentReference documentReference;

    @Mock
    private ApiFuture<WriteResult> writeResultFuture;

    @Mock
    private ApiFuture<DocumentSnapshot> documentSnapshotFuture;

    @InjectMocks
    private UserProfessionDetailsService service;

    private UserProfessionDetails userProfessionDetails;

    @BeforeEach
    void setUp() {
        userProfessionDetails = new UserProfessionDetails();
        userProfessionDetails.setFirstName("kirthi");
        userProfessionDetails.setMiddleName("v");
        userProfessionDetails.setLastName("chalageri");
        userProfessionDetails.setPhoneNo("1234567890");
        userProfessionDetails.setEmailId("kirthi@example.com");
        userProfessionDetails.setMainProfession("Software Engineer");
        userProfessionDetails.setOtherProfessions("Data Scientist");

        String formattedDate = Utility.getTime(LocalDateTime.now());
        userProfessionDetails.setCreatedDate(formattedDate);
        userProfessionDetails.setUpdatedDate(formattedDate);
    }

    @Test
    void testSaveUserProfessionDetails() throws ExecutionException, InterruptedException {
        // Mock Firestore collection and document
        when(firestore.collection(COLLECTION_NAME)).thenReturn(collectionReference);
        when(collectionReference.document()).thenReturn(documentReference);
        when(documentReference.getId()).thenReturn("test-id");
        when(documentReference.set(any(UserProfessionDetails.class))).thenReturn(writeResultFuture);
        when(writeResultFuture.get()).thenReturn(null);

        String savedId = service.saveUserProfessionDetails(userProfessionDetails);

        assertEquals("test-id", savedId);
        verify(documentReference, times(1)).set(any(UserProfessionDetails.class));
    }

    @Test
    void testGetUserProfessionDetailsById() throws ExecutionException, InterruptedException {
        when(firestore.collection(COLLECTION_NAME)).thenReturn(collectionReference);
        when(collectionReference.document("test-id")).thenReturn(documentReference);
        when(documentReference.get()).thenReturn(documentSnapshotFuture);

        DocumentSnapshot documentSnapshot = mock(DocumentSnapshot.class);
        when(documentSnapshotFuture.get()).thenReturn(documentSnapshot);
        when(documentSnapshot.exists()).thenReturn(true);
        when(documentSnapshot.toObject(UserProfessionDetails.class)).thenReturn(userProfessionDetails);

        UserProfessionDetails retrievedDetails = service.getUserProfessionDetailsById("test-id");

        assertNotNull(retrievedDetails);
        assertEquals("kirthi", retrievedDetails.getFirstName());
        assertEquals("Software Engineer", retrievedDetails.getMainProfession());
        verify(documentReference, times(1)).get();
    }

    @Test
    void testDeleteUserProfessionDetails() throws ExecutionException, InterruptedException {
        when(firestore.collection(COLLECTION_NAME)).thenReturn(collectionReference);
        when(collectionReference.document("test-id")).thenReturn(documentReference);
        when(documentReference.delete()).thenReturn(writeResultFuture);
        when(writeResultFuture.get()).thenReturn(null);

        String result = service.deleteUserProfessionDetails("test-id");

        assertEquals("Document with ID test-id has been deleted.", result);
        verify(documentReference, times(1)).delete();
    }
}
