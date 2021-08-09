package com.exalt.reddit.service;

import com.exalt.reddit.model.Link;
import com.exalt.reddit.model.Vote;
import com.exalt.reddit.repositories.VoteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
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

    public Vote save(Vote vote) {
        return voteRepository.save(vote);
    }

//    public String latestVote() {
//        LocalDateTime now = LocalDateTime.now();
//
//        List<Vote> optionalVote = voteRepository.findAll();
//
//        if (optionalVote.size() > 0) {
//            Vote latestVote = optionalVote.get(optionalVote.size()-1);
//            Long linkId = latestVote.getLink().getId();
//            int voteCount = latestVote.getLink().getVoteCount();
//
//            return "Latest vote was on this linkId: " + linkId + ", and the vote count is: " + voteCount;
//        }
//        return "Could not found";
//    }

    public String latestVote() {
        Optional<Vote> optionalVote = voteRepository.findTopByOrderByIdDesc();

        if (optionalVote.isPresent()) {
            Vote latestVote = optionalVote.get();
            Long linkId = latestVote.getLink().getId();
            int voteCount = latestVote.getLink().getVoteCount();

            return "Latest vote was on this linkId: " + linkId + ", and the vote count is: " + voteCount;
        }
        return "Could not found";
    }
}
