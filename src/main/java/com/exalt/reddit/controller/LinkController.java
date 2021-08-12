package com.exalt.reddit.controller;

import com.exalt.reddit.model.Comment;
import com.exalt.reddit.model.Link;
import com.exalt.reddit.service.CommentService;
import com.exalt.reddit.service.LinkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
public class LinkController {

    private LinkService linkService;
    private CommentService commentService;

    private static final Logger logger = LoggerFactory.getLogger(LinkController.class);

    public LinkController(LinkService linkService, CommentService commentService) {
        this.linkService = linkService;
        this.commentService = commentService;
    }

    @GetMapping(path = "/")
    public String list(Model model) {
        model.addAttribute("links",linkService.findAll());
        return "link/list";
    }

    @GetMapping(path = "/link/{id}")
    public String read(@PathVariable Long id, Model model) {
        Optional<Link> link = linkService.findById(id);

        if (link.isPresent()) {
            Link currentLink = link.get();
            Comment comment = new Comment();
            comment.setLink(currentLink);
            model.addAttribute("comment",comment);
            model.addAttribute("link",link.get());

            return "link/view";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping(path = "/submit")
    public String newLinkForm(Model model) {
        model.addAttribute("link",new Link());
        return "link/submit";
    }

    @PostMapping(path = "/submit")
    public String createLink(@Valid Link link, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()){
            logger.info("An error occurred while creating new link");
            model.addAttribute("link",link);
            return "link/submit";
        } else {
            link = linkService.save(link);
            logger.info("Success saving new link");
            redirectAttributes
                    .addAttribute("id",link.getId())
                    .addFlashAttribute("success",true);

            return "redirect:/link/{id}";
        }
    }

    @PostMapping(path = "/link/comments")
    public String addComment(@Valid Comment comment, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logger.info("There is a problem adding a comment");
        } else {
            commentService.save(comment);
            logger.info("Success adding a comment");
        }
        return "redirect:/link/" + comment.getLink().getId();
    }

    /***
     *
     * learning about @RequestParam
     */
    @GetMapping(path = "/link")
    @ResponseBody
    public String getLinkTitleById(@RequestParam Long id) {
        try {
            String title = linkService.findById(id).get().getTitle();
            return title;
        } catch (NoSuchElementException e) {
            return "Not found this link";
        }
    }

}
