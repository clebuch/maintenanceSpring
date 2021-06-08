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
@RequestMapping("/room")
@Tag(name = "Room", description = "the Maintenance Room API")
@CacheConfig(cacheNames = {"rooms"})
public class RoomController {

    private final RoomRepository roomRepository;

    RoomController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Cacheable
    @Operation(summary = "Get a room")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found the room",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Room.class))}),
            @ApiResponse(responseCode = "404", description = "Room not found", content = @Content(mediaType = "application/json"))})
    @GetMapping("/{id}")
    public Room findOne(@PathVariable int id) throws IllegalArgumentException {
        Room room = roomRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "Room"));
        return setLinks(room);
    }

    @Cacheable
    @Operation(summary = "Get all rooms")
    @ApiResponse(responseCode = "200", description = "Found the rooms",
            content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Room.class)))})
    @GetMapping
    public Iterable<Room> findAll() {
        Iterable<Room> rooms = roomRepository.findAll();
        rooms.forEach(t -> {
                    Link selfLink = linkTo(RoomController.class).slash(t.getId()).withSelfRel();
                    t.add(selfLink);
                }
        );
        Link selfLink = linkTo(RoomController.class).withSelfRel();
        return CollectionModel.of(rooms, selfLink);
    }

    @Cacheable
    @Operation(summary = "Edit a room")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Edited the room",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Room.class))}),
            @ApiResponse(responseCode = "404", description = "Room not found", content = @Content(mediaType = "application/json"))})
    @PutMapping("/{id}")
    public Room edit(@PathVariable int id, @RequestBody Room room) {
        room.setId(id);
        return setLinks(roomRepository.save(room));
    }

    @Cacheable
    @Operation(summary = "Add a new room")
    @ApiResponse(responseCode = "201", description = "Added the room",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Room.class))})
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Room add(@RequestBody Room room) {
        return setLinks(roomRepository.save(room));
    }

    @Cacheable
    @Operation(summary = "Delete a room")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Deleted the room",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Room.class))}),
            @ApiResponse(responseCode = "404", description = "Room not found", content = @Content(mediaType = "application/json"))})
    @DeleteMapping("/{id}")
    public Room delete(@PathVariable int id) {
        Room room = findOne(id);
        room.removeLinks();
        room.add(linkTo(methodOn(RoomController.class).findAll()).withRel("allRooms"));
        roomRepository.delete(room);
        return room;
    }

    private Room setLinks(Room room) {
        Link selfLink = linkTo(RoomController.class).slash(room.getId()).withSelfRel();
        Link allRooms = linkTo(methodOn(RoomController.class).findAll()).withRel("allRooms");
        room.add(selfLink, allRooms);
        return room;
    }


}
