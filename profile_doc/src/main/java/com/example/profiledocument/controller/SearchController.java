package com.example.profiledocument.controller;

import com.example.profiledocument.dto.SearchRequest;
import com.example.profiledocument.dto.SearchResult;
import com.example.profiledocument.service.SearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/search")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    // 🔍 Search users based on skill and location
    @PostMapping
    public ResponseEntity<List<SearchResult>> searchUsers(@RequestBody SearchRequest request) {
        try {
            List<SearchResult> results = searchService.searchUsers(request);
            if (results.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(results);
        } catch (ExecutionException | InterruptedException e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }
}
