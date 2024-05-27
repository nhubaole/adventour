package com.adventour.web.mapper;
import com.adventour.web.dto.*;
import com.adventour.web.enums.StatusOfTicket;
import com.adventour.web.models.*;
import org.springframework.stereotype.Component;

@Component
public class Mapper {
    public TourDto mapToTourDto(Tour tour) {
        return TourDto.builder()
                .id(tour.getId())
                .tourName(tour.getTourName())
                .departureLocation(tour.getDepartureLocation())
                .estimatedPrice(tour.getEstimatedPrice())
                .numberOfDays(tour.getNumberOfDays())
                .numberOfNights(tour.getNumberOfNights())
//                .typeOfTour(tour.getTypeOfTour())
                .build();
    }
    public Tour maptoTour(TourDto tourDto) {
        return Tour.builder()
                .id(tourDto.getId())
                .tourName(tourDto.getTourName())
                .departureLocation(tourDto.getDepartureLocation())
                .estimatedPrice(tourDto.getEstimatedPrice())
                .numberOfDays(tourDto.getNumberOfDays())
                .numberOfNights(tourDto.getNumberOfNights())
                .typeOfTour(tourDto.getTypeOfTour())
                .build();
    }
    public TripDto mapToTripDto(Trip trip){
        TripDto tripDto = new TripDto();
        tripDto.setId(trip.getId());
        tripDto.setSlots(trip.getSlots());
        tripDto.setStartDate(trip.getStartDate());
        tripDto.setEndDate(trip.getEndDate());
        tripDto.setPriceTicket(trip.getPriceTicket());
        tripDto.setDiscount(trip.getDiscount());
        tripDto.setTripType(trip.getTripType());

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
            tripDto.setTripType("Khuyến mãi");
        }
        else
        {
            tripDto.setTripType("Thông thường");
        }

        Tour tour = maptoTour(tripDto.getTourDto());
        trip.setTour(tour);

        trip.setPassengers(tripDto.getPassengers());
        return trip;
    }

    public CustomerDto mapToCustomerDto (Customer customer){
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

        int price = booking.getTrip().getPriceTicket();
        booking.setTotalAmount( (int)(booking.getNumberAdult() * price + booking.getNumberChildren() * price * 0.5));

        Customer customer = mapToCustomer(bookingDto.getCustomerDto());
        booking.setCustomer(customer);

        return booking;
    }

    public BookingDto mapToBookingDto(Booking booking) {

        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(booking.getId());
        bookingDto.setBookingDate(booking.getBookingDate());
        bookingDto.setStatus(booking.getStatus());
        bookingDto.setNumberChildren(booking.getNumberChildren());
        bookingDto.setNumberAdult(booking.getNumberAdult());
        bookingDto.setNumberBaby(booking.getNumberBaby());

        int numberOfPassenger = booking.getNumberAdult() + booking.getNumberBaby() + booking.getNumberChildren();
        bookingDto.setNumberOfPassengers(numberOfPassenger);

        bookingDto.setTotalAmount(booking.getTotalAmount());

        TripDto tripDto = mapToTripDto(booking.getTrip());
        bookingDto.setTripDto(tripDto);

        CustomerDto customerDto = mapToCustomerDto(booking.getCustomer());
        bookingDto.setCustomerDto(customerDto);

        return bookingDto;
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

        BookingDto booking = mapToBookingDto(passenger.getBooking());
        passengerDto.setBookingDto(booking);

        passengerDto.setTripDto(booking.getTripDto());

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
        ticketDto.setBookingDto(mapToBookingDto(ticket.getBooking()));

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
}
