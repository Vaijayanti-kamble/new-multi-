package com.example.profiledocument.entity;

import com.google.cloud.firestore.annotation.PropertyName;

public class ProfileResumeVideo {
    @PropertyName("resume_video_id")
    private String resumeVideoId;

    @PropertyName("profile_skill_video_url")
    private String profileSkillVideoUrl;

    @PropertyName("created_date")
    private String createdDate;

    @PropertyName("updated_date")
    private String updatedDate;

    // Getters and Setters
    @PropertyName("resume_video_id")
    public String getResumeVideoId() {
        return resumeVideoId;
    }

    @PropertyName("resume_video_id")
    public void setResumeVideoId(String resumeVideoId) {
        this.resumeVideoId = resumeVideoId;
    }

    @PropertyName("profile_skill_video_url")
    public String getProfileSkillVideoUrl() {
        return profileSkillVideoUrl;
    }

    @PropertyName("profile_skill_video_url")
    public void setProfileSkillVideoUrl(String profileSkillVideoUrl) {
        this.profileSkillVideoUrl = profileSkillVideoUrl;
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
