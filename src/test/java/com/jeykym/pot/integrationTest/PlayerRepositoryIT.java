package com.jeykym.pot.integrationTest;

import com.jeykym.pot.model.Player;
import com.jeykym.pot.repository.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Testcontainers
public class PlayerRepositoryIT extends AbstractRepositoryIT {

    @Autowired
    PlayerRepository playerRepository;

    @BeforeEach
    void setUp() {
        playerRepository.deleteAll();
    }

    @Test
    void shouldInsertPlayer() {
        var player = new Player("testName");

        playerRepository.save(player);

        var players = playerRepository.findAll();
        playerRepository.flush();

        assertThat(players)
                .hasSize(1)
                .extracting(Player::getName)
                .containsExactly(player.getName());

        assertThat(players.getFirst().getId())
                .isNotNull()
                .isPositive();
    }

    @Test
    void shouldFailOnDuplicateName() {
        var testName = "testName";

        var player1 = new Player(testName);
        var player2 = new Player(testName);

        playerRepository.save(player1);
        assertThatThrownBy(() -> {
            playerRepository.save(player2);
            playerRepository.flush();
        })
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void shouldFailOnNullName() {
        var player = new Player(null);

        assertThatThrownBy(() -> {
            playerRepository.save(player);
            playerRepository.flush();
        })
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void shouldFailOnBlankName() {
        var player = new Player("");

        assertThatThrownBy(() -> {
            playerRepository.save(player);
            playerRepository.flush();
        })
                .isInstanceOf(DataIntegrityViolationException.class);
    }
}
