package jku.se.maintenance.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;


@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Ticket extends RepresentationModel<Ticket> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    private Room room;
    private Priority priority;
    private long createdTimeInSeconds;
    private boolean isResolved;
    private long resolvedTimeInSeconds;
}
