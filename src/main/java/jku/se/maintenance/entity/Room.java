package jku.se.maintenance.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Room extends RepresentationModel<Room> {
    @Id
    @GeneratedValue
    private int id;
    private String roomNr;
    private String description;
    private RoomType roomType;
    private boolean booked;
}
