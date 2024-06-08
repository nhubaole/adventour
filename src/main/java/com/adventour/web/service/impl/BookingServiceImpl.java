package com.adventour.web.service.impl;

import com.adventour.web.controller.LoginController;
import com.adventour.web.dto.*;
import com.adventour.web.enums.StatusOfBooking;
import com.adventour.web.mapper.Mapper;
import com.adventour.web.models.*;
import com.adventour.web.repository.*;
import com.adventour.web.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final Mapper mapper;
    private final TripRepository tripRepository;
    private final CustomerService customerService;
    private final CustomerRepository customerRepository;
    private final PassengerService passengerService;
    private final PaymentInformationService paymentInformationService;

    private final TicketService ticketService;
    private static final Logger logger = LoggerFactory.getLogger(BookingServiceImpl.class);

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository, Mapper mapper, TripRepository tripRepository, CustomerService customerService, CustomerRepository customerRepository, PassengerService passengerService, PaymentInformationService paymentInformationService, PaymentInformationRepository paymentInformationRepository, TicketService ticketService) {
        this.bookingRepository = bookingRepository;
        this.mapper = mapper;
        this.tripRepository = tripRepository;
        this.customerService = customerService;
        this.customerRepository = customerRepository;
        this.passengerService = passengerService;
        this.paymentInformationService = paymentInformationService;
        this.ticketService = ticketService;
    }


    @Override
    @Transactional
    public List<BookingDto> getListBooking() {
        List<BookingDto> bookingDtoList = new ArrayList<>();
        List<Object[]> bookings = bookingRepository.findBookingsWithCustomer();

        for(Object[] result : bookings){
            Long id = (Long) result[0]; // id
            LocalDateTime bookingDate = (LocalDateTime) result[1]; // bookingDate
            StatusOfBooking status = (StatusOfBooking) result[2]; // status
            int totalAmount = (int) result[3]; // totalAmount
            int numberAdult = (int) result[4]; // numberAdult
            int numberChildren = (int) result[5]; // numberChildren
            int numberBaby = (int) result[6]; // numberBaby
            Customer customer = (Customer) result[7]; // customer
            Booking booking = new Booking(id, bookingDate,status,totalAmount, numberAdult,numberChildren, numberBaby,null,customer,null,null, null);
            BookingDto bookingDto = mapper.mapToBookingDto(booking);

            Set<PaymentInformationDto> paymentInformationDtos = getPaymentOfBooking(booking.getId());

            int amountPaid = 0;
            for (PaymentInformationDto paymentInformation : paymentInformationDtos){
                amountPaid += paymentInformation.getAmountOfMoney();
            }
            bookingDto.setAmountPaid(amountPaid);

            bookingDtoList.add(bookingDto);

        }
        return  bookingDtoList;
    }

    @Override
    public List<BookingDto> findByBookingDateBetween(LocalDateTime start, LocalDateTime end) {
        List<Booking> bookings = bookingRepository.findByBookingDateBetween(start, end);
        List<BookingDto> bookingDtoList = new ArrayList<>();
        if (!bookings.isEmpty()) {
            for (Booking booking : bookings) {
                BookingDto bookingDto = mapper.mapToBookingDto(booking);

                Set<PassengerDto> passengerDtos = getPassengerOfBooking(booking.getId());
                bookingDto.setPassengerDtos(passengerDtos);

                //TODO: Set<Ticket>;

                Set<TicketDto> ticketDtos = getTicketOfBooking(booking.getId());
                bookingDto.setTicketDtos(ticketDtos);

                bookingDtoList.add(bookingDto);
            }
        }
        return bookingDtoList;
    }

    @Override
    public List<BookingDto> searchBooking(String search) {
        //search theo ID hoawcj name customer
        List<BookingDto> result = new ArrayList<>();
        List<BookingDto> allBookingDto = getListBooking();
        for (BookingDto bookingDto : allBookingDto){
            if(bookingDto.getId().toString().contains(search)) {
                result.add(bookingDto);
            } else {
                if(bookingDto.getCustomerDto().getNameCustomer().toLowerCase().contains(search.toLowerCase())){
                    result.add(bookingDto);
                }
            }
        }
        return result;
    }

    @Override
    public Booking addNewBooking(BookingDto bookingDto) {
        Set<PassengerDto> passengerDtos = bookingDto.getPassengerDtos();
        Set<PaymentInformationDto> paymentInformationDtos = bookingDto.getPaymentInformationDtos();
        if(validateBooking(bookingDto)){
            Booking booking = mapper.mapToBooking(bookingDto);

            //customer moi
            if(bookingDto.getCustomerDto().getId() == null) {
                CustomerDto customerDto = bookingDto.getCustomerDto();
                Customer customer = customerService.addNewCustomer(customerDto);
                booking.setCustomer(customer);
            }

            booking =  bookingRepository.save(booking);

            if(booking != null){
                bookingDto = mapper.mapToBookingDto(booking);
                if(addSetPassengers(bookingDto, passengerDtos)){
                    if(addSetPayments(bookingDto, paymentInformationDtos)){
                        return booking;
                    }
                }
            }
            return null;
        }
        return null;
    }

    @Override
    @Transactional
    public BookingDto findById(Long id) {
        BookingDto bookingDto = new BookingDto();
        Booking booking = bookingRepository.findById(id).orElse(null);
        if (booking != null) {
            bookingDto =  mapper.mapToBookingDto(booking);

            //TODO: lấy set<Payment> tính lại amountPaid()
            Set<PaymentInformationDto> payments = getPaymentOfBooking(booking.getId());
            int amountPaid  = 0;

            for(PaymentInformationDto payment : payments){
                amountPaid += payment.getAmountOfMoney();
            }

            bookingDto.setAmountPaid(amountPaid);
        }
        return bookingDto;
    }

    @Override
    public BookingDto findBookingSimpler(Long id) {
        BookingDto bookingDto = new BookingDto();
        Booking booking = bookingRepository.findById(id).orElse(null);
        if (booking != null) {
            bookingDto =  mapper.mapToBookingDto(booking);
        }
        return bookingDto;
    }

    @Override
    public Booking updateBooking(BookingDto bookingDto) {
        if(validateBooking(bookingDto)) {
            Booking booking = mapper.mapToBooking(bookingDto);
            //TODO: luu danh sach passener, payment;
            return bookingRepository.save(booking);
        }
        return  null;
    }


    @Override
    public List<BookingDto> getBookingsByCustomerId(Long id) {
        List<BookingDto> result = new ArrayList<>();
        Customer customer = customerRepository.findById(id).orElse(null);
        if(customer != null){
            List<Booking> bookings = bookingRepository.findByCustomer(customer);
            if(!bookings.isEmpty()){
                for(Booking booking : bookings){
                    BookingDto bookingDto = mapper.mapToBookingDto(booking);

                    Set<PaymentInformationDto> paymentInformationDtos = getPaymentOfBooking(booking.getId());

                    int amountPaid = 0;
                    for (PaymentInformationDto paymentInformation : paymentInformationDtos){
                        amountPaid += paymentInformation.getAmountOfMoney();
                    }
                    bookingDto.setAmountPaid(amountPaid);

                    result.add(bookingDto);
                }
            }
        }
        return result;
    }

    @Override
    public List<BookingDto> getBookingsByTripId(Long id) {
        List<BookingDto> result = new ArrayList<>();
        Trip trip = tripRepository.findById(id).orElse(null);
        if(trip != null){
            List<Booking> bookings = bookingRepository.findByTrip(trip);
            if(!bookings.isEmpty()){
                for(Booking booking : bookings){
                    BookingDto bookingDto = mapper.mapToBookingDto(booking);
                    Set<PaymentInformationDto> paymentInformationDtos = getPaymentOfBooking(booking.getId());

                    int amountPaid = 0;
                    for (PaymentInformationDto paymentInformation : paymentInformationDtos){
                        amountPaid += paymentInformation.getAmountOfMoney();
                    }
                    bookingDto.setAmountPaid(amountPaid);

                    result.add(bookingDto);
                }
            }
        }
        return result;
    }

    @Override
    public Set<PassengerDto> getPassengerOfBooking(Long idBooking) {
        return passengerService.getPassengersByIdBooking(idBooking);
    }

    @Override
    public Set<PaymentInformationDto> getPaymentOfBooking(Long idBooking) {
        return paymentInformationService.getPaymentInforByIdBooking(idBooking);

    }

    @Override
    public Set<TicketDto> getTicketOfBooking(Long idBooking) {
        return ticketService.getTicketsByIdBooking(idBooking);
    }
    @Override
    public PaymentInformation addBookingPayment(PaymentInformationDto paymentInformationDto, BookingDto bookingDto) {
        paymentInformationDto.setBookingDto(bookingDto);
        PaymentInformation payment = paymentInformationService.addNewPaymentInformation(paymentInformationDto);
        int totalPaid = bookingDto.getAmountPaid() + paymentInformationDto.getAmountOfMoney();
        bookingDto.setAmountPaid(totalPaid);
        if(totalPaid >= bookingDto.getTotalAmount()){
            bookingDto.setStatus(StatusOfBooking.COMPLETED);
            TripDto tripDto = bookingDto.getTripDto();
            tripDto.setActualPassenger(bookingDto.getNumberOfPassengers());
            updateBooking(bookingDto);
            genarateTickets(bookingDto);
        }
        return  payment;
    }

    @Override
    public boolean validateBooking(BookingDto bookingDto) {
        if(bookingDto.getTripDto() != null
        && bookingDto.getCustomerDto() !=null
        && bookingDto.getBookingDate() != null
        && bookingDto.getNumberChildren() >= 0
        && bookingDto.getNumberBaby() >= 0
        && bookingDto.getNumberAdult() >= 0){
            return true;
        }

        return false;
    }

    @Override
    public Set<TicketDto> genarateTickets(BookingDto bookingDto) {
        Set<TicketDto> result  = new HashSet<>();
        Set<PassengerDto> passengerDtos = getPassengerOfBooking(bookingDto.getId());

        for(PassengerDto passengerDto : passengerDtos){
            TicketDto ticketDto = new TicketDto();
            ticketDto.setPassengerDto(passengerDto);
            ticketDto.setBookingDto(bookingDto);

            Ticket ticket = ticketService.addNewTicket(ticketDto);

            ticketDto = mapper.mapToTicketDto(ticket);
            result.add(ticketDto);

        }

        return result;
    }

    @Override
    public Booking deleteBooking(BookingDto bookingDto) {
        bookingDto.setStatus(StatusOfBooking.CANCELLED);
        return updateBooking(bookingDto);
    }


    public boolean addSetPassengers(BookingDto bookingDto, Set<PassengerDto> passengerDtos){
        if(!passengerDtos.isEmpty()){
            for(PassengerDto passengerDto : passengerDtos){
                passengerDto.setBookingDto(bookingDto);
                Passenger passenger = passengerService.addNewPassenger(passengerDto);
                if(passenger == null){
                    return false;
                }
                bookingDto.getPassengerDtos().add(mapper.mapToPassengerDto(passenger));
            }
            return true;
        }
        return false;
    }

    private boolean addSetPayments(BookingDto bookingDto, Set<PaymentInformationDto> paymentInformationDtos) {
        if(!paymentInformationDtos.isEmpty()){
            int amountPaid = 0;
            for(PaymentInformationDto paymentInformationDto : paymentInformationDtos){
                paymentInformationDto.setBookingDto(bookingDto);
                PaymentInformation payment = paymentInformationService.addNewPaymentInformation(paymentInformationDto);
                if(payment == null){
                    return false;
                }
                bookingDto.getPaymentInformationDtos().add(mapper.mapToPaymentInformationDto(payment));
                 amountPaid +=  payment.getAmountOfMoney();
            }
            bookingDto.setAmountPaid((int) amountPaid);

            return true;
        }
        return false;
    }



}
