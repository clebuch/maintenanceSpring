package jku.se.maintenance.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Room {
    @Id
    @GeneratedValue
    private int id;
    private String roomNr;
    private String description;
    private RoomType roomType;
    private boolean booked;
}
