package org.forum.service;

import org.forum.entities.user.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    List<User> findAll();

    User findOne(int id);

    User findByUsername(String username);

    User save(User user);

    List<User> findAllByUsername(String name);

    void delete(User user);
}