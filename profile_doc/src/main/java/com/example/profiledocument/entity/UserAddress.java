package com.example.profiledocument.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.PropertyName;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAddress {

    @DocumentId
    private String addressId; // Firestore document ID

    @PropertyName("profile_user_id")
    private String profileUserId;

    @PropertyName("house_no")
    private String houseNo;

    @PropertyName("road_name")
    private String roadName;

    @PropertyName("area")
    private String area;

    @PropertyName("district")
    private String district;

    @PropertyName("state")
    private String state;

    @PropertyName("pincode")
    private String pincode;

    @PropertyName("created_date")
    private String createdDate;

    @PropertyName("updated_date")
    private String updatedDate;

    @PropertyName("is_present_address")
    private boolean isPresentAddress;

    @PropertyName("is_permanent_address")
    private boolean isPermanentAddress;
}

