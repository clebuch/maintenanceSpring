package jku.se.maintenance.repository;

import jku.se.maintenance.entity.Ticket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends CrudRepository<Ticket,Integer> {
}
