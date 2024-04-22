package com.adventour.web.service;

import com.adventour.web.dto.BookingDto;
import com.adventour.web.dto.CustomerDto;
import com.adventour.web.models.Booking;
import com.adventour.web.models.Customer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    List<CustomerDto> findAllCustomer();

    Optional<Customer> findById(Long id);

    Customer saveCustomer(CustomerDto customerDto);

    Customer updateCustomer (CustomerDto customerDto);

    Customer deleteCustomerById(Long id);

    List<BookingDto> getAllBookingOfCustomer(Long id);



}
