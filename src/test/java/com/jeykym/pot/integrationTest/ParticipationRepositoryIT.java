package com.jeykym.pot.integrationTest;

import com.jeykym.pot.model.Game;
import com.jeykym.pot.model.Participation;
import com.jeykym.pot.model.Player;
import com.jeykym.pot.repository.GameRepository;
import com.jeykym.pot.repository.ParticipationRepository;
import com.jeykym.pot.repository.PlayerRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Testcontainers
@Transactional
@Rollback
public class ParticipationRepositoryIT extends AbstractRepositoryIT {

    @Autowired
    ParticipationRepository participationRepository;

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    GameRepository gameRepository;

    @Autowired
    EntityManager entityManager;

    @BeforeEach
    void setUp() {
        participationRepository.deleteAll();
        playerRepository.deleteAll();
        gameRepository.deleteAll();

        playerRepository.flush();
        gameRepository.flush();
        participationRepository.flush();
    }

    @Test
    void shouldInsertParticipation() {
        var player = playerRepository.save(new Player("John Wick"));
        var game = gameRepository.save(new Game(Instant.now()));
        playerRepository.flush();
        gameRepository.flush();

        var participation = participationRepository.save(new Participation(player, game, 100, 10));

        var participations = participationRepository.findAll();
        assertThat(participations)
                .hasSize(1);
        assertThat(participations)
                .singleElement()
                .returns(participation.getBuyIn(), Participation::getBuyIn)
                .returns(participation.getFinalStack(), Participation::getFinalStack);
        var savedParticipation = participations.getFirst();
        var optionalPlayer = playerRepository.findById(savedParticipation.getPlayerId());
        var optionalGame = gameRepository.findById(savedParticipation.getGameId());

        assertThat(optionalPlayer)
                .isPresent();
        assertThat(optionalPlayer.get().getId())
                .isEqualTo(player.getId());

        assertThat(optionalGame)
                .isPresent();
        assertThat(optionalGame.get().getId())
                .isEqualTo(game.getId());
    }


    @Test
    void shouldFailOnDuplicateParticipation() {
        var player = playerRepository.save(new Player("John Wick"));
        playerRepository.flush();

        var game = gameRepository.save(new Game(Instant.now()));
        gameRepository.flush();

        var participation1 = participationRepository.save(new Participation(player, game, 100, 10));
        participationRepository.flush();
        entityManager.clear();

        assertThatThrownBy(() -> {
            entityManager.persist(new Participation(player, game, 100, 150));
            participationRepository.flush();
        })
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void shouldFailOnNullBuyIn() {
        var player = playerRepository.save(new Player("John Wick"));
        playerRepository.flush();

        var game = gameRepository.save(new Game(Instant.now()));
        gameRepository.flush();

        assertThatThrownBy(() -> {
            participationRepository.save(new Participation(player, game, null, 10));
        })
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void shouldFailOnNullFinalStack() {
        var player = playerRepository.save(new Player("John Wick"));
        playerRepository.flush();

        var game = gameRepository.save(new Game(Instant.now()));
        gameRepository.flush();

        assertThatThrownBy(() -> {
            participationRepository.save(new Participation(player, game, 100, null));
        })
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void shouldAllowParticipationOfMultiplePlayers() {
        var game = gameRepository.save(new Game(Instant.now()));
        gameRepository.flush();

        var player1 = playerRepository.save(new Player("player 1"));
        var player2 = playerRepository.save(new Player("player 2"));
        playerRepository.flush();

        var players = playerRepository.findAll();
        assertThat(players)
                .hasSize(2);

        participationRepository.save(new Participation(player1, game, 100, 10));
        participationRepository.save(new Participation(player2, game, 100, 150));

        var participationsByGame = participationRepository.findAllById_GameId(game.getId());
        assertThat(participationsByGame)
                .hasSize(2);

    }

    @Test
    void shouldAllowParticipationInMultipleGames() {
        var player = playerRepository.save(new Player("John Wick"));
        gameRepository.flush();

        var game1 = gameRepository.save(new Game(Instant.now()));
        var game2 = gameRepository.save(new Game(Instant.now().minus(2, ChronoUnit.MINUTES)));
        gameRepository.flush();

        participationRepository.save(new Participation(player, game1, 100, 10));
        participationRepository.save(new Participation(player, game2, 100, 150));

        var participationsByPlayer = participationRepository.findAllById_PlayerId(player.getId());
        assertThat(participationsByPlayer)
                .hasSize(2);
    }
}
