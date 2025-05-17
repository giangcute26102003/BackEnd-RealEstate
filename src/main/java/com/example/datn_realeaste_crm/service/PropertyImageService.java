package com.example.datn_realeaste_crm.service;


import com.example.datn_realeaste_crm.dto.request.PropertyImageRequest;
import com.example.datn_realeaste_crm.dto.response.PropertyImageResponse;
import com.example.datn_realeaste_crm.entity.Property;
import com.example.datn_realeaste_crm.entity.PropertyImage;
import com.example.datn_realeaste_crm.exception.ResourceNotFoundException;
import com.example.datn_realeaste_crm.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PropertyImageService {

    private final PropertyImageRepository propertyImageRepository;
    private final PropertyRepository propertyRepository;

    public List<PropertyImageResponse> getPropertyImages(Integer propertyId) {
        if (!propertyRepository.existsById(propertyId)) {
            throw new ResourceNotFoundException("Property not found with id: " + propertyId);
        }

        return propertyImageRepository.findByPropertyPropertyId(propertyId)
                .stream()
                .map(this::convertToPropertyImageResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public PropertyImageResponse addPropertyImage(Integer propertyId, PropertyImageRequest request) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new ResourceNotFoundException("Property not found with id: " + propertyId));

        PropertyImage propertyImage = new PropertyImage();
        propertyImage.setProperty(property);
        propertyImage.setImageUrl(request.getImageUrl());

        PropertyImage savedImage = propertyImageRepository.save(propertyImage);

        return convertToPropertyImageResponse(savedImage);
    }

    @Transactional
    public void deletePropertyImage(Integer propertyId, Integer imageId) {
        if (!propertyRepository.existsById(propertyId)) {
            throw new ResourceNotFoundException("Property not found with id: " + propertyId);
        }

        PropertyImage propertyImage = propertyImageRepository.findById(imageId)
                .orElseThrow(() -> new ResourceNotFoundException("Property image not found with id: " + imageId));

        if (!propertyImage.getProperty().getPropertyId().equals(propertyId)) {
            throw new ResourceNotFoundException("Property image with id: " + imageId +
                    " does not belong to property with id: " + propertyId);
        }

        propertyImageRepository.delete(propertyImage);
    }

    @Transactional
    public PropertyImageResponse updatePropertyImage(Integer propertyId, Integer imageId, PropertyImageRequest request) {
        if (!propertyRepository.existsById(propertyId)) {
            throw new ResourceNotFoundException("Property not found with id: " + propertyId);
        }

        PropertyImage propertyImage = propertyImageRepository.findById(imageId)
                .orElseThrow(() -> new ResourceNotFoundException("Property image not found with id: " + imageId));

        if (!propertyImage.getProperty().getPropertyId().equals(propertyId)) {
            throw new ResourceNotFoundException("Property image with id: " + imageId +
                    " does not belong to property with id: " + propertyId);
        }

        propertyImage.setImageUrl(request.getImageUrl());

        PropertyImage updatedImage = propertyImageRepository.save(propertyImage);

        return convertToPropertyImageResponse(updatedImage);
    }

    private PropertyImageResponse convertToPropertyImageResponse(PropertyImage propertyImage) {
        return PropertyImageResponse.builder()
                .id(propertyImage.getId())
                .propertyId(propertyImage.getProperty().getPropertyId())
                .imageUrl(propertyImage.getImageUrl())
                .build();
    }
}