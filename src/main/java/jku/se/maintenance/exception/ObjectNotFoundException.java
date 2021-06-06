package jku.se.maintenance.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.NOT_FOUND)
public class ObjectNotFoundException extends RuntimeException {

    public ObjectNotFoundException(int id, String name) {
        super(String.format("Could not find %s with id %d", name.toLowerCase(), id));
    }

    public ObjectNotFoundException(int id) {
        super(String.format("Could not find object with id %d", id));
    }
}
