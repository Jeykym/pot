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
}
