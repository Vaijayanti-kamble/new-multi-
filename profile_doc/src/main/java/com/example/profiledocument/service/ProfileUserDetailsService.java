package com.example.profiledocument.service;

import com.example.profiledocument.entity.ProfileUserDetails;
import com.example.profiledocument.utility.Utility;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;

@Service
public class ProfileUserDetailsService {

    private static final String COLLECTION_NAME = "user_profession_details";

    private final Firestore firestore;

    public ProfileUserDetailsService(Firestore firestore) {
        this.firestore = firestore;
    }

    public String saveUserProfessionDetails(ProfileUserDetails details) throws ExecutionException, InterruptedException {
        DocumentReference document = firestore.collection(COLLECTION_NAME).document();
        details.setUserDetailsId(document.getId()); // renamed here
        String formattedDate = Utility.getTime(LocalDateTime.now());
        details.setCreatedDate(formattedDate);
        details.setUpdatedDate(formattedDate);

        ApiFuture<WriteResult> writeResult = document.set(details);
        writeResult.get();
        return document.getId();
    }

    public ProfileUserDetails getUserProfessionDetailsById(String id) throws ExecutionException, InterruptedException {
        DocumentSnapshot document = firestore.collection(COLLECTION_NAME).document(id).get().get();
        if (document.exists()) {
            return document.toObject(ProfileUserDetails.class);
        }
        return null;
    }

    public String deleteUserProfessionDetails(String id) throws ExecutionException, InterruptedException {
        ApiFuture<WriteResult> writeResult = firestore.collection(COLLECTION_NAME).document(id).delete();
        writeResult.get();
        return "Document with ID " + id + " has been deleted.";
    }
}
