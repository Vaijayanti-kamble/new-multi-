package com.example.profiledocument.service;

import com.example.profiledocument.entity.UserAddress;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class UserAddressService {

    private static final String COLLECTION_NAME = "user_address";

    private Firestore getFirestore() {
        return FirestoreClient.getFirestore();
    }

    // ✅ Add a new address
    public String addAddress(UserAddress address) throws ExecutionException, InterruptedException {
        Firestore db = getFirestore();
        DocumentReference docRef = db.collection(COLLECTION_NAME).document();
        address.setAddressId(docRef.getId()); // Set Firestore-generated ID
        ApiFuture<WriteResult> future = docRef.set(address);
        return "Address added successfully. Timestamp: " + future.get().getUpdateTime();
    }

    // ✅ Update an existing address
    public String updateAddress(String addressId, UserAddress address) throws ExecutionException, InterruptedException {
        Firestore db = getFirestore();
        DocumentReference docRef = db.collection(COLLECTION_NAME).document(addressId);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot snapshot = future.get();

        if (!snapshot.exists()) {
            return "Address not found!";
        }

        ApiFuture<WriteResult> updateFuture = docRef.set(address);
        return "Address updated successfully. Timestamp: " + updateFuture.get().getUpdateTime();
    }

    // ✅ Get address by ID
    public UserAddress getAddressById(String addressId) throws ExecutionException, InterruptedException {
        Firestore db = getFirestore();
        DocumentReference docRef = db.collection(COLLECTION_NAME).document(addressId);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot snapshot = future.get();

        if (!snapshot.exists()) {
            return null;
        }
        return snapshot.toObject(UserAddress.class);
    }

    // ✅ Delete address by ID
    public String deleteAddress(String addressId) throws ExecutionException, InterruptedException {
        Firestore db = getFirestore();
        DocumentReference docRef = db.collection(COLLECTION_NAME).document(addressId);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot snapshot = future.get();

        if (!snapshot.exists()) {
            return "Address not found!";
        }

        ApiFuture<WriteResult> deleteFuture = docRef.delete();
        return "Address deleted successfully. Timestamp: " + deleteFuture.get().getUpdateTime();
    }
}
