package com.example.datn_realeaste_crm.controller;


import com.example.datn_realeaste_crm.audit.Auditable;
import com.example.datn_realeaste_crm.dto.request.PropertyImageRequest;
import com.example.datn_realeaste_crm.dto.response.PropertyImageResponse;
import com.example.datn_realeaste_crm.service.PropertyImageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/properties/{propertyId}/images")
@RequiredArgsConstructor
public class PropertyImageController {
    
    private final PropertyImageService propertyImageService;
    
    @GetMapping
    public ResponseEntity<List<PropertyImageResponse>> getPropertyImages(@PathVariable Integer propertyId) {
        return ResponseEntity.ok(propertyImageService.getPropertyImages(propertyId));
    }
    
    @PostMapping
    @PreAuthorize("hasAuthority('property_update') or @propertyAuthorizationService.isPropertyOwner(authentication, #propertyId)")
    @Auditable(action = "ADD_PROPERTY_IMAGE", entityType = "PropertyImage", logResult = true)
    public ResponseEntity<PropertyImageResponse> addPropertyImage(
            @PathVariable Integer propertyId,
            @Valid @RequestBody PropertyImageRequest request) {
        return new ResponseEntity<>(propertyImageService.addPropertyImage(propertyId, request), HttpStatus.CREATED);
    }
    
    @DeleteMapping("/{imageId}")
    @PreAuthorize("hasAuthority('property_update') or @propertyAuthorizationService.isPropertyOwner(authentication, #propertyId)")
    @Auditable(action = "DELETE_PROPERTY_IMAGE", entityType = "PropertyImage", entityIdParam = "imageId")
    public ResponseEntity<Void> deletePropertyImage(
            @PathVariable Integer propertyId,
            @PathVariable Integer imageId) {
        propertyImageService.deletePropertyImage(propertyId, imageId);
        return ResponseEntity.ok().build();
    }
    
    @PutMapping("/{imageId}")
    @PreAuthorize("hasAuthority('property_update') or @propertyAuthorizationService.isPropertyOwner(authentication, #propertyId)")
    @Auditable(action = "UPDATE_PROPERTY_IMAGE", entityType = "PropertyImage", entityIdParam = "imageId")
    public ResponseEntity<PropertyImageResponse> updatePropertyImage(
            @PathVariable Integer propertyId,
            @PathVariable Integer imageId,
            @Valid @RequestBody PropertyImageRequest request) {
        return ResponseEntity.ok(propertyImageService.updatePropertyImage(propertyId, imageId, request));
    }
}