package com.jeykym.pot.service;

import com.jeykym.pot.dto.CreatePlayerRequest;
import com.jeykym.pot.model.Player;
import com.jeykym.pot.repository.PlayerRepository;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {

    PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Player createPlayer(CreatePlayerRequest createPlayerRequest) {
        return playerRepository.save(new Player(createPlayerRequest.name()));
    }
}
