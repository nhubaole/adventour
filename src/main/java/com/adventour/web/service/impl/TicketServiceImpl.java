package com.adventour.web.service.impl;

import com.adventour.web.dto.TicketDto;
import com.adventour.web.enums.StatusOfTicket;
import com.adventour.web.mapper.Mapper;
import com.adventour.web.models.Booking;
import com.adventour.web.models.Ticket;
import com.adventour.web.repository.BookingRepository;
import com.adventour.web.repository.TicketRepository;
import com.adventour.web.service.TicketService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final BookingRepository bookingRepository;

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
        Booking booking = bookingRepository.findById(idBooking).orElse(null);
        Set<TicketDto> ticketDtos = new HashSet<>();
        if(booking != null){
            List<Ticket> tickets = ticketRepository.findByBooking(booking);
            for(Ticket ticket : tickets){
                TicketDto ticketDto = mapper.mapToTicketDto(ticket);
                ticketDto.setCode(generateTickeCode(ticketDto.getId()));
                ticketDtos.add(ticketDto);
            }
            return ticketDtos;
        }

        return null;
    }

    @Override
    public TicketDto getTicketDetail(Long idTicket) {
        Ticket ticket = ticketRepository.findById(idTicket).orElse(null);
        if(ticket!= null){
            return  mapper.mapToTicketDto(ticket);
        }
        return null;
    }

    public  String generateTickeCode (Long idTicket){
        String formattedId = String.format("%06d", idTicket);
        return "VE-" + formattedId.substring(0, 3) + "-" + formattedId.substring(3);
    }
}
