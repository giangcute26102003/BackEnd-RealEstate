package com.example.datn_realeaste_crm.service;


import com.example.datn_realeaste_crm.dto.request.InteractionRequest;
import com.example.datn_realeaste_crm.dto.response.*;
import com.example.datn_realeaste_crm.entity.Customer;
import com.example.datn_realeaste_crm.entity.Interaction;
import com.example.datn_realeaste_crm.entity.Property;
import com.example.datn_realeaste_crm.exception.ResourceNotFoundException;
import com.example.datn_realeaste_crm.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class InteractionService {
    
    private final InteractionRepository interactionRepository;
    private final CustomerRepository customerRepository;
    private final PropertyRepository propertyRepository;
    
    public Page<InteractionResponse> getAllInteractions(Integer customerId, Integer propertyId, Pageable pageable) {
        Page<Interaction> interactions;
        
        if (customerId != null && propertyId != null) {
            interactions = interactionRepository.findByCustomerCustomerIdAndPropertyPropertyId(
                    customerId, propertyId, pageable);
        } else if (customerId != null) {
            interactions = interactionRepository.findByCustomerCustomerId(customerId, pageable);
        } else if (propertyId != null) {
            interactions = interactionRepository.findByPropertyPropertyId(propertyId, pageable);
        } else {
            interactions = interactionRepository.findAll(pageable);
        }
        
        return interactions.map(this::convertToInteractionResponse);
    }
    
    public InteractionResponse getInteraction(Integer id) {
        Interaction interaction = interactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Interaction not found with id: " + id));
        
        return convertToInteractionResponse(interaction);
    }
    
    @Transactional
    public InteractionResponse createInteraction(InteractionRequest request) {
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + request.getCustomerId()));
        
        Property property = propertyRepository.findById(request.getPropertyId())
                .orElseThrow(() -> new ResourceNotFoundException("Property not found with id: " + request.getPropertyId()));
        
        Interaction interaction = new Interaction();
        interaction.setCustomer(customer);
        interaction.setProperty(property);
        interaction.setDate(request.getDate());
        interaction.setDetails(request.getDetails());
        interaction.setCreatedAt(LocalDateTime.now());
        interaction.setUpdatedAt(LocalDateTime.now());
        
        Interaction savedInteraction = interactionRepository.save(interaction);
        
        return convertToInteractionResponse(savedInteraction);
    }
    
    @Transactional
    public InteractionResponse updateInteraction(Integer id, InteractionRequest request) {
        Interaction interaction = interactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Interaction not found with id: " + id));
        
        if (!interaction.getCustomer().getCustomerId().equals(request.getCustomerId())) {
            Customer customer = customerRepository.findById(request.getCustomerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + request.getCustomerId()));
            interaction.setCustomer(customer);
        }
        
        if (!interaction.getProperty().getPropertyId().equals(request.getPropertyId())) {
            Property property = propertyRepository.findById(request.getPropertyId())
                    .orElseThrow(() -> new ResourceNotFoundException("Property not found with id: " + request.getPropertyId()));
            interaction.setProperty(property);
        }
        
        interaction.setDate(request.getDate());
        interaction.setDetails(request.getDetails());
        interaction.setUpdatedAt(LocalDateTime.now());
        
        Interaction updatedInteraction = interactionRepository.save(interaction);
        
        return convertToInteractionResponse(updatedInteraction);
    }
    
    @Transactional
    public void deleteInteraction(Integer id) {
        if (!interactionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Interaction not found with id: " + id);
        }
        
        interactionRepository.deleteById(id);
    }
    
    private InteractionResponse convertToInteractionResponse(Interaction interaction) {
        return InteractionResponse.builder()
                .id(interaction.getInteractionId())
                .customerId(interaction.getCustomer().getCustomerId())
                .customerName(interaction.getCustomer().getName())
                .propertyId(interaction.getProperty().getPropertyId())
                .propertyAddress(interaction.getProperty().getAddressProperty())
                .propertyType(interaction.getProperty().getPropertyType())
                .date(interaction.getDate())
                .details(interaction.getDetails())
                .createdAt(interaction.getCreatedAt())
                .updatedAt(interaction.getUpdatedAt())
                .build();
    }
}