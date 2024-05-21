package com.adventour.web.mapper;

import com.adventour.web.controller.TourController;
import com.adventour.web.dto.*;
import com.adventour.web.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Mapper {
    private static Logger logger = LoggerFactory.getLogger(TourController.class);

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
    public Tour mapToTour(TourDto tourDto) {
        Tour tour = new Tour();

        tour.setId(tourDto.getId());
        tour.setTourName(tourDto.getTourName());
        tour.setEstimatedPrice(tourDto.getEstimatedPrice());
        tour.setNumberOfDays(tourDto.getNumberOfDays());
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

        Tour tour = mapToTour(tripDto.getTourDto());
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
    public LocationDto mapToLocationDto(Location location) {
        return LocationDto.builder()
                .id(location.getId())
                .nameLocation(location.getNameLocation())
                .address(location.getAddress())
                .description(location.getDescription())
                .images(location.getImages())
                .build();
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
}
