package com.jeykym.pot.unitTest;

import com.jeykym.pot.dto.CreatePlayerRequest;
import com.jeykym.pot.exception.customException.PlayerAlreadyExistsException;
import com.jeykym.pot.model.Player;
import com.jeykym.pot.repository.PlayerRepository;
import com.jeykym.pot.service.PlayerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PlayerServiceTest {

    @Mock
    PlayerRepository playerRepository;

    @InjectMocks
    PlayerService playerService;

    @Test
    void createPlayer_returnsCorrectDTO() {
        var player = new Player("John");
        when(playerRepository.save(any(Player.class))).thenReturn(player);

        var result = playerService.createPlayer(new CreatePlayerRequest(player.getName()));

        assertThat(result.name()).isEqualTo(player.getName());
    }

    @Test
    void createPlayer_savesExactlyOnce() {
        when(playerRepository.save(any(Player.class))).thenReturn(new Player("John"));

        playerService.createPlayer(new CreatePlayerRequest("John"));

        verify(playerRepository, times(1)).save(any(Player.class));
    }

    @Test
    void createPlayer_throwsWhenDuplicateName() {
        when(playerRepository.save(any(Player.class)))
                .thenThrow(DataIntegrityViolationException.class);

        assertThatThrownBy(() -> playerService.createPlayer(new CreatePlayerRequest("John")))
                .isInstanceOf(PlayerAlreadyExistsException.class);
    }

    @Test
    void getAllPlayers_returnsEmptyList_whenNoPlayersExist() {
        when(playerRepository.findAll()).thenReturn(List.of());

        var result = playerService.getAllPlayers();

        assertThat(result).isEmpty();
        verify(playerRepository, times(1)).findAll();
    }

    @Test
    void getAllPlayers_returnsSinglePlayer_whenOnePlayerExists() {
        var player = new Player("Alice");
        when(playerRepository.findAll()).thenReturn(List.of(player));

        var result = playerService.getAllPlayers();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).name()).isEqualTo(player.getName());
        verify(playerRepository, times(1)).findAll();
    }

    @Test
    void getAllPlayers_returnsMultiplePlayers_whenMultiplePlayersExist() {
        var player1 = new Player("Alice");
        var player2 = new Player("Bob");
        when(playerRepository.findAll()).thenReturn(List.of(player1, player2));

        var result = playerService.getAllPlayers();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).name()).isEqualTo(player1.getName());
        assertThat(result.get(1).name()).isEqualTo(player2.getName());
        verify(playerRepository, times(1)).findAll();
    }
}
