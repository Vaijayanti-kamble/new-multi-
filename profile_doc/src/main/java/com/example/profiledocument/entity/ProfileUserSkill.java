package com.example.profiledocument.entity;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.PropertyName;

import java.util.List;

public class ProfileUserSkill {

    @DocumentId
    private String userSkillId;
    private String profileUserId;
    private String createdDate;
    private String updatedDate;
    private List<String> skills;

    // Getters and Setters with @PropertyName

    @PropertyName("user_skill_id")
    public String getUserSkillId() {
        return userSkillId;
    }

    @PropertyName("user_skill_id")
    public void setUserSkillId(String userSkillId) {
        this.userSkillId = userSkillId;
    }

    @PropertyName("profile_user_id")
    public String getProfileUserId() {
        return profileUserId;
    }

    @PropertyName("profile_user_id")
    public void setProfileUserId(String profileUserId) {
        this.profileUserId = profileUserId;
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

    @PropertyName("skills")
    public List<String> getSkills() {
        return skills;
    }

    @PropertyName("skills")
    public void setSkills(List<String> skills) {
        this.skills = skills;
    }
}
