package jku.se.maintenance;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class Ticket {
    @Id @GeneratedValue
    private int id;
    private String title;
    private String description;
    private int roomNr;
    private Priority priority;
    private Date createdDate;
    private boolean resolved;
    private Date resolvedDate;
}
