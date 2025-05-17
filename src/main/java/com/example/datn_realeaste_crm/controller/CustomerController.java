package com.example.datn_realeaste_crm.controller;



import com.example.datn_realeaste_crm.audit.Auditable;
import com.example.datn_realeaste_crm.dto.request.CustomerRequest;
import com.example.datn_realeaste_crm.dto.request.CustomerRequirementRequest;
import com.example.datn_realeaste_crm.dto.response.*;
import com.example.datn_realeaste_crm.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {
    
    private final CustomerService customerService;
    
    @GetMapping
    @PreAuthorize("hasAuthority('customer_view')")
    public ResponseEntity<Page<CustomerResponse>> getAllCustomers(Pageable pageable) {
        return ResponseEntity.ok(customerService.getAllCustomers(pageable));
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('customer_view')")
    public ResponseEntity<CustomerResponse> getCustomer(@PathVariable Integer id) {
        return ResponseEntity.ok(customerService.getCustomer(id));
    }
    
    @PostMapping
    @PreAuthorize("hasAuthority('customer_create')")
    @Auditable(action = "CREATE_CUSTOMER", entityType = "Customer", logResult = true)
    public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CustomerRequest request) {
        return new ResponseEntity<>(customerService.createCustomer(request), HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('customer_update')")
    @Auditable(action = "UPDATE_CUSTOMER", entityType = "Customer", entityIdParam = "id")
    public ResponseEntity<CustomerResponse> updateCustomer(
            @PathVariable Integer id, 
            @Valid @RequestBody CustomerRequest request) {
        return ResponseEntity.ok(customerService.updateCustomer(id, request));
    }
    
    @PostMapping("/requirements")
    @PreAuthorize("hasAuthority('customer_requirements_create')")
    @Auditable(action = "CREATE_CUSTOMER_REQUIREMENT", entityType = "CustomerRequirement", logResult = true)
    public ResponseEntity<CustomerRequirementResponse> createCustomerRequirement(
            @Valid @RequestBody CustomerRequirementRequest request) {
        return new ResponseEntity<>(customerService.createCustomerRequirement(request), HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}/requirements")
    @PreAuthorize("hasAuthority('customer_view')")
    public ResponseEntity<Page<CustomerRequirementResponse>> getCustomerRequirements(
            @PathVariable Integer id, 
            Pageable pageable) {
        return ResponseEntity.ok(customerService.getCustomerRequirements(id, pageable));
    }
}