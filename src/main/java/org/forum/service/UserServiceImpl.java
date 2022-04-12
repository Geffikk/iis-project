package org.forum.service;

import org.forum.entities.Topic;
import org.forum.entities.user.User;
import org.forum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findOne(int id) {
            Optional<User> optional = userRepository.findById(id);
            User user = null;
            if(optional.isPresent()) {
                user = optional.get();
            }else {
                throw new RuntimeException("Uzivatel s id: " + id + " nebol najdeny!");
            }
            return user;
    }

    @Override
    public User findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("Uzivatel s menom: " + username + " nebol najdeny!");
        }
        return user;
    }

    public List<User> findAllByUsername(String name) {
        List<User> user = userRepository.findByUsernameLike("%" + name + "%");

        if (user == null) {
            throw new RuntimeException("Uzivatel s menom: " + name + " nebol najdeny!");
        }
        return user;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }
}