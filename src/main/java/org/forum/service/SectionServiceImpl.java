package org.forum.service;

import org.forum.AccessRules;
import org.forum.entities.Section;
import org.forum.entities.user.User;
import org.forum.repository.SectionRepository;
import org.forum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SectionServiceImpl implements SectionService {

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private SectionService sectionService;

    @Override
    public List<Section> findAll() {
        return sectionRepository.findAll();
    }

    @Override
    public Section findOne(int id) {
        Optional<Section> optional = sectionRepository.findById(id);
        Section section = null;
        if (optional.isPresent()) {
            section = optional.get();
        } else {
            throw new RuntimeException("Skupina s id: " + id + " nebola najdena!");
        }
        return section;
    }

    @Override
    public Section findByName(String nazov) {
        return sectionRepository.findByName(nazov);
    }

    @Override
    public Section save(Section section) {
        return sectionRepository.save(section);
    }

    @Override
    public Set<Section> findByUser(User user) {
        return sectionRepository.findByUser(user);
    }

//    @Override
//    public Set<Section> findAllByModerators() {
//        return sectionRepository.findAllByModerators();
//    }
//
//    @Override
//    public Set<Section> findAllByMembers() {
//        return sectionRepository.findAllByMembers();
//    }

    @Override
    public void delete(int id) {
        delete(findOne(id));
    }

    @Override
    public void delete(Section section) {
        sectionRepository.delete(section);
    }

    public boolean deleteUserInCurrentSection(Authentication auth, User userForDelete, Section section,Integer id_S) {
        List<GrantedAuthority> updatedAuthorities =
                auth.getAuthorities().stream()
                        .filter(r -> !id_S.toString().equals(r.getAuthority()))
                        .collect(Collectors.toList());

        Authentication newAuth = new UsernamePasswordAuthenticationToken(
                auth.getPrincipal(), auth.getCredentials(), updatedAuthorities);
        SecurityContextHolder.getContext().setAuthentication(newAuth);

        List<String> permissions = userForDelete.getPermissionList();
        permissions.remove(id_S.toString());
        userForDelete.setPermissionsFromListToString(permissions);

        userService.save(userForDelete);
        section.getMembers().remove(userForDelete);

        if(section.getModerators().contains(userForDelete)) {
            section.getModerators().remove(userForDelete);
        }

        sectionService.save(section);
        return true;
    }

    public boolean deleteModeratorInCurrentSection(Authentication auth, User userForDelete, Section section, Integer id_S) {
        List<GrantedAuthority> updatedAuthorities =
                auth.getAuthorities().stream()
                        .filter(r -> !id_S.toString().equals(r.getAuthority()))
                        .collect(Collectors.toList());

        Authentication newAuth = new UsernamePasswordAuthenticationToken(
                auth.getPrincipal(), auth.getCredentials(), updatedAuthorities);
        SecurityContextHolder.getContext().setAuthentication(newAuth);

        List<String> permissions = userForDelete.getPermissionList();
        permissions.remove(id_S.toString());
        userForDelete.setPermissionsFromListToString(permissions);

        userService.save(userForDelete);
        section.getModerators().remove(userForDelete);
        sectionService.save(section);
        return true;
    }

    public boolean addUserInCurrentSection(Authentication auth, User userForAdd, Section section,Integer id_S) {
        // Pridanie prav do authentication
        List<GrantedAuthority> updatedAuthorities = new ArrayList<>(auth.getAuthorities());
        updatedAuthorities.add(new SimpleGrantedAuthority(id_S.toString()));
        Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), updatedAuthorities);
        SecurityContextHolder.getContext().setAuthentication(newAuth);

        // Pridanie prav do DB
        List<String> permissions = userForAdd.getPermissionList();
        permissions.add(id_S.toString());
        userForAdd.setPermissionsFromListToString(permissions);

        section.getMembers().add(userForAdd);
        // Save
        userService.save(userForAdd);
        sectionService.save(section);
        return true;
    }

    public boolean addModeratorInCurrentSection(User userForAdd, Section section) {
        // Pridanie do DB Skupina -> moderators
        section.getModerators().add(userForAdd);

        // Pridanie prav do DB (skupina -> list moderatorov)
        List<User> moderators = section.getModerators();
        section.setModeratorsFromListToString(moderators);
        sectionService.save(section);
        return true;
    }
}