package org.forum.entities.user.activation;

import org.forum.exception.ForumException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;


@Controller
public class ActivationController {

    @Autowired
    private ActivationService activationService;

    @RequestMapping(value = "/users/{username}/activation")
    public String activateUserAndRedirectToLoginPage(@PathVariable String username,
        @RequestParam String id) {
        activationService.activate(username, id);
        return "home/activation_success";
    }

    @ExceptionHandler(ForumException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleActivationError(
        HttpServletRequest request,
        ForumException exception) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", exception);
        mav.addObject("timestamp", new Date());
        mav.addObject("url", request.getRequestURL());
        mav.setViewName("error");
        return mav;
    }

}
