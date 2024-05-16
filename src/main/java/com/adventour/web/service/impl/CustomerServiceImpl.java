package com.adventour.web.service.impl;

import com.adventour.web.dto.BookingDto;
import com.adventour.web.dto.CustomerDto;
import com.adventour.web.models.Booking;
import com.adventour.web.models.Customer;
import com.adventour.web.repository.BookingRepository;
import com.adventour.web.repository.CustomerRepository;
import com.adventour.web.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
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
//                .isMale(customer.isMale())
                .nationality(customer.getNationality())
                .addressCustomer(customer.getAddressCustomer())
                .imagesCustomer(customer.getImagesCustomer())
                .build();
    }

    @Override
    public List<CustomerDto> getListCustomer() {
        List<Customer> customers = customerRepository.findAll();

        return customers.stream().map(customer -> mapToCustomerDto(customer)).collect(Collectors.toList());
    }

    @Override
    public CustomerDto findById(Long id) {
        Customer cus =  customerRepository.findById(id).orElse(null);
        if(cus != null){
            CustomerDto cusDTO = mapToCustomerDto(cus);
            return cusDTO;
        }
        return null;
    }

    @Override
    public List<CustomerDto> searchCustomer(String search) {
        search = search.trim();
        List<CustomerDto> result = new ArrayList<>();
        List<Customer> allCus = customerRepository.findAll();
        for (Customer customer : allCus){
            if (customer.getNameCustomer().contains(search) ||
                    customer.getEmail().contains(search) ||
                    customer.getCccd().contains(search) ||
                    customer.getPhoneNumber().contains(search)){
                       CustomerDto customerDto = mapToCustomerDto(customer);
                       result.add(customerDto);
            }
        }
        return result;
    }

    @Override
    public Customer  addNewCustomer(CustomerDto customerDto) {
        if(validateCustomer(customerDto)){
            Customer customer = mapToCustomer(customerDto);
            return customerRepository.save(customer);
        }
        else return null;
    }

    private boolean validateCustomer(CustomerDto customerDto) {
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
                .id(customerDto.getId())
                .nameCustomer(customerDto.getNameCustomer())
                .addressCustomer(customerDto.getAddressCustomer())
                .phoneNumber(customerDto.getPhoneNumber())
                .email(customerDto.getEmail())
                .dateOfBirth(customerDto.getDateOfBirth())
                .cccd(customerDto.getCccd())
//                .isMale(customerDto.isMale())
                .nationality(customerDto.getNationality())
                .imagesCustomer(customerDto.getImagesCustomer())
                .build();
    }

    @Override
    public Customer updateCustomer(CustomerDto customerDto) {
        return addNewCustomer(customerDto);
    }

    @Override
    public Customer deleteCustomer(CustomerDto customerDto) {
        //kiem tra co th xoa hay k


        return null;
    }

//    @Override
//    public List<BookingDto> getAllBookingOfCustomer(Long id) {
//        List<Booking> bookings = bookingRepository.findByCustomerId(id);
//
//        return null;
//    }
}
