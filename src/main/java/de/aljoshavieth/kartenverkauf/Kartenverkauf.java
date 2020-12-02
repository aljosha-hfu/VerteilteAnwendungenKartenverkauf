package de.aljoshavieth.kartenverkauf;

import de.aljoshavieth.kartenverkauf.exceptions.TicketException;

public class Kartenverkauf {
    private Ticket[] tickets = new Ticket[100];
    private boolean acceptReservations = true;

    private synchronized void sellTicket(int seat) throws TicketException {
        if (ticketNotExists(seat)) {
            throw new TicketException("Ticket does not exist!");
        }
        if (!ticketIsFree(seat)) {
            throw new TicketException("Ticket not available!");
        }
        setStateOfTicket(seat, TicketState.SOLD);

    }

    public synchronized void reserveTicket(int seat, String name) throws TicketException {
        if (!acceptReservations) {
            throw new TicketException("Reservations are not available at this time!");
        }
        if (ticketNotExists(seat)) {
            throw new TicketException("Ticket does not exist!");
        }
        if (!ticketIsFree(seat)) {
            throw new TicketException("Ticket not available!");
        }
        if (name == null) {
            throw new TicketException("No name entered!");
        }
        setTicketReserved(seat, name);
    }

    public synchronized void sellReservedTicket(int seat, String name) throws TicketException {
        if (ticketNotExists(seat)) {
            throw new TicketException("Ticket does not exist!");
        }
        if (!ticketIsFree(seat)) {
            throw new TicketException("Ticket not available!");
        }
        if (name == null) {
            throw new TicketException("No name entered!");
        }
        if (!ticketIsReserved(seat)){
            throw new TicketException("Ticket is not reserved!");
        }
        if (!tickets[seat].getName().equals(name)) {
            throw new TicketException("Ticket not reserved under this name!");
        }
        setStateOfTicket(seat, TicketState.SOLD);

    }

    public synchronized void cancelTicket(int seat) throws TicketException {
        if (ticketNotExists(seat)) {
            throw new TicketException("Ticket does not exist!");
        }
        if (ticketIsFree(seat)) {
            throw new TicketException("Ticket is already available!");
        }
        setStateOfTicket(seat, TicketState.AVAILABLE);

    }

    public synchronized void cancelTicketReservation() {
        acceptReservations = false;
    }

    private boolean ticketIsFree(int seat) {
        return tickets[seat].getState().equals(TicketState.AVAILABLE);
    }

    private boolean ticketIsReserved(int seat) {
        return tickets[seat].getState().equals(TicketState.RESERVED);
    }


    private boolean ticketNotExists(int seat) {
        return (seat < 0 || seat > 99);
    }

    private void setStateOfTicket(int seat, TicketState state) {
        Ticket ticket = tickets[seat];
        ticket.setState(state);
        tickets[seat] = ticket;
    }

    private void setTicketReserved(int seat, String name) {
        Ticket ticket = tickets[seat];
        ticket.setState(TicketState.RESERVED);
        ticket.setName(name);
        tickets[seat] = ticket;
    }

}
