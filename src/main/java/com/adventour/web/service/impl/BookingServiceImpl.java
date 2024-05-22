package com.adventour.web.service.impl;

import com.adventour.web.dto.*;
import com.adventour.web.enums.StatusOfBooking;
import com.adventour.web.mapper.Mapper;
import com.adventour.web.models.*;
import com.adventour.web.repository.BookingRepository;
import com.adventour.web.repository.CustomerRepository;
import com.adventour.web.repository.PassengerRepository;
import com.adventour.web.repository.TripRepository;
import com.adventour.web.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final Mapper mapper;

    private final CustomerService customerService;
    private final PassengerService passengerService;
    private final PaymentInformationService paymentInformationService;

    private final TicketService ticketService;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository, Mapper mapper, CustomerService customerService, PassengerService passengerService, PaymentInformationService paymentInformationService, TicketService ticketService) {
        this.bookingRepository = bookingRepository;
        this.mapper = mapper;
        this.customerService = customerService;
        this.passengerService = passengerService;
        this.paymentInformationService = paymentInformationService;
        this.ticketService = ticketService;
    }


    @Override
    public List<BookingDto> getListBooking() {
        List<BookingDto> bookingDtoList = new ArrayList<>();
        List<Booking> bookings = bookingRepository.findAll();
        for(Booking booking : bookings){
            BookingDto bookingDto = mapper.mapToBookingDto(booking);

            Set<PassengerDto> passengerDtos = getPassengerOfBooking(booking.getId());
            bookingDto.setPassengerDtos(passengerDtos);

            Set<PaymentInformationDto> paymentInformationDtos = getPaymentOfBooking(booking.getId());
            bookingDto.setPaymentInformationDtos(paymentInformationDtos);

            int amountPaid = 0;
            for (PaymentInformationDto paymentInformation : paymentInformationDtos){
                amountPaid += paymentInformation.getAmountOfMoney();
            }
            bookingDto.setAmountPaid(amountPaid);

            if(bookingDto.getStatus() == StatusOfBooking.COMPLETED){
                //TODO: Set<Ticket>;
                Set<TicketDto> ticketDtos = getTicketOfBooking(booking.getId());
                bookingDto.setTicketDtos(ticketDtos);
            }

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
    public BookingDto searchBooking() {
        return null;
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
    public Booking updateBooking(BookingDto bookingDto) {
        if(validateBooking(bookingDto)) {
            Booking booking = mapper.mapToBooking(bookingDto);

            //tinh tong tien
            int price = booking.getTrip().getPriceTicket();
            booking.setTotalAmount((int) (booking.getNumberAdult() * price + booking.getNumberChildren() * price * 0.5));

            //TODO: luu danh sach passener, payment;


            return bookingRepository.save(booking);
        }
        return  null;
    }

    @Override
    public List<BookingDto> getBookingsByCustomerId(Long id) {
        List<BookingDto> result = new ArrayList<>();
        List<Booking> bookings = bookingRepository.findByCustomerId(id);
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
        return result;
    }

    @Override
    public List<BookingDto> getBookingsByTripId(Long id) {
        List<BookingDto> result = new ArrayList<>();
        List<Booking> bookings = bookingRepository.findByTripId(id);
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
        //TODO: get ticket off Bokking
        return ticketService.getTicketsByIdBooking(idBooking);
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
