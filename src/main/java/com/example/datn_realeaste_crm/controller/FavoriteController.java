package com.example.datn_realeaste_crm.controller;

import com.example.datn_realeaste_crm.audit.Auditable;

import com.example.datn_realeaste_crm.dto.request.FavoriteRequest;
import com.example.datn_realeaste_crm.dto.response.PropertyResponse;
import com.example.datn_realeaste_crm.entity.User;
import com.example.datn_realeaste_crm.service.FavoriteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/favorites")
@RequiredArgsConstructor
public class FavoriteController {
    
    private final FavoriteService favoriteService;
    
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<PropertyResponse>> getFavoriteProperties(Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = ((User) authentication.getPrincipal()).getUserId();
        
        return ResponseEntity.ok(favoriteService.getFavoriteProperties(userId, pageable));
    }
    
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @Auditable(action = "ADD_FAVORITE", entityType = "Favorite", logResult = true)
    public ResponseEntity<Void> addFavorite(@Valid @RequestBody FavoriteRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = ((User) authentication.getPrincipal()).getUserId();
        
        favoriteService.addFavorite(userId, request.getPropertyId());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    @DeleteMapping("/{propertyId}")
    @PreAuthorize("isAuthenticated()")
    @Auditable(action = "REMOVE_FAVORITE", entityType = "Favorite", entityIdParam = "propertyId")
    public ResponseEntity<Void> removeFavorite(@PathVariable Integer propertyId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = ((User) authentication.getPrincipal()).getUserId();
        
        favoriteService.removeFavorite(userId, propertyId);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/check/{propertyId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Boolean> checkFavorite(@PathVariable Integer propertyId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = ((User) authentication.getPrincipal()).getUserId();
        
        boolean isFavorite = favoriteService.checkFavorite(userId, propertyId);
        return ResponseEntity.ok(isFavorite);
    }
}