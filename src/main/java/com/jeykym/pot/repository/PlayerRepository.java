package com.jeykym.pot.repository;

import com.jeykym.pot.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
}
