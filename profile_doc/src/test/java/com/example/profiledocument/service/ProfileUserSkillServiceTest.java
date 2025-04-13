package com.example.profiledocument.service;

import com.example.profiledocument.entity.ProfileUserSkill;
import com.example.profiledocument.utility.Utility;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProfileUserSkillServiceTest {

    @Mock
    private Firestore firestore;

    @Mock
    private CollectionReference collectionReference;

    @Mock
    private DocumentReference documentReference;

    @Mock
    private DocumentSnapshot documentSnapshot;

    @Mock
    private ApiFuture<WriteResult> writeResultApiFuture;

    @InjectMocks
    private ProfileUserSkillService profileUserSkillService;

    private ProfileUserSkill profileUserSkill;

    @BeforeEach
    void setUp() {
        profileUserSkill = new ProfileUserSkill();
        profileUserSkill.setUserSkillId("12345");
        profileUserSkill.setProfileUserId("user123");
        profileUserSkill.setCreatedDate(Utility.getTime(LocalDateTime.now()));
        profileUserSkill.setUpdatedDate(Utility.getTime(LocalDateTime.now()));
        profileUserSkill.setSkills(Arrays.asList("Java", "Spring Boot", "Firebase"));
    }

    // 🔹 Test Save ProfileUserSkill
    @Test
    void testSaveProfileUserSkill() throws ExecutionException, InterruptedException {
        when(firestore.collection("profile_user_skills")).thenReturn(collectionReference);
        when(collectionReference.document()).thenReturn(documentReference);
        when(documentReference.getId()).thenReturn("12345");
        when(documentReference.set(any(ProfileUserSkill.class))).thenReturn(writeResultApiFuture);
        when(writeResultApiFuture.get()).thenReturn(null); // Simulating Firestore write operation

        String result = profileUserSkillService.saveProfileUserSkill(profileUserSkill);
        assertEquals("12345", result);
    }

    // 🔹 Test Get ProfileUserSkill by ID (Found)
    @Test
    void testGetProfileUserSkillById_Found() throws ExecutionException, InterruptedException {
        when(firestore.collection("profile_user_skills")).thenReturn(collectionReference);
        when(collectionReference.document("12345")).thenReturn(documentReference);
        when(documentReference.get()).thenReturn(Mockito.mock(ApiFuture.class));
        when(documentReference.get().get()).thenReturn(documentSnapshot);
        when(documentSnapshot.exists()).thenReturn(true);
        when(documentSnapshot.toObject(ProfileUserSkill.class)).thenReturn(profileUserSkill);

        ProfileUserSkill result = profileUserSkillService.getProfileUserSkillById("12345");
        assertNotNull(result);
        assertEquals("12345", result.getUserSkillId());
    }

    // 🔹 Test Get ProfileUserSkill by ID (Not Found)
    @Test
    void testGetProfileUserSkillById_NotFound() throws ExecutionException, InterruptedException {
        when(firestore.collection("profile_user_skills")).thenReturn(collectionReference);
        when(collectionReference.document("12345")).thenReturn(documentReference);
        when(documentReference.get()).thenReturn(Mockito.mock(ApiFuture.class));
        when(documentReference.get().get()).thenReturn(documentSnapshot);
        when(documentSnapshot.exists()).thenReturn(false);

        ProfileUserSkill result = profileUserSkillService.getProfileUserSkillById("12345");
        assertNull(result);
    }

    // 🔹 Test Update ProfileUserSkill
    @Test
    void testUpdateProfileUserSkill() throws ExecutionException, InterruptedException {
        List<String> updatedSkills = Arrays.asList("AWS", "Docker", "Kubernetes");

        when(firestore.collection("profile_user_skills")).thenReturn(collectionReference);
        when(collectionReference.document("12345")).thenReturn(documentReference);
        when(documentReference.get()).thenReturn(Mockito.mock(ApiFuture.class));
        when(documentReference.get().get()).thenReturn(documentSnapshot);
        when(documentSnapshot.exists()).thenReturn(true);
        when(documentSnapshot.toObject(ProfileUserSkill.class)).thenReturn(profileUserSkill);
        when(documentReference.set(any(ProfileUserSkill.class))).thenReturn(writeResultApiFuture);
        when(writeResultApiFuture.get()).thenReturn(null);

        String result = profileUserSkillService.updateProfileUserSkill("12345", updatedSkills);
        assertEquals("ProfileUserSkill updated successfully!", result);
        assertEquals(updatedSkills, profileUserSkill.getSkills());
    }

    // 🔹 Test Update ProfileUserSkill (Not Found)
    @Test
    void testUpdateProfileUserSkill_NotFound() throws ExecutionException, InterruptedException {
        when(firestore.collection("profile_user_skills")).thenReturn(collectionReference);
        when(collectionReference.document("12345")).thenReturn(documentReference);
        when(documentReference.get()).thenReturn(Mockito.mock(ApiFuture.class));
        when(documentReference.get().get()).thenReturn(documentSnapshot);
        when(documentSnapshot.exists()).thenReturn(false);

        String result = profileUserSkillService.updateProfileUserSkill("12345", Arrays.asList("React", "Node.js"));
        assertEquals("ProfileUserSkill not found!", result);
    }

    // 🔹 Test Delete ProfileUserSkill
    @Test
    void testDeleteProfileUserSkill() throws ExecutionException, InterruptedException {
        when(firestore.collection("profile_user_skills")).thenReturn(collectionReference);
        when(collectionReference.document("12345")).thenReturn(documentReference);
        when(documentReference.delete()).thenReturn(writeResultApiFuture);
        when(writeResultApiFuture.get()).thenReturn(null);

        String result = profileUserSkillService.deleteProfileUserSkill("12345");
        assertEquals("Document with ID 12345 has been deleted.", result);
    }
}
