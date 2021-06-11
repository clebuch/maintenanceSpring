package jku.se.maintenance.repository;

import jku.se.maintenance.entity.Priority;
import jku.se.maintenance.entity.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Order(value = 2)
@Component
public class TicketDataRunner implements CommandLineRunner {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private TicketRepository ticketRepository;


    @Override
    public void run(String... args) {


        Ticket ticket_1 = new Ticket();
        ticket_1.setTitle("Princess Nala wants a Java Bean");
        ticket_1.setDescription("under her bedding");
        ticket_1.setPriority(Priority.HIGH);
        ticket_1.setRoom(roomRepository.findById(1).get());
        ticket_1.setCreatedTimeInSeconds(Instant.now().getEpochSecond());
        ticketRepository.save(ticket_1);

        Ticket ticket_2 = new Ticket();
        ticket_2.setTitle("Jack Jack wants a Cookie");
        ticket_2.setDescription("than he will be visible again");
        ticket_2.setPriority(Priority.MEDIUM);
        ticket_2.setRoom(roomRepository.findById(2).get());
        ticket_1.setCreatedTimeInSeconds(Instant.now().getEpochSecond());
        ticketRepository.save(ticket_2);

        Ticket ticket_3 = new Ticket();
        ticket_3.setTitle("Water is cold");
        ticket_3.setDescription("in the bathroom");
        ticket_3.setPriority(Priority.HIGH);
        ticket_3.setRoom(roomRepository.findById(3).get());
        ticket_3.setCreatedTimeInSeconds(Instant.now().getEpochSecond());
        ticketRepository.save(ticket_3);

        System.out.println("Default tickets added to Database");
    }
}

