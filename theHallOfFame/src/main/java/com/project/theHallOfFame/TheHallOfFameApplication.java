package com.project.theHallOfFame;

import com.project.theHallOfFame.service.InitialDataMigration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TheHallOfFameApplication {

	public static void main(String[] args) {

		// InitialDataMigration 호출 예정
		SpringApplication.run(TheHallOfFameApplication.class, args);
	}

}
