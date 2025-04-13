package com.example.profiledocument.entity;

import com.google.cloud.firestore.annotation.PropertyName;

public class ProfileResumeDocument {
    private String resumeDocumentId;
    private String profileDocUrl;
    private String createdDate;
    private String updatedDate;

    @PropertyName("resume_document_id")
    public String getResumeDocumentId() {
        return resumeDocumentId;
    }

    @PropertyName("resume_document_id")
    public void setResumeDocumentId(String resumeDocumentId) {
        this.resumeDocumentId = resumeDocumentId;
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
