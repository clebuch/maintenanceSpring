package jku.se.maintenance.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jku.se.maintenance.entity.Room;
import jku.se.maintenance.exception.ObjectNotFoundException;
import jku.se.maintenance.repository.RoomRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/room")
@Tag(name = "Room", description = "the Maintenance Room API")
public class RoomController {

    private final RoomRepository roomRepository;

    RoomController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Operation(summary = "Get a room")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found the room",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Room.class))}),
            @ApiResponse(responseCode = "404", description = "Room not found", content = @Content(mediaType = "application/json"))})
    @GetMapping("/{id}")
    public Room findOne(@PathVariable int id) throws IllegalArgumentException {
        return roomRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "Room"));
    }

    @Operation(summary = "Get all rooms")
    @ApiResponse(responseCode = "200", description = "Found the rooms",
            content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Room.class)))})
    @GetMapping
    public Iterable<Room> findAll() {
        return roomRepository.findAll();
    }

    @Operation(summary = "Edit a room")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Edited the room",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Room.class))}),
            @ApiResponse(responseCode = "404", description = "Room not found", content = @Content(mediaType = "application/json"))})
    @PutMapping("/{id}")
    public Room edit(@PathVariable int id, @RequestBody Room room) {
        room.setId(id);
        return roomRepository.save(room);
    }

    @Operation(summary = "Add a new room")
    @ApiResponse(responseCode = "201", description = "Added the room",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Room.class))})
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Room add(@RequestBody Room room) {
        return roomRepository.save(room);
    }

    @Operation(summary = "Delete a room")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Deleted the room",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Room.class))}),
            @ApiResponse(responseCode = "404", description = "Room not found", content = @Content(mediaType = "application/json"))})
    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        roomRepository.delete(findOne(id));
    }


}
