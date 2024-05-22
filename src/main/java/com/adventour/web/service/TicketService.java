package com.adventour.web.service;

import com.adventour.web.dto.TicketDto;
import com.adventour.web.models.Ticket;

import java.util.List;

public interface TicketService {
    public Ticket addNewTicket(TicketDto ticketDto);

    public List<TicketDto> getTicketsByIdBooking(Long idBooking);
}
