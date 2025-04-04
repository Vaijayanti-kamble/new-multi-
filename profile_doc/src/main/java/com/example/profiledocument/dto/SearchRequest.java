package com.example.profiledocument.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchRequest {
    private String location; // Example: "Bangalore"
    private String skill; // Example: "Java"
}
