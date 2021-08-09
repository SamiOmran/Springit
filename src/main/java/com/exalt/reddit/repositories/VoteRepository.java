package com.exalt.reddit.repositories;

import com.exalt.reddit.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findFirstByCreatedDateBefore(LocalDateTime current);
    Optional<Vote> findTopByOrderByIdDesc();

}
