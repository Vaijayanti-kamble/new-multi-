package com.example.profiledocument.service;

import com.example.profiledocument.entity.ProfileAddress;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class ProfileUserAddressService {

    private static final String COLLECTION_NAME = "profile_address";

    private Firestore getFirestore() {
        return FirestoreClient.getFirestore();
    }

    public String saveOrUpdateAddress(ProfileAddress profileAddress) throws ExecutionException, InterruptedException {
        Firestore db = getFirestore();

        // Create a new document with auto-generated ID
        DocumentReference docRef = db.collection(COLLECTION_NAME).document();

        // Set the generated ID into profileUserId field
        String generatedId = docRef.getId();
        profileAddress.setProfileUserId(generatedId);

        // Save the object
        ApiFuture<WriteResult> future = docRef.set(profileAddress);

        // Return the generated ID to the client
        return "Saved successfully with ID: " + generatedId;
    }


    public ProfileAddress getAddress(String profileUserId) throws ExecutionException, InterruptedException {
        Firestore db = getFirestore();
        DocumentReference docRef = db.collection(COLLECTION_NAME).document(profileUserId);
        DocumentSnapshot snapshot = docRef.get().get();
        return snapshot.exists() ? snapshot.toObject(ProfileAddress.class) : null;
    }

    public String deleteAddress(String profileUserId) throws ExecutionException, InterruptedException {
        Firestore db = getFirestore();
        DocumentReference docRef = db.collection(COLLECTION_NAME).document(profileUserId);
        ApiFuture<WriteResult> future = docRef.delete();
        return "Deleted at: " + future.get().getUpdateTime();
    }
}
