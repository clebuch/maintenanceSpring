package jku.se.maintenance;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class MaintenanceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MaintenanceApplication.class, args);

		log.info("Swagger API UI:");
		log.info("Remote: https://maintenance-spring.herokuapp.com/swagger-ui.html");
		log.info("Local:  http://localhost:8080/swagger-ui.html");
	}

}
