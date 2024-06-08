package com.adventour.web.controller;

import com.adventour.web.dto.*;
import com.adventour.web.controller.CustomerController;
import com.adventour.web.enums.PaymentMethod;
import com.adventour.web.enums.StatusOfBooking;
import com.adventour.web.mapper.Mapper;
import com.adventour.web.models.Booking;
import com.adventour.web.models.Customer;
import com.adventour.web.models.Trip;
import com.adventour.web.service.*;
import com.adventour.web.utils.FormatNumber;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private PaymentInformationService paymentInformationService;
    private PassengerService passengerService;
    private TicketService ticketService;
    private Mapper mapper;
    private BookingDto bookingForm;
    private BucketService bucketService;


        @Autowired
        public BookingController(BookingService bookingService, CustomerService customerService,
                                 TripService tripService, TourService tourService,
                                 PaymentInformationService paymentInformationService,
                                 PassengerService passengerService,TicketService ticketService,
                                 Mapper mapper, BucketService bucketService) {

            this.bookingService = bookingService;
            this.customerService = customerService;
            this.tripService = tripService;
            this.tourService = tourService;
            this.paymentInformationService = paymentInformationService;
            this.passengerService = passengerService;
            this.ticketService = ticketService;
            this.mapper = mapper;
            this.bucketService = bucketService;
            this.bookingForm = new BookingDto();
        }
    private static Logger logger = LoggerFactory.getLogger(BookingController.class);
    @GetMapping("/all-booking")
    public String allBooking(Model model,@Param("keyword") String keyword){
        List<BookingDto> bookingDtos = new ArrayList<>();
        if (keyword == null) {
            bookingDtos =  bookingService.getListBooking();
        } else {
            bookingDtos = bookingService.searchBooking(keyword);
            model.addAttribute("keyword", keyword);
        }
        model.addAttribute("bookings", bookingDtos);
        //TODO : format number
        model.addAttribute("formatNumber", new FormatNumber());
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
        model.addAttribute("formatNumber", new FormatNumber());
        return "/pages/booking-detail-information";}

    @PostMapping("/booking-detail-information/{id}")
    public String cancelBooking(@PathVariable("id") Long id) {
        BookingDto booking = bookingService.findById(id);
        if (booking != null) {
            booking.setStatus(StatusOfBooking.CANCELLED);
            bookingService.updateBooking(booking); // Update booking state in the database
        }
        return "redirect:/all-booking"; // Redirect to the desired page after cancellation
    }

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



    @GetMapping("/add-new-booking-information/{id}")
    public String addNewBookingInfo(@PathVariable("id") Long id,Model model){
        TripDto trip = tripService.getTripDetail(id);
        List<BookingDto> bookingDtos = bookingService.getListBooking();
        List<CustomerDto> customerDtos = customerService.getListCustomer();
        BookingDto bookingDto = new BookingDto();
        if (bookingDto.getCustomerDto() == null) {
            bookingDto.setCustomerDto(new CustomerDto());
        }
        model.addAttribute("bookingDtos", bookingDtos);
        model.addAttribute("customerDtos", customerDtos);
        model.addAttribute("trip",trip);
        model.addAttribute("bookingDto", bookingDto);
            return "/pages/add-new-booking-information";}

    @PostMapping("/add-new-booking-information/{id}")
    public String handleBookingInfo(@PathVariable("id") Long id,
                                    @ModelAttribute BookingDto bookingDto, @RequestParam("files") MultipartFile[] files) {
        List<String> customerImage = new ArrayList<>();
        for (MultipartFile image : files){
            if (image.isEmpty()) {
                logger.warn("Uploaded image is empty: " + image.getOriginalFilename());
                break;
            }
            try{
                String fileUrl = bucketService.uploadFile(image);
                customerImage.add(fileUrl);
            } catch (Exception e){
                logger.error("Error uploading image: " + e.getMessage());
            }
        }

        if(bookingDto.getCustomerDto()==null){
            return "redirect:/add-new-booking-information/" + id;
        }
        this.bookingForm.setCustomerDto(new CustomerDto());
        bookingDto.getCustomerDto().setImagesCustomer(customerImage.toArray(new String[0]));
        this.bookingForm.setCustomerDto(bookingDto.getCustomerDto());
        return "redirect:/add-new-booking-passenger/" + id;
    }

    @GetMapping("/booking-detail-passenger/{id}")
    public String bookingDetailPassenger(@PathVariable Long id,Model model){
        BookingDto booking = bookingService.findById(id);
//        CustomerDto customer = customerService.findById(booking.getCustomerDto().getId());
        Set<PassengerDto> passengerDtos = passengerService.getPassengersByIdBooking(id);
        TripDto trip = booking.getTripDto();
        TourDto tour = booking.getTripDto().getTourDto();
        model.addAttribute("booking", booking);
        model.addAttribute("trip", trip);
        model.addAttribute("tour",tour);
//        model.addAttribute("customer", customer);
        model.addAttribute("passengerDtos",passengerDtos);
        model.addAttribute("formatNumber", new FormatNumber());
            return "/pages/booking-detail-passenger";}

    @GetMapping("/add-new-booking-passenger/{id}")
    public String addNewBookingPassenger(@PathVariable Long id,Model model){
        TripDto trip = tripService.getTripDetail(id);
        model.addAttribute("trip",trip);
        BookingDto bookingDto = new BookingDto();
        if (bookingDto.getPassengerDtos() == null) {
            bookingDto.setPassengerDtos(new HashSet<>());
        }
        model.addAttribute("bookingDto", bookingDto);
            return "/pages/add-new-booking-passenger";
        }

    @PostMapping("/add-new-booking-passenger/{id}")
    public String processNewBookingPassenger(@PathVariable Long id, @ModelAttribute BookingDto bookingDto, HttpServletRequest request) {
        //this.bookingForm = bookingDto;
        Set<PassengerDto> passengers = new HashSet<>();
        this.bookingForm.setPassengerDtos(passengers);
        TripDto trip = tripService.getTripDetail(id);

        int numOfAdults = Integer.parseInt(request.getParameter("num_of_adults"));
        for (int i = 1; i <= numOfAdults; i++) {
            PassengerDto adult = new PassengerDto();
            adult.setNamePassenger(request.getParameter("adult_fname_" + i));
            adult.setMale(Boolean.parseBoolean(request.getParameter("adult_gender_" + i)));
            adult.setDateOfBirth(LocalDate.parse(request.getParameter("adult_birthday_" + i)));
            adult.setCccd(request.getParameter("adult_id_" + i));
            adult.setType("Adult");
            passengers.add(adult);
        }

        this.bookingForm.setNumberAdult(numOfAdults);

        int numOfChildren = Integer.parseInt(request.getParameter("num_of_children"));
        for (int i = 1; i <= numOfChildren; i++) {
            PassengerDto child = new PassengerDto();
            child.setNamePassenger(request.getParameter("children_fname_" + i));
            child.setMale(Boolean.parseBoolean(request.getParameter("children_gender_" + i)));
            child.setDateOfBirth(LocalDate.parse(request.getParameter("children_birthday_" + i)));
            child.setCccd(request.getParameter("children_id_" + i));
            child.setType("Child");
            passengers.add(child);
        }
        this.bookingForm.setNumberChildren(numOfChildren);

        int numOfBaby = Integer.parseInt(request.getParameter("num_of_baby"));
        for (int i = 1; i <= numOfBaby; i++) {
            PassengerDto baby = new PassengerDto();
            baby.setNamePassenger(request.getParameter("baby_fname_" + i));
            baby.setMale(Boolean.parseBoolean(request.getParameter("baby_gender_" + i)));
            baby.setDateOfBirth(LocalDate.parse(request.getParameter("baby_birthday_" + i)));
            baby.setCccd(request.getParameter("baby_id_" + i));
            baby.setType("Baby");
            passengers.add(baby);
        }
        this.bookingForm.setNumberBaby(numOfBaby);
        int numOfPassengers = numOfBaby+numOfChildren+numOfAdults;
        this.bookingForm.setNumberOfPassengers(numOfPassengers);
        this.bookingForm.setTotalAmount((int) (numOfAdults*trip.getPriceTicket()*(1-trip.getDiscount()/100) +numOfChildren*trip.getPriceTicket()*(1-trip.getDiscount()/100)/2));
        /*bookingDto.setPassengerDtos(passengers);*/
        this.bookingForm.setPassengerDtos(passengers);
        return "redirect:/add-new-booking-payment/" + id;
    }


    @GetMapping("/booking-detail-payment/{id}")
    public String bookingDetailPayment(@PathVariable Long id, Model model){
        BookingDto booking = bookingService.findById(id);
        TourDto tour = booking.getTripDto().getTourDto();
        TripDto trip = booking.getTripDto();
        Set<TicketDto> tickets = ticketService.getTicketsByIdBooking(id);

        PaymentInformationDto paymentInformationDto = new PaymentInformationDto();
        Set<PaymentInformationDto> paymentInformationDtos = paymentInformationService.getPaymentInforByIdBooking(id);
        int paidAmount = 0;
        for (PaymentInformationDto informationDto : paymentInformationDtos) {
                 paidAmount+=informationDto.getAmountOfMoney();
             }
        model.addAttribute("tickets",tickets);
        model.addAttribute("booking", booking);
        model.addAttribute("tour",tour);
        model.addAttribute("trip",trip);
        model.addAttribute("paymentInformationDto", paymentInformationDto);
        model.addAttribute("paymentInformationDtos",paymentInformationDtos);
        model.addAttribute("paidAmount",paidAmount);
        model.addAttribute("formatNumber", new FormatNumber());
        return "/pages/booking-detail-payment";}

    @PostMapping("/booking-detail-payment/{id}/add-payment")
    public String addPayment(@PathVariable Long id, @ModelAttribute PaymentInformationDto paymentInformationDto, @RequestParam String amountPaid, @RequestParam String paymentMethod) {
        BookingDto bookingDto = bookingService.findById(id);
        paymentInformationDto.setAmountOfMoney(Integer.parseInt(amountPaid));
        paymentInformationDto.setPaymentMethod(PaymentMethod.valueOf(paymentMethod));
        paymentInformationDto.setPaymentTime(LocalDateTime.now());
        paymentInformationDto.setBookingDto(bookingDto);
        bookingService.addBookingPayment(paymentInformationDto,bookingDto);
        return "redirect:/booking-detail-payment/" + bookingDto.getId();
    }
    @GetMapping("/booking-detail-ticket/{id}")
    public String viewBookingDetailTicket(@PathVariable Long id,Model model){
        BookingDto booking = bookingService.findById(id);
        TripDto trip = booking.getTripDto();
        TourDto tour = booking.getTripDto().getTourDto();
//        Set<PassengerDto> passengerDtos = passengerService.getPassengersByIdBooking(id);
        List<TicketDto> ticketDtos = ticketService.getTicketsByIdBooking(id).stream().toList();
//        logger.info("======== " + booking.getAmountPaid() + " " + booking.getTotalAmount());
//        model.addAttribute("passengers", booking.getPassengerDtos());
        model.addAttribute("booking",booking);
        model.addAttribute("tour",tour);
        model.addAttribute("trip",trip);
        model.addAttribute("tickets", ticketDtos);
        model.addAttribute("formatNumber", new FormatNumber());
        return "/pages/booking-detail-ticket";
    }

    @GetMapping("/add-new-booking-payment/{id}")
    public String addNewBookingPayment(@PathVariable Long id,Model model){
        TripDto trip = tripService.getTripDetail(id);
        BookingDto booking = new BookingDto();
        TourDto tour = trip.getTourDto();

        Set<PaymentInformationDto> paymentInformationDtos = new HashSet<>();
        model.addAttribute("paymentInformationDtos", paymentInformationDtos);
        model.addAttribute("tour",tour);
        model.addAttribute("trip",trip);
        model.addAttribute("bookingform", this.bookingForm);
        model.addAttribute("formatNumber", new FormatNumber());
        return "/pages/add-new-booking-payment";
        }

    @PostMapping("/add-new-booking-payment/{id}")
    public String addPayment(@PathVariable Long id, @RequestParam String amountPaid, @RequestParam String paymentMethod) {
        PaymentInformationDto paymentInformationDto = new PaymentInformationDto();
        paymentInformationDto.setAmountOfMoney(Integer.parseInt(amountPaid));
        paymentInformationDto.setPaymentMethod(PaymentMethod.valueOf(paymentMethod));
        paymentInformationDto.setPaymentTime(LocalDateTime.now());
        paymentInformationDto.setBookingDto(bookingForm);
        this.bookingForm.getPaymentInformationDtos().add(paymentInformationDto);
        this.bookingForm.setTripDto(tripService.getTripDetail(id));
        this.bookingForm.setBookingDate(LocalDateTime.now());
        logger.info("Total amount1: {}", bookingForm.getTotalAmount());
        Booking booking = bookingService.addNewBooking(this.bookingForm);
        logger.info("Total amount2: {}", booking.getTotalAmount());
        BookingDto bookingDto = mapper.mapToBookingDto(booking);
        //bookingService.addBookingPayment(paymentInformationDto,this.bookingForm);
        int totalPaid = paymentInformationDto.getAmountOfMoney();
        bookingDto.setAmountPaid(totalPaid);
        if(totalPaid >=bookingDto.getTotalAmount()){
            bookingDto.setStatus(StatusOfBooking.COMPLETED);
            bookingService.updateBooking(bookingDto);
            logger.info("Total amount3: {}", bookingDto.getTotalAmount());
            TripDto tripDto = bookingDto.getTripDto();
            tripDto.setActualPassenger(bookingDto.getNumberOfPassengers());
            bookingService.genarateTickets(bookingDto);
        }
        return "redirect:/all-booking";
    }
}
