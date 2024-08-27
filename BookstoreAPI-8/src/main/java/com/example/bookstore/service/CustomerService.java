package com.example.bookstore.service;

import com.example.bookstore.dto.CustomerDTO;
import com.example.bookstore.entity.Customer;
import com.example.bookstore.mapper.CustomerMapper;
import com.example.bookstore.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public CustomerDTO getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Customer not found"));
        return CustomerMapper.INSTANCE.customerToCustomerDTO(customer);
    }

    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream()
            .map(CustomerMapper.INSTANCE::customerToCustomerDTO)
            .collect(Collectors.toList());
    }

    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        Customer customer = CustomerMapper.INSTANCE.customerDTOToCustomer(customerDTO);
        customer = customerRepository.save(customer);
        return CustomerMapper.INSTANCE.customerToCustomerDTO(customer);
    }

    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) {
        Customer existingCustomer = customerRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Customer not found"));
        CustomerMapper.INSTANCE.customerDTOToCustomer(customerDTO);
        customerRepository.save(existingCustomer);
        return CustomerMapper.INSTANCE.customerToCustomerDTO(existingCustomer);
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}

