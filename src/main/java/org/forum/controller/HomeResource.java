package org.forum.controller;

import org.forum.entities.user.User;
import org.forum.service.PostService;
import org.forum.service.SectionService;
import org.forum.service.TopicService;
import org.forum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
public class HomeResource {


    @Autowired
    private SectionService sectionService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;


    @RequestMapping(value = { "/", "/home" })
    public String home(Model model) {
        model.addAttribute("sections", sectionService.findAll());
        model.addAttribute("topics", topicService.findRecent());
        model.addAttribute("posts", postService.findRecent());
        model.addAttribute("error", "You sent");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(!authentication.getName().equals("anonymousUser")) {
            User user = userService.findByUsername(authentication.getName());
            user.setLastLoginTime(new Date());
            userService.save(user);
        }

        return "home/home";
    }

    @RequestMapping("/403")
    public String accessdenied() {
        return "fragments/403";
    }

}
