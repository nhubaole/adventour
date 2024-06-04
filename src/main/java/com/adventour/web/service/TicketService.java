package com.adventour.web.service;

import com.adventour.web.dto.TicketDto;
import com.adventour.web.models.Ticket;

import java.util.List;
import java.util.Set;

public interface TicketService {
    public Ticket addNewTicket(TicketDto ticketDto);

    public Set<TicketDto> getTicketsByIdBooking(Long idBooking);

    public TicketDto getTicketDetail (Long idTicket);
}
