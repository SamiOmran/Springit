package com.exalt.reddit.controller;

import com.exalt.reddit.model.Link;
import com.exalt.reddit.model.Vote;
import com.exalt.reddit.repositories.LinkRepository;
import com.exalt.reddit.repositories.VoteRepository;
import com.exalt.reddit.service.LinkService;
import com.exalt.reddit.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class VoteController {

    private VoteService voteService;
    private LinkService linkService;

    public VoteController(VoteService voteService, LinkService linkService) {
        this.voteService = voteService;
        this.linkService = linkService;
    }

    @Secured({"ROLE_USER"})
    @GetMapping(path = "/vote/link/{linkID}/direction/{direction}/votecount/{voteCount}")
    public int vote(@PathVariable Long linkID, @PathVariable short direction, @PathVariable int voteCount) {
        Optional <Link> optionalLink = linkService.findById(linkID);

        if (optionalLink.isPresent()) {
            Link link = optionalLink.get();
            Vote vote = new Vote(direction, link);
            voteService.save(vote);

            int updatedVoteCount = voteCount + direction;
            link.setVoteCount(updatedVoteCount);
            linkService.save(link);

            return updatedVoteCount;
        } else {
            return voteCount;
        }
    }

    @GetMapping(path = "/vote/latestvote")
    public String latestVote() {
        return voteService.latestVote();
    }

}
