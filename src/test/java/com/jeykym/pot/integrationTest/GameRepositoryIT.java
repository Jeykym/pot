package com.jeykym.pot.integrationTest;

import com.jeykym.pot.model.Game;
import com.jeykym.pot.repository.GameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Testcontainers
public class GameRepositoryIT extends AbstractRepositoryIT {

    @Autowired
    GameRepository gameRepository;

    @BeforeEach
    void setUp() {
        gameRepository.deleteAll();
    }

    @Test
    void shouldInsertGame() {
        var game = new Game(Instant.now());

        gameRepository.save(game);
        gameRepository.flush();

        var games = gameRepository.findAll();

        assertThat(games)
                .hasSize(1);
        assertThat(games.getFirst().getDate())
                .isCloseTo(game.getDate(), within(1, ChronoUnit.MILLIS));
    }

    @Test
    void shouldThrowOnNullDate() {
        var game = new Game(null);

        assertThatThrownBy(() -> {
            gameRepository.save(game);
            gameRepository.flush();
        })
                .isInstanceOf(DataIntegrityViolationException.class);
    }
}
