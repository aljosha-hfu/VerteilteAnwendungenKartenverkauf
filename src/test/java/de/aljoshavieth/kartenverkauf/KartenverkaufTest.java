package de.aljoshavieth.kartenverkauf;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

class KartenverkaufTest {

    Kartenverkauf kartenverkauf = new Kartenverkauf();
    final Logger logger = Logger.getLogger("KartenverkaufTest");

    @BeforeEach
    void setUp() {
        logger.info("Setting up Kartenverkauf");
        kartenverkauf = new Kartenverkauf();
        for (int i = 0; i < 100; i++) {
            Ticket ticket = new Ticket();
            if (i % 2 == 0) {
                ticket.setState(TicketState.RESERVED);
            } else {
                ticket.setState(TicketState.AVAILABLE);
            }
            kartenverkauf.tickets[i] = ticket;
        }
        logger.info("Printing all TicketStates:");
        Arrays.stream(kartenverkauf.tickets).forEach(t -> logger.info(t.getState().toString()));
    }


    @Test
    void cancelAllTicketReservations() {
        logger.info("Cancelling all ticket reservations");
        kartenverkauf.cancelAllTicketReservations();
        logger.info("Printing all TicketStates:");
        Arrays.stream(kartenverkauf.tickets).forEach(t -> logger.info(t.getState().toString()));
        assumeTrue(Arrays.stream(kartenverkauf.tickets).allMatch(ticket -> ticket.getState() == TicketState.AVAILABLE));
    }
}