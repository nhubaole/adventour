package com.adventour.web.service.impl;

import com.adventour.web.dto.BookingDto;
import com.adventour.web.dto.CustomerDto;
import com.adventour.web.models.Customer;
import com.adventour.web.repository.CustomerRepository;
import com.adventour.web.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    private CustomerDto mapToCustomerDto (Customer customer){
        return CustomerDto.builder()
                .id(customer.getId())
                .nameCustomer(customer.getNameCustomer())
                .phoneNumber(customer.getPhoneNumber())
                .email(customer.getEmail())
                .dateOfBirth(customer.getDateOfBirth())
                .cccd(customer.getCccd())
                .isMale(customer.isMale())
                .nationality(customer.getNationality())
                .addressCustomer(customer.getAddressCustomer())
                .imagesCustomer(customer.getImagesCustomer())
                .build();
    }

    @Override
    public List<CustomerDto> findAllCustomer() {
        List<Customer> customers = customerRepository.findAll();

        return customers.stream().map(customer -> mapToCustomerDto(customer)).collect(Collectors.toList());
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public Customer saveCustomer(CustomerDto customerDto) {
        if(AllInforCustomerIsNotNull(customerDto)){
            Customer customer = mapToCustomer(customerDto);
            return customerRepository.save(customer);
        }
        else return null;
    }

    private boolean AllInforCustomerIsNotNull(CustomerDto customerDto) {
        if(StringUtils.isEmptyOrWhitespace(customerDto.getNameCustomer())
            ||StringUtils.isEmptyOrWhitespace(customerDto.getPhoneNumber())
            ||StringUtils.isEmptyOrWhitespace(customerDto.getAddressCustomer())
            ||StringUtils.isEmptyOrWhitespace(customerDto.getEmail())
            ||StringUtils.isEmptyOrWhitespace(customerDto.getCccd())
            ||StringUtils.isEmptyOrWhitespace(customerDto.getNationality())
            ||StringUtils.isEmptyOrWhitespace(customerDto.getDateOfBirth().toString())
            ) {
            return false;
        }
        return true;
    }

    private Customer mapToCustomer(CustomerDto customerDto) {
        return Customer.builder()
                .nameCustomer(customerDto.getNameCustomer())
                .addressCustomer(customerDto.getAddressCustomer())
                .phoneNumber(customerDto.getPhoneNumber())
                .email(customerDto.getEmail())
                .dateOfBirth(customerDto.getDateOfBirth())
                .cccd(customerDto.getCccd())
                .isMale(customerDto.isMale())
                .nationality(customerDto.getNationality())
                .imagesCustomer(customerDto.getImagesCustomer())
                .build();
    }

    @Override
    public Customer updateCustomer(CustomerDto customerDto) {
        return saveCustomer(customerDto);
    }

    @Override
    public Customer deleteCustomerById(Long id) {
        return null;
    }

    @Override
    public List<BookingDto> getAllBookingOfCustomer(Long id) {

        return null;
    }
}
