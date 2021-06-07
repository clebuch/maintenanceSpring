package jku.se.maintenance.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Ticket extends RepresentationModel<Ticket> {
    @Id
    @GeneratedValue
    private int id;
    private String title;
    private String description;
    @ManyToOne(cascade = CascadeType.ALL)
    private Room room;
    private Priority priority;
    private Date createdDate;
    private boolean resolved;
    private Date resolvedDate;
}
