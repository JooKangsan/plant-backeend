package com.plantcommunity.controller;

import com.plantcommunity.entity.Challenge;
import com.plantcommunity.entity.ChallengeParticipation;
import com.plantcommunity.service.ChallengeService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/challenges")
public class ChallengeController {
    
    private final ChallengeService challengeService;
    
    public ChallengeController(ChallengeService challengeService) {
        this.challengeService = challengeService;
    }
    
    @GetMapping
    public ResponseEntity<Page<Challenge>> getChallenges(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String difficulty,
            @RequestParam(defaultValue = "latest") String sort,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Page<Challenge> challenges = challengeService.getChallenges(status, category, difficulty, sort, page, size);
        return ResponseEntity.ok(challenges);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Challenge> getChallenge(@PathVariable Long id) {
        Challenge challenge = challengeService.getChallenge(id);
        return ResponseEntity.ok(challenge);
    }
    
    @PostMapping("/{id}/join")
    public ResponseEntity<Map<String, String>> joinChallenge(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        
        Map<String, String> response = challengeService.joinChallenge(id, userEmail);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/{id}/verify")
    public ResponseEntity<Map<String, Object>> verifyChallenge(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        String comment = request.get("comment");
        
        Map<String, Object> response = challengeService.verifyChallenge(id, userEmail, comment);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/my")
    public ResponseEntity<Page<ChallengeParticipation>> getMyChallenges(
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        
        Page<ChallengeParticipation> challenges = challengeService.getMyChallenges(status, page, size, userEmail);
        return ResponseEntity.ok(challenges);
    }
}
