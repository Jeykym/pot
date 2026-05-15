package com.jeykym.pot;

import com.jeykym.pot.model.Game;
import com.jeykym.pot.model.Participation;
import com.jeykym.pot.model.Player;
import com.jeykym.pot.repository.GameRepository;
import com.jeykym.pot.repository.ParticipationRepository;
import com.jeykym.pot.repository.PlayerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Instant;

@SpringBootApplication
public class PotApplication {

	public static void main(String[] args) {
		SpringApplication.run(PotApplication.class, args);
	}
}
