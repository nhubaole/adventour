package com.adventour.web.controller;

import com.adventour.web.dto.*;
import com.adventour.web.controller.CustomerController;
import com.adventour.web.enums.StatusOfBooking;
import com.adventour.web.models.Booking;
import com.adventour.web.models.Customer;
import com.adventour.web.models.Trip;
import com.adventour.web.service.BookingService;
import com.adventour.web.service.CustomerService;
import com.adventour.web.service.TourService;
import com.adventour.web.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
    private TourService tourService;
    private TripService tripService;

        @Autowired
        public BookingController(BookingService bookingService, CustomerService customerService, TripService tripService, TourService tourService) {
            this.bookingService = bookingService;
            this.customerService = customerService;
            this.tripService = tripService;
            this.tourService = tourService;
        }
    @GetMapping("/all-booking")
    public String allBooking(Model model){
        List<BookingDto> bookingDtos = bookingService.getListBooking();
        model.addAttribute("bookings", bookingDtos);
        return "/pages/all-booking";}

    @GetMapping("/all-booking/{id}/delete")
    public String deleteTour(@PathVariable("id") Long id){
        /*bookingService.deleteBooking(id);*/
        return "redirect:/all-booking";
    }

    @GetMapping("/booking-detail-information/{id}")
    public String bookingInfo(@PathVariable Long id, Model model){
        BookingDto booking = bookingService.findById(id);
        CustomerDto customer = customerService.findById(booking.getCustomerDto().getId());
        TripDto trip = booking.getTripDto();
        TourDto tour = booking.getTripDto().getTourDto();
        model.addAttribute("booking", booking);
        model.addAttribute("trip", trip);
        model.addAttribute("tour",tour);
        model.addAttribute("customer", customer);
        return "/pages/booking-detail-information";}

    @GetMapping("/booking-detail-information-edit/{id}")
    public String bookingEdit(@PathVariable Long id, Model model){
        BookingDto booking = bookingService.findById(id);
        CustomerDto customer = customerService.findById(booking.getCustomerDto().getId());
        TripDto trip = booking.getTripDto();
        TourDto tour = booking.getTripDto().getTourDto();
        model.addAttribute("booking", booking);
        model.addAttribute("trip", trip);
        model.addAttribute("tour",tour);
        model.addAttribute("customer", customer);
        return "/pages/booking-detail-information-edit";}

    @PostMapping("/booking-detail-information-edit/{id}/edit")
    public String updateBooking(@PathVariable("id") Long id,@ModelAttribute BookingDto booking) {

        booking.setId(id);
        CustomerDto customer = booking.getCustomerDto();
        customerService.updateCustomer(customer);
        booking.setCustomerDto(customer);
        bookingService.updateBooking(booking);
        return "redirect:/booking-detail-information-edit/" + booking.getId();
    }



    @GetMapping("/add-new-booking-information")
    public String addNewBookingInfo(Model model){return "/pages/add-new-booking-information";}

    @GetMapping("/booking-detail-passenger")
    public String bookingDetailPassenger(Model model){
        BookingDto booking = new BookingDto();
        Set<PassengerDto> passengers = new HashSet<>();
        booking.setPassengerDtos(passengers);

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
