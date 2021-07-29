package com.exalt.reddit.controller;

import com.exalt.reddit.model.Link;
import com.exalt.reddit.repositories.LinkRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
//@RestController
//@RequestMapping("/links")
public class LinkController {

    private LinkRepository linkRepository;

    private static final Logger logger = LoggerFactory.getLogger(LinkController.class);

    public LinkController(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    @GetMapping(path = "/")
    public String list(Model model) {
        model.addAttribute("links",linkRepository.findAll());
        return "link/list";
    }

    @GetMapping(path = "/link/{id}")
    public String read(@PathVariable Long id, Model model) {
        Optional<Link> link = linkRepository.findById(id);

        if (link.isPresent()) {
            model.addAttribute("link",link.get());
            return "link/view";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping(path = "/link/submit")
    public String newLinkForm(Model model) {
        model.addAttribute("link",new Link());
        return "link/submit";
    }

    @PostMapping(path = "/link/submit")
    public String createLink(@Valid Link link, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()){
            logger.info("An error occurred while creating new link");
            model.addAttribute("link",link);
            return "link/submit";
        } else {
            linkRepository.save(link);
            logger.info("Success saving new link");
            redirectAttributes.addAttribute("id",link.getId());//.addFlashAttribute("success",true);
            return "redirect:/link/{id}";
        }

    }

//    /***
//     * @return all Links in the linkRepository
//     */
//    @GetMapping(path = "/")
//    public List<Link> getLinks() {
//        return linkRepository.findAll();
//    }
//
//    /**
//     * create new link in the linkRepository
//     * @param link the new link to be added
//     */
//    @PostMapping(path = "/create")
//    public void create(@ModelAttribute Link link) {
//        linkRepository.save(link);
//    }
//
//    /**
//     * @param id the PathVariable would be in the RequestMapping
//     * @return the link has the same id
//     */
//    @GetMapping(path = "{id}")
//    public Optional<Link> read(@PathVariable Long id) {
//        return linkRepository.findById(id);
//    }
//
//    /**
//     * @param id the PathVariable would be in the RequestMapping
//     * @param link the updated link
//     */
//    @PutMapping(path = "/{id}")
//    public void update(@PathVariable Long id, @ModelAttribute Link link) {
//        linkRepository.save(link);
//    }
//
//    /**
//     * @param id the PathVariable would be in the RequestMapping to delete
//     *           the link has this id
//     */
//    @DeleteMapping(path = "/{id}")
//    public void delete(@PathVariable Long id) {
//        linkRepository.deleteById(id);
//    }

}