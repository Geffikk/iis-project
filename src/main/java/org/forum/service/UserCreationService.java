package org.forum.service;

import org.forum.entities.user.User;
import org.springframework.stereotype.Service;

@Service
public interface UserCreationService {

    void create(User user);
}
