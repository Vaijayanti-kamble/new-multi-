package com.example.profiledocument.dto;

import com.example.profiledocument.entity.ProfileUserSkill;
import com.example.profiledocument.entity.UserAddress;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchResult {
    private ProfileUserSkill profileUserSkill;
    private UserAddress userAddress;
}
