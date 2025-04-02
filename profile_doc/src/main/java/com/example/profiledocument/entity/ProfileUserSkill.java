package com.example.profiledocument.entity;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.PropertyName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileUserSkill {

    @DocumentId
    private String profileUserSkillId; // Unique ID

    @PropertyName("profile_user_id")
    private String profileUserId;

    @PropertyName("created_date")
    private String createdDate;

    @PropertyName("updated_date")
    private String updatedDate;

    @PropertyName("skills")
    private List<String> skills;
}
