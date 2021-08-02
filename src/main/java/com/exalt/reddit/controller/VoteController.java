package com.exalt.reddit.controller;

import com.exalt.reddit.model.Link;
import com.exalt.reddit.model.Vote;
import com.exalt.reddit.repositories.LinkRepository;
import com.exalt.reddit.repositories.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class VoteController {

    private VoteRepository voteRepository;
    private LinkRepository linkRepository;

    @GetMapping(path = "/vote/link/{linkID}/direction/{direction}/votecount/{voteCount}")
    public int vote(@PathVariable Long linkID, @PathVariable short direction, @PathVariable int voteCount) {

        Optional <Link> optionalLink = linkRepository.findById(linkID);
        if (optionalLink.isPresent()) {
            Link link = optionalLink.get();
            Vote vote = new Vote(direction, link);

            voteRepository.save(vote);

            int updatedVoteCount = voteCount + direction;
            link.setVoteCount(updatedVoteCount);
            linkRepository.save(link);
            return updatedVoteCount;
        } else {
            return voteCount;
        }
    }
    
}
