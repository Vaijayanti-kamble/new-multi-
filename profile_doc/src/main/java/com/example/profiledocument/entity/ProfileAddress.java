package com.example.profiledocument.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.cloud.firestore.annotation.PropertyName;

public class ProfileAddress {

    @JsonProperty("profile_user_id")  // Maps JSON -> Java
    @PropertyName("profile_user_id")  // Maps Java -> Firestore
    private String profileUserId;

    @JsonProperty("present_address")
    @PropertyName("present_address")
    private ProfileUserAddress presentAddress;

    @JsonProperty("permanent_address")
    @PropertyName("permanent_address")
    private ProfileUserAddress permanentAddress;

    public ProfileAddress() {}

    public String getProfileUserId() {
        return profileUserId;
    }

    public void setProfileUserId(String profileUserId) {
        this.profileUserId = profileUserId;
    }

    public ProfileUserAddress getPresentAddress() {
        return presentAddress;
    }

    public void setPresentAddress(ProfileUserAddress presentAddress) {
        this.presentAddress = presentAddress;
    }

    public ProfileUserAddress getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(ProfileUserAddress permanentAddress) {
        this.permanentAddress = permanentAddress;
    }
}
