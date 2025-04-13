package com.example.profiledocument.dto;

import com.example.profiledocument.entity.ProfileUserSkill;
import com.example.profiledocument.entity.ProfileUserAddress;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchResult {
    private String searchId;
    private List<ProfileUserAddress> matchedAddresses;
    private List<ProfileUserSkill> matchedSkills;
}
