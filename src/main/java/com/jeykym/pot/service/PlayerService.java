package com.jeykym.pot.service;

import com.jeykym.pot.dto.CreatePlayerRequest;
import com.jeykym.pot.dto.PlayerDTO;
import com.jeykym.pot.exception.customException.PlayerAlreadyExistsException;
import com.jeykym.pot.exception.customException.InvalidFieldException;
import com.jeykym.pot.model.Player;
import com.jeykym.pot.repository.PlayerRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public PlayerDTO createPlayer(CreatePlayerRequest request) {
        try {
            var savedPlayer = playerRepository.save(new Player(request.name()));
            return PlayerDTO.from(savedPlayer);
        } catch (DataIntegrityViolationException e) {
            throw new PlayerAlreadyExistsException(request.name());
        }
    }

    public List<PlayerDTO> getAllPlayers() {
        var players = playerRepository.findAll();
        return players.stream()
                .map(PlayerDTO::from)
                .toList();

    }
}
