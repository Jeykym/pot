package com.jeykym.pot.service;

import com.jeykym.pot.dto.CreatePlayerRequest;
import com.jeykym.pot.dto.PlayerDTO;
import com.jeykym.pot.exception.customException.PlayerAlreadyExistsException;
import com.jeykym.pot.exception.customException.InvalidFieldException;
import com.jeykym.pot.model.Player;
import com.jeykym.pot.repository.PlayerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    public PlayerDTO getById(UUID id) {
        if (id == null) {
            throw new InvalidFieldException("Player ID cannot be null");
        }

        var player = playerRepository.findById(id);

        return player
                .map(PlayerDTO::from)
                .orElseThrow(() -> new EntityNotFoundException("Player not found: " + id));
    }
}
