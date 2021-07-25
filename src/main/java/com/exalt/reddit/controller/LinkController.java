package com.exalt.reddit.controller;

import com.exalt.reddit.model.Link;
import com.exalt.reddit.repositories.LinkRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/links")
public class LinkController {
    private LinkRepository linkRepository;

    public LinkController(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    /***
     * @return all Links in the linkRepository
     */
    @GetMapping(path = "/")
    public List<Link> getLinks() {
        return linkRepository.findAll();
    }

    /**
     * create new link in the linkRepository
     * @param link the new link to be added
     */
    @PostMapping(path = "/create")
    public void create(@ModelAttribute Link link) {
        linkRepository.save(link);
    }

    /**
     * @param id the PathVariable would be in the RequestMapping
     * @return the link has the same id
     */
    @GetMapping(path = "{id}")
    public Optional<Link> read(@PathVariable Long id) {
        return linkRepository.findById(id);
    }

    /**
     * @param id the PathVariable would be in the RequestMapping
     * @param link the updated link
     */
    @PutMapping(path = "/{id}")
    public void update(@PathVariable Long id, @ModelAttribute Link link) {
        linkRepository.save(link);
    }

    /**
     * @param id the PathVariable would be in the RequestMapping to delete
     *           the link has this id
     */
    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id) {
        linkRepository.deleteById(id);
    }

}
