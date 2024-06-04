package com.adventour.web.service;

import com.adventour.web.dto.BookingDto;
import com.adventour.web.dto.CustomerDto;
import com.adventour.web.models.Booking;
import com.adventour.web.models.Customer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    List<CustomerDto> getListCustomer();

    CustomerDto findById(Long id);

    List<CustomerDto> searchCustomer(String search);

    Customer addNewCustomer(CustomerDto customerDto);

    Customer updateCustomer (CustomerDto customerDto);

    boolean deleteCustomer(CustomerDto customerDto);

    //List<BookingDto> getAllBookingOfCustomer(Long id);
}
