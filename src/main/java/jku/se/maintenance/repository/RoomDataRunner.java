package jku.se.maintenance.repository;

import jku.se.maintenance.entity.Room;
import jku.se.maintenance.entity.RoomType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(value = 1)
@Component
public class RoomDataRunner implements CommandLineRunner {

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public void run(String... args) {
        Room room_1 = new Room();
        room_1.setId(1);
        room_1.setRoomNr("11");
        room_1.setDescription("Princess Room");
        room_1.setRoomType(RoomType.SUITE);
        roomRepository.save(room_1);

        Room room_2 = new Room();
        room_2.setId(2);
        room_2.setRoomNr("12");
        room_2.setDescription("Swiss Room");
        room_2.setRoomType(RoomType.SINGLE);
        roomRepository.save(room_2);


        Room room_3 = new Room();
        room_3.setId(3);
        room_3.setRoomNr("13");
        room_3.setDescription("Queens Room");
        room_3.setRoomType(RoomType.DOUBLE);
        roomRepository.save(room_3);

        System.out.println("Default rooms added to Database");

    }
}

