package com.example.profiledocument.entity;

import com.google.cloud.firestore.annotation.PropertyName;

public class ProfileDocument {
    private String id;
    private String profileDocUrl;
    private String createdDate;
    private String updatedDate;

    // Getters and Setters with @PropertyName for Firestore mapping
    @PropertyName("id")
    public String getId() {
        return id;
    }

    @PropertyName("id")
    public void setId(String id) {
        this.id = id;
    }

    @PropertyName("profile_doc_url")
    public String getProfileDocUrl() {
        return profileDocUrl;
    }

    @PropertyName("profile_doc_url")
    public void setProfileDocUrl(String profileDocUrl) {
        this.profileDocUrl = profileDocUrl;
    }

    @PropertyName("created_date")
    public String getCreatedDate() {
        return createdDate;
    }

    @PropertyName("created_date")
    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    @PropertyName("updated_date")
    public String getUpdatedDate() {
        return updatedDate;
    }

    @PropertyName("updated_date")
    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }
}
