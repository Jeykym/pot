package com.jeykym.pot.service;

import com.jeykym.pot.dto.CreatePlayerRequest;
import com.jeykym.pot.dto.PlayerDTO;
import com.jeykym.pot.model.Player;
import com.jeykym.pot.repository.PlayerRepository;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public PlayerDTO createPlayer(CreatePlayerRequest request) {
        var savedPlayer = playerRepository.save(new Player(request.name()));
        return PlayerDTO.from(savedPlayer);
    }
}
