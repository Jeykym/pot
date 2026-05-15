package com.jeykym.pot.api;

import com.jeykym.pot.dto.CreatePlayerRequest;
import com.jeykym.pot.dto.PlayerDTO;
import com.jeykym.pot.service.PlayerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/players")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping
    public ResponseEntity<PlayerDTO> createPlayer(@RequestBody@Valid CreatePlayerRequest request) {
        return ResponseEntity.ok(playerService.createPlayer(request));
    }
}
