package com.adventour.web.service.impl;

import com.adventour.web.controller.LoginController;
import com.adventour.web.dto.TicketDto;
import com.adventour.web.enums.StatusOfTicket;
import com.adventour.web.mapper.Mapper;
import com.adventour.web.models.Booking;
import com.adventour.web.models.Passenger;
import com.adventour.web.models.Ticket;
import com.adventour.web.repository.BookingRepository;
import com.adventour.web.repository.TicketRepository;
import com.adventour.web.service.TicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service

public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final BookingRepository bookingRepository;
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);


    private final Mapper mapper;

    public TicketServiceImpl(TicketRepository ticketRepository, BookingRepository bookingRepository, Mapper mapper) {
        this.ticketRepository = ticketRepository;
        this.bookingRepository = bookingRepository;
        this.mapper = mapper;
    }

    @Override
    public Ticket addNewTicket(TicketDto ticketDto) {
        Ticket ticket = mapper.mapToTicket(ticketDto);
        return ticketRepository.save(ticket);
    }

    @Override
    public Set<TicketDto> getTicketsByIdBooking(Long idBooking) {
        Set<TicketDto> ticketDtos = new HashSet<>();
        List<Object[]> results = ticketRepository.getTicketByIdBooking(idBooking);
        logger.info("xong query+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        for(Object[] row : results){
            long id = (long) row[0];
            LocalDateTime usedAt = (LocalDateTime) row[1];
            long idPass  = (long) row[2];
            String namePas = (String) row[3];
            String cccd = (String) row[4];
            boolean isMale = (boolean) row[5];
            String type = (String) row[6];
            LocalDate dob = (LocalDate) row[7];

            Passenger passenger = new Passenger(idPass, namePas, cccd, isMale, type, dob, null, null);

            Ticket ticket = new Ticket(id,usedAt,null, passenger);
            TicketDto ticketDto = mapper.mapToTicketDto(ticket);
            ticketDto.setCode(generateTickeCode(ticketDto.getId()));
            ticketDtos.add(ticketDto);
        }

        return ticketDtos;
    }

    @Override
    public TicketDto getTicketDetail(Long idTicket) {
        Ticket ticket = ticketRepository.findById(idTicket).orElse(null);
        if(ticket!= null){
            TicketDto ticketDto = mapper.mapToTicketDto(ticket);
            ticketDto.setCode(generateTickeCode(ticketDto.getId()));
            return  ticketDto;
        }
        return null;
    }

    public  String generateTickeCode (Long idTicket){
        String formattedId = String.format("%06d", idTicket);
        return "TSK-" + formattedId.substring(0, 3) + "-" + formattedId.substring(3);
    }
}
