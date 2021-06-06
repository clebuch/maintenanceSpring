package jku.se.maintenance.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jku.se.maintenance.entity.Ticket;
import jku.se.maintenance.exception.ObjectNotFoundException;
import jku.se.maintenance.repository.TicketRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/ticket")
@Tag(name = "Ticket", description = "the Maintenance Ticket API")
public class TicketController {

    private final TicketRepository ticketRepository;

    TicketController(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Operation(summary = "Get a ticket")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found the ticket",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Ticket.class))}),
            @ApiResponse(responseCode = "404", description = "Ticket not found", content = @Content(mediaType = "application/json"))})
    @GetMapping("/{id}")
    public Ticket findOne(@PathVariable int id) throws IllegalArgumentException {
        return ticketRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "Ticket"));
    }

    @Operation(summary = "Get all tickets")
    @ApiResponse(responseCode = "200", description = "Found the tickets",
            content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Ticket.class)))})
    @GetMapping
    public Iterable<Ticket> findAll() {
        return ticketRepository.findAll();
    }

    @Operation(summary = "Edit a ticket")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Edited the ticket",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Ticket.class))}),
            @ApiResponse(responseCode = "404", description = "Ticket not found", content = @Content(mediaType = "application/json"))})
    @PutMapping("/{id}")
    public Ticket edit(@PathVariable int id, @RequestBody Ticket ticket) {
        ticket.setId(id);
        return ticketRepository.save(ticket);
    }

    @Operation(summary = "Add a new ticket")
    @ApiResponse(responseCode = "201", description = "Added the ticket",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Ticket.class))})
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Ticket add(@RequestBody Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    @Operation(summary = "Delete a ticket")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Deleted the ticket",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Ticket.class))}),
            @ApiResponse(responseCode = "404", description = "Ticket not found", content = @Content(mediaType = "application/json"))})
    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        ticketRepository.delete(findOne(id));
    }


}
