package com.example.profiledocument.service;

import com.example.profiledocument.dto.SearchRequest;
import com.example.profiledocument.dto.SearchResult;
import com.example.profiledocument.entity.ProfileUserSkill;
import com.example.profiledocument.entity.UserAddress;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class SearchService {

    private static final String PROFILE_USER_SKILL_COLLECTION = "profile_user_skills";
    private static final String USER_ADDRESS_COLLECTION = "user_address";

    private Firestore getFirestore() {
        return FirestoreClient.getFirestore();
    }

    // 🔍 Search for users based on skill and location
    public List<SearchResult> searchUsers(SearchRequest request) throws ExecutionException, InterruptedException {
        Firestore db = getFirestore();

        // Step 1️⃣: Fetch all users with the given skill
        List<ProfileUserSkill> matchingSkills = getUsersBySkill(request.getSkill());

        // Step 2️⃣: Fetch all users with the given location
        List<UserAddress> matchingAddresses = getUsersByLocation(request.getLocation());

        // Step 3️⃣: Combine the results
        return matchUsers(matchingSkills, matchingAddresses);
    }

    // ✅ Fetch users who have a specific skill
    private List<ProfileUserSkill> getUsersBySkill(String skill) throws ExecutionException, InterruptedException {
        Firestore db = getFirestore();
        CollectionReference collection = db.collection(PROFILE_USER_SKILL_COLLECTION);
        ApiFuture<QuerySnapshot> query = collection.get();
        QuerySnapshot snapshot = query.get();

        List<ProfileUserSkill> result = new ArrayList<>();
        for (DocumentSnapshot document : snapshot.getDocuments()) {
            ProfileUserSkill profile = document.toObject(ProfileUserSkill.class);
            if (profile != null && profile.getSkills() != null && profile.getSkills().contains(skill)) {
                result.add(profile);
            }
        }
        return result;
    }

    // ✅ Fetch users who have a specific location
    private List<UserAddress> getUsersByLocation(String location) throws ExecutionException, InterruptedException {
        Firestore db = getFirestore();
        CollectionReference collection = db.collection(USER_ADDRESS_COLLECTION);
        ApiFuture<QuerySnapshot> query = collection.get();
        QuerySnapshot snapshot = query.get();

        List<UserAddress> result = new ArrayList<>();
        for (DocumentSnapshot document : snapshot.getDocuments()) {
            UserAddress address = document.toObject(UserAddress.class);
            if (address != null && address.getDistrict() != null && address.getDistrict().equalsIgnoreCase(location)) {
                result.add(address);
            }
        }
        return result;
    }

    // 🔗 Combine ProfileUserSkill and UserAddress based on profileUserId
    private List<SearchResult> matchUsers(List<ProfileUserSkill> skills, List<UserAddress> addresses) {
        return skills.stream()
                .flatMap(skill -> addresses.stream()
                        .filter(address -> address.getProfileUserId().equals(skill.getProfileUserId()))
                        .map(address -> new SearchResult(skill, address)))
                .collect(Collectors.toList());
    }
}
