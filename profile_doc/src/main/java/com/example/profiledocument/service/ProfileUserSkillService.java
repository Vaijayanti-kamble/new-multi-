package com.example.profiledocument.service;

import com.example.profiledocument.entity.ProfileUserSkill;
import com.example.profiledocument.utility.Utility;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class ProfileUserSkillService {

    private static final String COLLECTION_NAME = "profile_user_skills";
    private final Firestore firestore;

    public ProfileUserSkillService(Firestore firestore) {
        this.firestore = firestore;
    }

    // 🔹 Save ProfileUserSkill
    public String saveProfileUserSkill(ProfileUserSkill profileSkill) throws ExecutionException, InterruptedException {
        DocumentReference document = firestore.collection(COLLECTION_NAME).document();
        profileSkill.setUserSkillId(document.getId());  // Updated field name

        String formattedDate = Utility.getTime(LocalDateTime.now());
        profileSkill.setCreatedDate(formattedDate);
        profileSkill.setUpdatedDate(formattedDate);

        ApiFuture<WriteResult> writeResult = document.set(profileSkill);
        writeResult.get();
        return document.getId();
    }

    // 🔹 Get ProfileUserSkill by ID
    public ProfileUserSkill getProfileUserSkillById(String id) throws ExecutionException, InterruptedException {
        DocumentSnapshot document = firestore.collection(COLLECTION_NAME).document(id).get().get();
        if (document.exists()) {
            return document.toObject(ProfileUserSkill.class);
        }
        return null;
    }

    // 🔹 Update ProfileUserSkill
    public String updateProfileUserSkill(String id, List<String> skills) throws ExecutionException, InterruptedException {
        DocumentReference document = firestore.collection(COLLECTION_NAME).document(id);
        DocumentSnapshot snapshot = document.get().get();

        if (snapshot.exists()) {
            ProfileUserSkill existingProfile = snapshot.toObject(ProfileUserSkill.class);
            if (existingProfile != null) {
                existingProfile.setSkills(skills);
                existingProfile.setUpdatedDate(Utility.getTime(LocalDateTime.now()));

                ApiFuture<WriteResult> writeResult = document.set(existingProfile);
                writeResult.get();
                return "ProfileUserSkill updated successfully!";
            }
        }
        return "ProfileUserSkill not found!";
    }

    // 🔹 Delete ProfileUserSkill by ID
    public String deleteProfileUserSkill(String id) throws ExecutionException, InterruptedException {
        ApiFuture<WriteResult> writeResult = firestore.collection(COLLECTION_NAME).document(id).delete();
        writeResult.get();
        return "Document with ID " + id + " has been deleted.";
    }
}
