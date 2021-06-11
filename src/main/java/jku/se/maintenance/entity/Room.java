package jku.se.maintenance.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Room extends RepresentationModel<Room> {
    @OneToMany(
            mappedBy = "room",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonIgnore
    public List<Ticket> tickets = new ArrayList<>();
    private String roomNr;
    private String description;
    private RoomType roomType;
    private boolean booked;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

}
