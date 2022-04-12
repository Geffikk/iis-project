package org.forum.entities.user.activation;


import org.forum.entities.user.User;

public interface ActivationSenderService {

    void sendActivationCode(User user);

}
