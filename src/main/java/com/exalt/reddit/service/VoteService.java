package com.exalt.reddit.service;

import com.exalt.reddit.model.Link;
import com.exalt.reddit.model.Vote;
import com.exalt.reddit.repositories.VoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VoteService {

    private final VoteRepository voteRepository;

    public VoteService(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    public List<Vote> findAll() {
        return voteRepository.findAll();
    }

    public Optional<Vote> findById(Long id) {
        return voteRepository.findById(id);
    }

    public void save(Vote vote) {
        voteRepository.save(vote);
    }
}
