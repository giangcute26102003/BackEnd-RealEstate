package com.example.datn_realeaste_crm.service;


import com.example.datn_realeaste_crm.dto.request.CustomerRequest;
import com.example.datn_realeaste_crm.dto.request.CustomerRequirementRequest;
import com.example.datn_realeaste_crm.dto.response.CustomerRequirementResponse;
import com.example.datn_realeaste_crm.dto.response.CustomerResponse;
import com.example.datn_realeaste_crm.entity.Customer;
import com.example.datn_realeaste_crm.entity.CustomerRequirements;
import com.example.datn_realeaste_crm.exception.ResourceNotFoundException;

import com.example.datn_realeaste_crm.repository.CustomerRepository;
import com.example.datn_realeaste_crm.repository.CustomerRequirementsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CustomerService {
    
    private final CustomerRepository customerRepository;
    private final CustomerRequirementsRepository customerRequirementsRepository;
    
    public Page<CustomerResponse> getAllCustomers(Pageable pageable) {
        return customerRepository.findAll(pageable)
                .map(this::convertToCustomerResponse);
    }
    
    public CustomerResponse getCustomer(Integer id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
        
        return convertToCustomerResponse(customer);
    }
    
    @Transactional
    public CustomerResponse createCustomer(CustomerRequest request) {
        Customer customer = new Customer();
        updateCustomerFromRequest(customer, request);
        
        customer.setCreatedAt(LocalDateTime.now());
        customer.setUpdatedAt(LocalDateTime.now());
        
        Customer savedCustomer = customerRepository.save(customer);
        
        return convertToCustomerResponse(savedCustomer);
    }
    
    @Transactional
    public CustomerResponse updateCustomer(Integer id, CustomerRequest request) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
        
        updateCustomerFromRequest(customer, request);
        customer.setUpdatedAt(LocalDateTime.now());
        
        Customer updatedCustomer = customerRepository.save(customer);
        
        return convertToCustomerResponse(updatedCustomer);
    }
    
    @Transactional
    public CustomerRequirementResponse createCustomerRequirement(CustomerRequirementRequest request) {
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + request.getCustomerId()));
        
        CustomerRequirements requirements = new CustomerRequirements();
        requirements.setCustomer(customer);
        requirements.setPurpose(request.getPurpose());
        requirements.setBudgetMin(request.getBudgetMin());
        requirements.setBudgetMax(request.getBudgetMax());
        requirements.setPreferredLocation(request.getPreferredLocation());
        requirements.setPropertyType(request.getPropertyType());
        requirements.setSizeMin(request.getSizeMin());
        requirements.setBedrooms(request.getBedrooms());
        requirements.setBathrooms(request.getBathrooms());
        requirements.setOtherPreferences(request.getOtherPreferences());
        requirements.setCreatedAt(LocalDateTime.now());
        requirements.setUpdatedAt(LocalDateTime.now());
        
        CustomerRequirements savedRequirements = customerRequirementsRepository.save(requirements);
        
        return convertToCustomerRequirementResponse(savedRequirements);
    }
    
    public Page<CustomerRequirementResponse> getCustomerRequirements(Integer customerId, Pageable pageable) {
        if (!customerRepository.existsById(customerId)) {
            throw new ResourceNotFoundException("Customer not found with id: " + customerId);
        }
        
        return customerRequirementsRepository.findByCustomerCustomerId(customerId, pageable)
                .map(this::convertToCustomerRequirementResponse);
    }
    
    private void updateCustomerFromRequest(Customer customer, CustomerRequest request) {
        customer.setName(request.getName());
        customer.setPhoneNumber(request.getPhoneNumber());
        customer.setEmail(request.getEmail());
        customer.setAddress(request.getAddress());
        customer.setDob(request.getDob());
    }
    
    private CustomerResponse convertToCustomerResponse(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getCustomerId())
                .name(customer.getName())
                .phoneNumber(customer.getPhoneNumber())
                .email(customer.getEmail())
                .address(customer.getAddress())
                .dob(customer.getDob())
                .createdAt(customer.getCreatedAt())
                .updatedAt(customer.getUpdatedAt())
                .build();
    }
    
    private CustomerRequirementResponse convertToCustomerRequirementResponse(CustomerRequirements requirements) {
        return CustomerRequirementResponse.builder()
                .id(requirements.getRequirementId())
                .customerId(requirements.getCustomer().getCustomerId())
                .customerName(requirements.getCustomer().getName())
                .purpose(requirements.getPurpose())
                .budgetMin(requirements.getBudgetMin())
                .budgetMax(requirements.getBudgetMax())
                .preferredLocation(requirements.getPreferredLocation())
                .propertyType(requirements.getPropertyType())
                .sizeMin(requirements.getSizeMin())
                .bedrooms(requirements.getBedrooms())
                .bathrooms(requirements.getBathrooms())
                .otherPreferences(requirements.getOtherPreferences())
                .createdAt(requirements.getCreatedAt())
                .updatedAt(requirements.getUpdatedAt())
                .build();
    }
}