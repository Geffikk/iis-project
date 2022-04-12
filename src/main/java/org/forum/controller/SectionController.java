package org.forum.controller;

import org.forum.entities.Section;
import org.forum.entities.user.User;
import org.forum.newform.NewSectionForm;
import org.forum.service.SectionService;
import org.forum.service.UserService;
import org.forum.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@ComponentScan
@Controller
@RequestMapping("/section/")
public class SectionController {

    @Autowired
    private SectionService sectionService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private UserService userService;

    /** SECTION OVERVIEW **/
    @RequestMapping("{id}")
    public String getTopicsFromSection(@PathVariable int id,
                                       Model model) {
        model.addAttribute("section", sectionService.findOne(id));
        model.addAttribute("topics", topicService.findBySection(id));
        return "section/section";
    }

    /** SECTION MODERATORS OVERVIEW **/
    @RequestMapping(value = "{id}/moderators", method = RequestMethod.GET)
    public String getModeratorsOfSection(@PathVariable int id,
                                       Model model) {
        model.addAttribute("section", sectionService.findOne(id));
        model.addAttribute("users", sectionService.findOne(id).getModerators());
        return "section/moderators";
    }

    /** SECTION MEMBERS OVERVIEW **/
    @RequestMapping(value = "{id}/members", method = RequestMethod.GET)
    public String getMembersOfSection(@PathVariable int id, Model model) {
        model.addAttribute("section", sectionService.findOne(id));
        model.addAttribute("users", sectionService.findOne(id).getMembers());
        return "section/members";
    }

    @RequestMapping(value = "add/member/{id}", method = RequestMethod.GET)
    public String addMembersToSection(@PathVariable int id, Model model, Authentication authentication){
        model.addAttribute("section", sectionService.findOne(id));
        model.addAttribute("users", userService.findAll());

        Section section = sectionService.findOne(id);

        if(!section.getMembersUsername().contains(authentication.getName()) && !authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            throw new AccessDeniedException("You have no permission for invite member in this section");
        }

        return "section/add_member";
    }

    @RequestMapping(value = "add/moderator/{id}", method = RequestMethod.GET)
    public String addModeratorsToSection(@PathVariable int id, Model model, Authentication authentication){
        model.addAttribute("section", sectionService.findOne(id));
        model.addAttribute("users", userService.findAll());

        Section section = sectionService.findOne(id);

        if(!section.getUser().getUsername().equals(authentication.getName()) && !authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            throw new AccessDeniedException("You have no permission for invite member in this section");
        }
        return "section/add_moderator";
    }

    @RequestMapping(value = "new", method = RequestMethod.GET)
    public String getNewSectionForm(Model model) {
        model.addAttribute("newSection", new NewSectionForm());
        return "section/new_section_form";
    }

    /** CREATE NEW SECTION AND REDIRECT TO THIS SECTION VIEW**/
    @RequestMapping(value = "new", method = RequestMethod.POST)
    public String processAndAddNewSection(
            @Valid @ModelAttribute("newSection") NewSectionForm newSkupina,
            BindingResult result,
            Authentication authentication) {

        if (result.hasErrors()) {
            return "section/new_section_form";
        }
        User user = userService.findByUsername(authentication.getName());

        Section section = new Section();
        section.setName(newSkupina.getName());
        section.setDescription(newSkupina.getDescription());

        if (newSkupina.getIsPublic().toLowerCase().equals("public")) {
            section.setIsPublic(1);
        } else {
            section.setIsPublic(0);
        }
        section.setUser(user);
        section.getModerators().add(user);
        section = sectionService.save(section);
        return "redirect:/section/" + section.getId();
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable int id,
                         Authentication authentication,
                         RedirectAttributes model) {
        User user = userService.findByUsername(authentication.getName());
        Section section = sectionService.findOne(id);

        if(!user.equals(section.getUser()) || !section.getModerators().contains(user) && !authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))){
            throw new AccessDeniedException("");
        }

        sectionService.delete(id);

        model.addFlashAttribute("message", "section.successfully.deleted");
        return "redirect:/home";
    }

    @RequestMapping(value = "delete/user/{id_S}/{id_U}", method = RequestMethod.GET)
    public String deleteUserFromSection(@PathVariable Integer id_S,
                                        @PathVariable Integer id_U) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Section section = sectionService.findOne(id_S);

        if(!section.getUser().getUsername().equals(auth.getName()) && !auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            throw new AccessDeniedException("You dont have permission for delete User");
        }

        User user = userService.findOne(id_U);
        sectionService.deleteUserInCurrentSection(auth, user, section, id_S);

        return "redirect:/section/"+section.getId()+"/members";
    }

    @RequestMapping(value = "delete/moderator/{id_S}/{id_U}", method = RequestMethod.GET)
    public String deleteModeratorFromSection(@PathVariable Integer id_S,
                                        @PathVariable Integer id_U) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Section section = sectionService.findOne(id_S);
        User user = userService.findOne(id_U);

        if(!section.getUser().getUsername().equals(auth.getName()) && !auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            throw new AccessDeniedException("You dont have permission for delete Moderator");
        }

        sectionService.deleteModeratorInCurrentSection(auth, user, section, id_S);

        return "redirect:/section/"+section.getId()+"/moderators";
    }

    @RequestMapping(value = "add/member/{id_S}/{id_U}", method = RequestMethod.POST)
    public String addMemberToSection(@PathVariable Integer id_S, @PathVariable Integer id_U, Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findOne(id_U);
        Section section = sectionService.findOne(id_S);

        if(!section.getUser().getUsername().equals(auth.getName()) && !auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            throw new AccessDeniedException("You dont have permission for add user");
        }

        sectionService.addUserInCurrentSection(auth, user, section, id_S);

        return "redirect:/section/"+section.getId()+"/members";
    }

    @RequestMapping(value = "add/moderator/{id_S}/{id_U}", method = RequestMethod.POST)
    public String addModeratorToSection(@PathVariable Integer id_S, @PathVariable Integer id_U, Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findOne(id_U);
        Section section = sectionService.findOne(id_S);

        if(!section.getUser().getUsername().equals(auth.getName()) && !auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            throw new AccessDeniedException("You dont have permission for add moderator");
        }

        sectionService.addModeratorInCurrentSection(user, section);

        return "redirect:/section/"+section.getId()+"/moderators";
    }

    @RequestMapping(value = "{id}/register", method = RequestMethod.GET)
    public String registerToSection(@PathVariable Integer id, Authentication auth, Model model, String error){
        Section section = sectionService.findOne(id);

        if (section.getRegisterApplicationAsList().contains(auth.getName())) {
            System.out.println("Uz si poziadal o registraciu");
        } else {
            section.setApplication_registers_user(auth.getName());
        }

        sectionService.save(section);
        return "redirect:/";
    }

    @RequestMapping(value = "{id}/applications", method = RequestMethod.GET)
    public String getApplicationsOfSection(@PathVariable int id,
                                         Model model, Authentication authentication) {
        model.addAttribute("section", sectionService.findOne(id));
        model.addAttribute("users", userService.findAll());

        Section section = sectionService.findOne(id);

        if(!section.getUser().getUsername().equals(authentication.getName()) && !authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            throw new AccessDeniedException("You dont have permission for this operation !");
        }

        return "section/register_application";
    }

    @RequestMapping(value = "{id_S}/accept/{id_U}", method = RequestMethod.GET)
    public String acceptApplicationsOfSection(@PathVariable int id_S,
                                              @PathVariable int id_U,
                                              Authentication authentication) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Section section = sectionService.findOne(id_S);
        User user = userService.findOne(id_U);

        if(!section.getUser().getUsername().equals(authentication.getName()) && !authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            throw new AccessDeniedException("You dont have permission for this operation !");
        }

        List<String> str = section.getRegisterApplicationAsList();
        str.remove(user.getUsername());

        section.setApplicationFromListToString(str);
        sectionService.addUserInCurrentSection(auth, user, section, id_S);
        sectionService.save(section);
        return "section/register_application";
    }

    @RequestMapping(value = "{id}/register/moderator", method = RequestMethod.GET)
    public String registerModeratorToSection(@PathVariable Integer id, Authentication auth){
        Section section = sectionService.findOne(id);

        if (section.getRegisterModeratorApplicationAsList().contains(auth.getName())) {
            System.out.println("Uz si poziadal o registraciu");
        } else {
            section.setApplication_registers_moderator(auth.getName());
        }

        if(!section.getMembersUsername().contains(auth.getName()) && !auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            throw new AccessDeniedException("You dont have permission for this operation !");
        }

        sectionService.save(section);
        return "redirect:/";
    }

    @RequestMapping(value = "{id}/applications/moderator", method = RequestMethod.GET)
    public String getApplicationsModeratorOfSection(@PathVariable int id,
                                           Model model, Authentication authentication) {
        model.addAttribute("section", sectionService.findOne(id));
        model.addAttribute("users", userService.findAll());

        Section section = sectionService.findOne(id);

        if(!section.getUser().getUsername().equals(authentication.getName()) && !authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            throw new AccessDeniedException("You dont have permission for this operation !");
        }

        return "section/register_application_moderator";
    }

    @RequestMapping(value = "{id_S}/accept/moderator/{id_U}", method = RequestMethod.GET)
    public String acceptApplicationsModeratorOfSection(@PathVariable int id_S,
                                              @PathVariable int id_U,
                                              Authentication authentication,
                                              Model model) {

        Section section = sectionService.findOne(id_S);
        User user = userService.findOne(id_U);

        if(!section.getUser().getUsername().equals(authentication.getName()) && !authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            throw new AccessDeniedException("You dont have permission for this operation !");
        }

        List<String> str = section.getRegisterModeratorApplicationAsList();
        str.remove(user.getUsername());

        section.setApplicationModeratorFromListToString(str);
        sectionService.addModeratorInCurrentSection(user, section);
        sectionService.save(section);
        return "section/register_application_moderator";
    }
}
