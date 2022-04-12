package org.forum.controller;

import org.forum.entities.Section;
import org.forum.entities.user.User;
import org.forum.entities.user.UserAdditionalInfo;
import org.forum.entities.user.UserProfile;
import org.forum.entities.user.exception.UserNotFoundException;
import org.forum.newform.NewSectionForm;
import org.forum.newform.ProfilForm;
import org.forum.service.SectionService;
import org.forum.service.UserAdditionalInfoService;
import org.forum.service.UserProfileService;
import org.forum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;

@ComponentScan
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private SectionService sectionService;

    @Autowired
    private UserAdditionalInfoService userAdditionalInfoService;

    @RequestMapping(value = "/user/{username}")
    public String findUserByUsernameAndViewProfilePage(@PathVariable String username,
                                                       Model model, Authentication authentication) {
        UserProfile userProfile;
        try {
            userProfile = userProfileService.findOne(username);
        } catch (NullPointerException e) {
            throw new UserNotFoundException();
        }

        if (userProfile.getUser().getIsPublic() == 0 && !authentication.getName().equals(username) && !authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            throw new AccessDeniedException("You dont have permission for this operation");
        }

        model.addAttribute("userProfile", userProfile);
        return "user/myprofile";
    }

    @RequestMapping(value = "/users")
    public String listOfAllUser(Model model, @RequestParam(defaultValue="") String name) {
        try {
            model.addAttribute("users", userService.findAllByUsername(name));
        } catch (Exception e) {
            model.addAttribute("users", userService.findAll());
        }
        return "user/users";
    }

    @RequestMapping(value = "/myprofile")
    public String myProfile(Authentication authentication,
                            Model model) {
        String username = authentication.getName();
        UserProfile userProfile;
        try {
            userProfile = userProfileService.findOne(username);
        } catch (NullPointerException e) {
            throw new UserNotFoundException();
        }
        model.addAttribute("userProfile", userProfile);
        model.addAttribute("profilForm", new ProfilForm());
        model.addAttribute("newSection", new NewSectionForm());
        return "user/user";
    }

    @RequestMapping(value = "/myprofile/edit", method = RequestMethod.GET)
    public String editMyProfile(Authentication authentication,
                            Model model) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        UserProfile userProfile;
        UserAdditionalInfo userAdditionalInfo = new UserAdditionalInfo();
        try {
            userProfile = userProfileService.findOne(username);
        } catch (NullPointerException e) {
            throw new UserNotFoundException();
        }

        userAdditionalInfo.setAboutMe(user.getInfo().getAboutMe());
        userAdditionalInfo.setCity(user.getInfo().getCity());
        userAdditionalInfo.setFooter(user.getInfo().getFooter());
        userAdditionalInfo.setName(user.getInfo().getName());
        userAdditionalInfo.setLastName(user.getInfo().getLastName());
        userAdditionalInfo.setPhone(user.getInfo().getPhone());

        model.addAttribute("userProfile", userProfile);
        model.addAttribute("userAdditionalInfo", userAdditionalInfo);
        model.addAttribute("profilForm", new ProfilForm());
        model.addAttribute("newSection", new NewSectionForm());


        return "user/user_edit_form";
    }

    @RequestMapping(value = "/user/edit/{id}", method = RequestMethod.GET)
    public String editProfile(Authentication authentication,
                              @PathVariable int id,
                                Model model) {
        if(!authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            throw new AccessDeniedException("You dont have permission for this operation");
        }

        String username = authentication.getName();
        User user = userService.findOne(id);
        UserProfile userProfile;
        UserAdditionalInfo userAdditionalInfo = new UserAdditionalInfo();
        try {
            userProfile = userProfileService.findOne(user.getUsername());
        } catch (NullPointerException e) {
            throw new UserNotFoundException();
        }

        userAdditionalInfo.setAboutMe(user.getInfo().getAboutMe());
        userAdditionalInfo.setCity(user.getInfo().getCity());
        userAdditionalInfo.setFooter(user.getInfo().getFooter());
        userAdditionalInfo.setName(user.getInfo().getName());
        userAdditionalInfo.setLastName(user.getInfo().getLastName());
        userAdditionalInfo.setPhone(user.getInfo().getPhone());

        model.addAttribute("userProfile", userProfile);
        model.addAttribute("userAdditionalInfo", userAdditionalInfo);
        model.addAttribute("profilForm", new ProfilForm());
        model.addAttribute("newSection", new NewSectionForm());


        return "user/user_edit_form";
    }

    @RequestMapping(value = "/myprofile/edit/confirm", method = RequestMethod.POST)
    public String confirmEditMyProfile(@Valid @ModelAttribute("profilForm") ProfilForm profilForm, Authentication authentication) {
        User user = userService.findByUsername(authentication.getName());

        user.getInfo().setAboutMe(profilForm.getAboutMe());
        user.getInfo().setCity(profilForm.getCity());
        user.getInfo().setFooter(profilForm.getFooter());
        user.getInfo().setName(profilForm.getName());
        user.getInfo().setLastName(profilForm.getLastName());
        user.getInfo().setPhone(profilForm.getPhone());

        userService.save(user);
        return "redirect:/myprofile";
    }

    @RequestMapping(value = "/myprofile/delete")
    public String deleteMyProfile(Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        userService.delete(user);
        return "redirect:/logout";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null) {
            model.addAttribute("error", "Your username or password is invalid.");
        }

        if (logout != null) {
            model.addAttribute("message", "You have been logged out successfully.");
        }

        return "home/login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(){
        return "home/register";
    }


    @RequestMapping(value = "/logout")
    public String logOutAndRedirectToLoginPage(Authentication authentication,
                                               HttpServletRequest request,
                                               HttpServletResponse response) {
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/login?logout=true";
    }

    @RequestMapping(value = "/myprofile/edit/sec/{name}", method = RequestMethod.POST)
    public String editSection(
            @Valid @ModelAttribute("newSection") NewSectionForm newSkupina,
            BindingResult result,
            Authentication authentication,
            @PathVariable String name) {

        if (result.hasErrors()) {
            return "user/user";
        }
        User user = userService.findByUsername(authentication.getName());
        Section section = sectionService.findByName(name);

        if (newSkupina.getIsPublic().toLowerCase().equals("private")) {
            section.setIsPublic(0);
        } else if (newSkupina.getIsPublic().toLowerCase().equals("members")){
            section.setIsPublic(2);
        } else {
            section.setIsPublic(1);
        }
        section.setUser(user);
        sectionService.save(section);
        return "redirect:/myprofile/";
    }
}
