package com.jeykym.pot.repository;

import com.jeykym.pot.model.Participation;
import com.jeykym.pot.model.ParticipationId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipationRepository extends JpaRepository<Participation, ParticipationId> {
    List<Participation> findAllById_GameId(Long gameId);
    List<Participation> findAllById_PlayerId(Long playerId);
}
