package com.adventour.web.controller;

import com.adventour.web.dto.BookingDto;
import com.adventour.web.dto.CustomerDto;
import com.adventour.web.controller.CustomerController;
import com.adventour.web.dto.PassengerDto;
import com.adventour.web.dto.TripDto;
import com.adventour.web.enums.StatusOfBooking;
import com.adventour.web.models.Booking;
import com.adventour.web.models.Customer;
import com.adventour.web.models.Trip;
import com.adventour.web.service.BookingService;
import com.adventour.web.service.CustomerService;
import com.adventour.web.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Controller
public class BookingController {

    private BookingService bookingService;
    private CustomerService customerService;
    private TripService tripService;

        @Autowired
        public BookingController(BookingService bookingService, CustomerService customerService, TripService tripService) {
            this.bookingService = bookingService;
            this.customerService = customerService;
            this.tripService = tripService;
        }
    @GetMapping("/all-booking")
    public String allBooking(Model model){
        List<BookingDto> bookingDtos = bookingService.getListBooking();
        BookingDto booking = new BookingDto();
        booking.setId(1L);
        booking.setBookingDate(LocalDateTime.now());
        booking.setStatus(StatusOfBooking.PENDING);
        booking.setNumberOfPassengers(2);
        booking.setTotalAmount(1500000);
        booking.setAmountPaid(1000000);
        bookingDtos.add(booking);
        model.addAttribute("bookings", bookingDtos);
        return "/pages/all-booking";}

    @GetMapping("/booking-detail-information/{id}")
    public String bookingInfo(@PathVariable Long id, Model model){
        BookingDto booking = bookingService.findById(id);
        if (booking == null) {
            booking = new BookingDto();
            booking.setId(id);
            booking.setBookingDate(LocalDateTime.now());
            booking.setNumberOfPassengers(2);
            booking.setTotalAmount(1500000);
            booking.setAmountPaid(1000000);
        }
        CustomerDto customer = customerService.findById(booking.getCustomerDto().getId());
        /*Trip trip = tripService.findById(booking.getIdTrip());*/
        model.addAttribute("booking", booking);
        /*model.addAttribute("trip", trip);*/
        model.addAttribute("customer", customer);
        return "/pages/booking-detail-information";}

    @GetMapping("/add-new-booking-information")
    public String addNewBookingInfo(Model model){return "/pages/add-new-booking-information";}

    @GetMapping("/booking-detail-passenger")
    public String bookingDetailPassenger(Model model){
        BookingDto booking = new BookingDto();
        Set<PassengerDto> passengers = new HashSet<>();
//        booking.setPassengers(passengers);

        model.addAttribute("booking", booking);
            return "/pages/booking-detail-passenger";}

    @GetMapping("/add-new-booking-passenger")
    public String addNewBookingPassenger(Model model){return "/pages/add-new-booking-passenger";}

    @GetMapping("/booking-detail-payment")
    public String bookingDetailPayment(Model model){return "/pages/booking-detail-payment";}
    @GetMapping("/booking-detail-ticket")
    public String viewBookingDetailTicket(Model model){
        return "/pages/booking-detail-ticket";
    }

    @GetMapping("/add-new-booking-payment")
    public String addNewBookingPayment(Model model){return "/pages/add-new-booking-payment";}
}
