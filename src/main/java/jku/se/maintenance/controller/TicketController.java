package jku.se.maintenance.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jku.se.maintenance.entity.Room;
import jku.se.maintenance.entity.Ticket;
import jku.se.maintenance.exception.ObjectNotFoundException;
import jku.se.maintenance.repository.RoomRepository;
import jku.se.maintenance.repository.TicketRepository;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@CrossOrigin
@RestController
@RequestMapping("/ticket")
@Tag(name = "Ticket", description = "the Maintenance Ticket API")
@CacheConfig(cacheNames = {"tickets"})
public class TicketController {

    private final TicketRepository ticketRepository;
    private final RoomRepository roomRepository;

    TicketController(TicketRepository ticketRepository, RoomRepository roomRepository) {
        this.ticketRepository = ticketRepository;
        this.roomRepository = roomRepository;
    }

    @Cacheable
    @Operation(summary = "Get a ticket")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found the ticket",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Ticket.class))}),
            @ApiResponse(responseCode = "404", description = "Ticket not found", content = @Content(mediaType = "application/json"))})
    @GetMapping("/{id}")
    public Ticket findOne(@PathVariable int id) throws IllegalArgumentException {
        Ticket ticket = ticketRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "Ticket"));
        return setLinks(ticket);
    }

    @Cacheable
    @Operation(summary = "Get all tickets")
    @ApiResponse(responseCode = "200", description = "Found the tickets",
            content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Ticket.class)))})
    @GetMapping
    public Iterable<Ticket> findAll() {
        Iterable<Ticket> tickets = ticketRepository.findAll();
        tickets.forEach(t -> {
                    Link selfLink = linkTo(TicketController.class).slash(t.getId()).withSelfRel();
                    t.add(selfLink);
                }
        );
        Link selfLink = linkTo(TicketController.class).withSelfRel();
        return CollectionModel.of(tickets, selfLink);
    }

    @Cacheable
    @Operation(summary = "Edit a ticket")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Edited the ticket",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Ticket.class))}),
            @ApiResponse(responseCode = "404", description = "Ticket not found", content = @Content(mediaType = "application/json"))})
    @PutMapping("/{id}")
    public Ticket edit(@PathVariable int id, @RequestBody Ticket ticket) {
        ticket.setId(id);
        ticket.setRoom(roomRepository.findById(ticket.getRoom().getId())
                .orElseThrow(() -> new ObjectNotFoundException(ticket.getRoom().getId(), "Room")));
        return setLinks(ticketRepository.save(ticket));
    }

    @Cacheable
    @Operation(summary = "Add a new ticket")
    @ApiResponse(responseCode = "201", description = "Added the ticket",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Ticket.class))})
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Ticket add(@RequestBody Ticket ticket) {
        ticket.setRoom(roomRepository.findById(ticket.getRoom().getId())
                .orElseThrow(() -> new ObjectNotFoundException(ticket.getRoom().getId(), "Room")));


        return setLinks(ticketRepository.save(ticket));
    }

    @Cacheable
    @Operation(summary = "Delete a ticket")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Deleted the ticket",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Ticket.class))}),
            @ApiResponse(responseCode = "404", description = "Ticket not found", content = @Content(mediaType = "application/json"))})
    @DeleteMapping("/{id}")
    public Ticket delete(@PathVariable int id) {
        Ticket ticket = findOne(id);
        ticket.removeLinks();
        ticket.add(linkTo(methodOn(TicketController.class).findAll()).withRel("allTickets"));
        Room room = roomRepository.findById(ticket.getRoom().getId()).get();
        room.getTickets().remove(ticket);
        roomRepository.save(room);
        return ticket;
    }

    private Ticket setLinks(Ticket ticket) {
        Link selfLink = linkTo(TicketController.class).slash(ticket.getId()).withSelfRel();
        Link allTickets = linkTo(methodOn(TicketController.class).findAll()).withRel("allTickets");
        ticket.add(selfLink, allTickets);
        return ticket;
    }


}
