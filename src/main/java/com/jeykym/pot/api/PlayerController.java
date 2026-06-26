package com.jeykym.pot.api;

import com.jeykym.pot.dto.CreatePlayerRequest;
import com.jeykym.pot.dto.PlayerDTO;
import com.jeykym.pot.model.Player;
import com.jeykym.pot.service.PlayerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/players")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping
    public ResponseEntity<PlayerDTO> createPlayer(@RequestBody @Valid CreatePlayerRequest request) {
        PlayerDTO dto = playerService.createPlayer(request);
        return ResponseEntity
                .created(URI.create("/players/" + dto.id()))
                .body(dto);
    }

    @GetMapping
    public ResponseEntity<List<PlayerDTO>> getAllPlayers() {
        var players = playerService.getAllPlayers();
        return ResponseEntity
                .ok()
                .body(players);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayerDTO> getPlayerById(@PathVariable UUID id) {
        PlayerDTO dto = playerService.getById(id);
        return ResponseEntity.ok(dto);
    }
}
