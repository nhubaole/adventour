package com.adventour.web.mapper;

import com.adventour.web.dto.BookingDto;
import com.adventour.web.dto.CustomerDto;
import com.adventour.web.dto.TourDto;
import com.adventour.web.dto.TripDto;
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

        TourDto tourDto = mapToTourDto(trip.getTour());
        tripDto.setTourDto(tourDto);

        tripDto.setPassengers(trip.getPassengers());
        return tripDto;
    }
    public Trip mapToTrip(TripDto tripDto){
        Trip trip = new Trip();
        trip.setId(tripDto.getId());
        trip.setSlots(tripDto.getSlots());
        trip.setStartDate(tripDto.getStartDate());
        trip.setEndDate(tripDto.getEndDate());
        trip.setPriceTicket(trip.getPriceTicket());
        trip.setDiscount(tripDto.getDiscount());

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
//                .isMale(customerDto.isMale())
                .nationality(customerDto.getNationality())
                .imagesCustomer(customerDto.getImagesCustomer())
                .build();
    }
    public Booking mapToBooking(BookingDto bookingDto) {
        Booking booking = new Booking();
        booking.setId(bookingDto.getId());
        booking.setBookingDate(bookingDto.getBookingDate());
        booking.setStatus(bookingDto.getStatus());
        booking.setTotalAmount(bookingDto.getTotalAmount());

        Trip trip = mapToTrip(bookingDto.getTripDto());
        booking.setTrip(trip);

        Customer customer = mapToCustomer(bookingDto.getCustomerDto());
        booking.setCustomer(customer);

        //con ds passenger ???
        return booking;
    }

    public BookingDto mapToBookingDto(Booking booking) {

        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(booking.getId());
        bookingDto.setBookingDate(booking.getBookingDate());
        bookingDto.setStatus(booking.getStatus());
        int numberOfPassenger = booking.getNumberAdult() + booking.getNumberBaby() + booking.getNumberChildren();
        bookingDto.setNumberOfPassengers(numberOfPassenger);
        bookingDto.setTotalAmount(booking.getTotalAmount());

        int amountPaid = 0;
        for (PaymentInformation paymentInformation : booking.getPaymentInformation()){
            amountPaid += paymentInformation.getAmountOfMoney();
        }
        bookingDto.setAmountPaid(amountPaid);

        TripDto tripDto = mapToTripDto(booking.getTrip());
        bookingDto.setTripDto(tripDto);

        CustomerDto customerDto = mapToCustomerDto(booking.getCustomer());
        bookingDto.setCustomerDto(customerDto);



        return bookingDto;
    }

}
