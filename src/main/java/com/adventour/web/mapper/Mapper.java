package com.adventour.web.mapper;

import com.adventour.web.controller.TourController;
import com.adventour.web.dto.*;
import com.adventour.web.dto.*;
import com.adventour.web.enums.StatusOfTicket;
import com.adventour.web.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class Mapper {
    private static Logger logger = LoggerFactory.getLogger(TourController.class);

    public TourDto mapToTourDto(Tour tour) {
        TourDto tourDto = TourDto.builder()
                .id(tour.getId())
                .tourName(tour.getTourName())
                .departureLocation(tour.getDepartureLocation())
                .estimatedPrice(tour.getEstimatedPrice())
                .numberOfDays(tour.getNumberOfDays())
                .numberOfNights(tour.getNumberOfNights())
                .code(String.format("T%07d",tour.getId()))
                .typeOfTour(tour.getTypeOfTour())
                .build();
        if (tour.getSchedules() != null) {
            List<ScheduleDto> scheduleDtoList = new ArrayList<>();
            for (Schedule schedule : tour.getSchedules()) {
                ScheduleDto scheduleDto = mapToScheduleDto(schedule);
                scheduleDtoList.add(scheduleDto);
                logger.info("====================scheduleDto " + scheduleDto.toString());
            }
            tourDto.setSchedules(scheduleDtoList);
            logger.info("====================size " + tourDto.getSchedules().size());
        }

        return tourDto;
    }
    public Tour mapToTour(TourDto tourDto) {
        Tour tour = new Tour();

        tour.setId(tourDto.getId());
        tour.setTourName(tourDto.getTourName());
        tour.setEstimatedPrice(tourDto.getEstimatedPrice() == null ? 0 : tourDto.getEstimatedPrice());
        tour.setNumberOfDays(tourDto.getNumberOfDays()== null ? 0 :tourDto.getNumberOfDays());
        tour.setNumberOfNights(tourDto.getNumberOfNights());
        tour.setTypeOfTour(tourDto.getTypeOfTour());
        tour.setDepartureLocation(tourDto.getDepartureLocation());

        if (tourDto.getSchedules() != null) {
            for (ScheduleDto scheduleDto : tourDto.getSchedules()) {
                logger.info(String.valueOf("====================scheduleDto " + scheduleDto.toString()));

                Schedule schedule = mapToSchedule(scheduleDto);
                schedule.setTour(tour);
                tour.getSchedules().add(schedule);
                logger.info(String.valueOf("====================size " + tour.getSchedules().size()));
            }
        }

        return tour;
    }
    public TripDto mapToTripDto(Trip trip){
        TripDto tripDto = new TripDto();
        tripDto.setId(trip.getId());
        tripDto.setCode(String.format("C%08d", trip.getId()));
        tripDto.setSlots(trip.getSlots());
        tripDto.setStartDate(trip.getStartDate());
        tripDto.setEndDate(trip.getEndDate());
        tripDto.setPriceTicket(trip.getPriceTicket());
        tripDto.setDiscount(trip.getDiscount());
        tripDto.setIsActive(trip.getStartDate().isAfter(LocalDateTime.now()));
        int actualPrice = (int) (trip.getPriceTicket() * (1 - trip.getDiscount()*0.01));
        tripDto.setActualPrice(actualPrice);
        tripDto.setTripType(trip.getTripType());
        tripDto.setStatus(trip.getStatus());

        TourDto tourDto = mapToTourDto(trip.getTour());
        tripDto.setTourDto(tourDto);

        tripDto.setName(tourDto.getTourName());

        tripDto.setPassengers(trip.getPassengers());

        return tripDto;
    }
    public Trip mapToTrip(TripDto tripDto){
        Trip trip = new Trip();
        trip.setId(tripDto.getId());
        trip.setSlots(tripDto.getSlots());
        trip.setStartDate(tripDto.getStartDate());
        trip.setEndDate(tripDto.getEndDate());
        trip.setPriceTicket(tripDto.getPriceTicket());
        trip.setDiscount(tripDto.getDiscount());
        if(tripDto.getDiscount() > 0){
            trip.setTripType("Khuyến mãi");
        }
        else
        {
            trip.setTripType("Thông thường");
        }
        trip.setStatus(tripDto.getStatus());
        Tour tour = mapToTour(tripDto.getTourDto());
        trip.setTour(tour);

        trip.setPassengers(tripDto.getPassengers());
        return trip;
    }

    public CustomerDto mapToCustomerDto (Customer customer){
        return CustomerDto.builder()
                .id(customer.getId())
                .code(String.format("KH%07d",customer.getId()))
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

    public Customer mapToCustomer(CustomerDto customerDto) {
        return Customer.builder()
                .id(customerDto.getId())
                .nameCustomer(customerDto.getNameCustomer())
                .addressCustomer(customerDto.getAddressCustomer())
                .phoneNumber(customerDto.getPhoneNumber())
                .email(customerDto.getEmail())
                .dateOfBirth(customerDto.getDateOfBirth())
                .cccd(customerDto.getCccd())
                .isMale(customerDto.getIsMale())
                .nationality(customerDto.getNationality())
                .imagesCustomer(customerDto.getImagesCustomer())
                .build();
    }
    public Booking mapToBooking(BookingDto bookingDto) {
        Booking booking = new Booking();
        booking.setId(bookingDto.getId());
        booking.setBookingDate(bookingDto.getBookingDate());
        booking.setStatus(bookingDto.getStatus());
        booking.setNumberAdult(bookingDto.getNumberAdult());
        booking.setNumberBaby(bookingDto.getNumberBaby());
        booking.setNumberChildren(bookingDto.getNumberChildren());

        Trip trip = mapToTrip(bookingDto.getTripDto());
        booking.setTrip(trip);

        int price = (int) (trip.getPriceTicket() * (1 - trip.getDiscount()*0.01));
        booking.setTotalAmount( (int)(booking.getNumberAdult() * price + booking.getNumberChildren() * price * 0.5));

        Customer customer = mapToCustomer(bookingDto.getCustomerDto());
        booking.setCustomer(customer);

        return booking;
    }

    public BookingDto mapToBookingDto(Booking booking) {

        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(booking.getId());
        bookingDto.setCode(String.format("P%08d", booking.getId()));
        bookingDto.setBookingDate(booking.getBookingDate());
        bookingDto.setStatus(booking.getStatus());
        bookingDto.setNumberChildren(booking.getNumberChildren());
        bookingDto.setNumberAdult(booking.getNumberAdult());
        bookingDto.setNumberBaby(booking.getNumberBaby());

        int numberOfPassenger = booking.getNumberAdult() + booking.getNumberBaby() + booking.getNumberChildren();
        bookingDto.setNumberOfPassengers(numberOfPassenger);

        bookingDto.setTotalAmount(booking.getTotalAmount());
        bookingDto.setAmountPaidFormat(String.format("%,d", bookingDto.getTotalAmount()));
        if(booking.getTrip() !=null){
            TripDto tripDto = mapToTripDto(booking.getTrip());
            bookingDto.setTripDto(tripDto);
        }

        CustomerDto customerDto = mapToCustomerDto(booking.getCustomer());
        bookingDto.setCustomerDto(customerDto);

        return bookingDto;
    }
    public LocationDto mapToLocationDto(Location location) {
        return LocationDto.builder()
                .id(location.getId())
                .code(String.format("DD%07d",location.getId()))
                .nameLocation(location.getNameLocation())
                .address(location.getAddress())
                .description(location.getDescription())
                .images(location.getImages())
                .code(String.format("DD%07d",location.getId()))
                .build();
    }

    public Passenger mapToPassenger (PassengerDto passengerDto){
        Passenger passenger = new Passenger();
        passenger.setId(passengerDto.getId());
        passenger.setNamePassenger(passengerDto.getNamePassenger());
        passenger.setMale(passengerDto.isMale());
        passenger.setDateOfBirth(passengerDto.getDateOfBirth());
        passenger.setCccd(passengerDto.getCccd());
        passenger.setType(passengerDto.getType());

        Booking booking = mapToBooking(passengerDto.getBookingDto());
        passenger.setBooking(booking);

        passenger.setTrip(booking.getTrip());

        return passenger;
    };

    public PassengerDto mapToPassengerDto (Passenger passenger){
        PassengerDto passengerDto = new PassengerDto();
        passengerDto.setId(passenger.getId());
        passengerDto.setNamePassenger(passenger.getNamePassenger());
        passengerDto.setMale(passenger.isMale());
        passengerDto.setDateOfBirth(passenger.getDateOfBirth());
        passengerDto.setCccd(passenger.getCccd());
        passengerDto.setType(passenger.getType());

        if(passenger.getBooking() != null){
            BookingDto booking = mapToBookingDto(passenger.getBooking());
            passengerDto.setBookingDto(booking);
            passengerDto.setTripDto(booking.getTripDto());
        }

        return passengerDto;
    };

    public PaymentInformationDto mapToPaymentInformationDto(PaymentInformation paymentInformation){
        BookingDto bookingDto = mapToBookingDto(paymentInformation.getBooking());
        return  PaymentInformationDto.builder()
                .id(paymentInformation.getId())
                .amountOfMoney(paymentInformation.getAmountOfMoney())
                .paymentMethod(paymentInformation.getPaymentMethod())
                .paymentTime(paymentInformation.getPaymentTime())
                .bookingDto(bookingDto)
                .build();
    }

    public PaymentInformation mapToPaymentInformation( PaymentInformationDto paymentInformationDto) {

        PaymentInformation paymentInformation = new PaymentInformation();

        paymentInformation.setId(paymentInformationDto.getId());
        paymentInformation.setPaymentMethod(paymentInformationDto.getPaymentMethod());
        paymentInformation.setAmountOfMoney(paymentInformationDto.getAmountOfMoney());
        paymentInformation.setPaymentTime(paymentInformationDto.getPaymentTime());

        Booking booking = mapToBooking(paymentInformationDto.getBookingDto());
        paymentInformation.setBooking(booking);
        return paymentInformation;
    }

    public TicketDto mapToTicketDto(Ticket ticket){
        TicketDto ticketDto = new TicketDto();
        ticketDto.setId(ticket.getId());
        ticketDto.setUsedAt(ticket.getUsedAt());

        ticketDto.setPassengerDto(mapToPassengerDto(ticket.getPassenger()));

        if(ticket.getBooking() != null){
            ticketDto.setBookingDto(mapToBookingDto(ticket.getBooking()));
        }

        ticketDto.setNamePassenger(ticket.getPassenger().getNamePassenger());

        if(ticket.getUsedAt() == null){
            ticketDto.setStatusTicket(StatusOfTicket.UNUSED);
        }
        else {
            ticketDto.setStatusTicket(StatusOfTicket.USED);
        }

        ticketDto.setTypeTicket(ticket.getPassenger().getType());

        return ticketDto;
    }

    public Ticket mapToTicket (TicketDto ticketDto){
        Ticket ticket = new Ticket();
        ticket.setId(ticket.getId());
        ticket.setUsedAt(ticketDto.getUsedAt());
        ticket.setBooking(mapToBooking(ticketDto.getBookingDto()));
        ticket.setPassenger(mapToPassenger(ticketDto.getPassengerDto()));
        return ticket;
    }
    public Location mapToLocation(LocationDto location) {
        return Location.builder()
                .id(location.getId())
                .nameLocation(location.getNameLocation())
                .address(location.getAddress())
                .description(location.getDescription())
                .images(location.getImages())
                .build();
    }
    public Schedule mapToSchedule(ScheduleDto scheduleDto) {
        return Schedule.builder()
                .id(scheduleDto.getId())
                .dayOfSchedule(scheduleDto.getDayOfSchedule())
                .startLocation(mapToLocation(scheduleDto.getStartLocation()))
                .endLocation((mapToLocation(scheduleDto.getEndLocation())))
                .listHotel(scheduleDto.getHotels())
                .listRestaurant(scheduleDto.getRestaurants())
                .listVehicle(scheduleDto.getVehicles())
                .listOtherService(scheduleDto.getOtherServices())
                .build();
    }
    public ScheduleDto mapToScheduleDto(Schedule schedule) {
        return ScheduleDto.builder()
                .id(schedule.getId())
                .dayOfSchedule(schedule.getDayOfSchedule())
                .startLocation(mapToLocationDto(schedule.getStartLocation()))
                .endLocation(mapToLocationDto(schedule.getEndLocation()))
                .hotels(schedule.getListHotel())
                .restaurants(schedule.getListRestaurant())
                .vehicles(schedule.getListVehicle())
                .otherServices(schedule.getListOtherService())
                .build();
    }
}
