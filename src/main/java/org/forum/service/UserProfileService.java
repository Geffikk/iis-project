package org.forum.service;

import org.forum.entities.user.UserProfile;
import org.springframework.stereotype.Service;

@Service
public interface UserProfileService {

    public UserProfile findOne(int uzivatelId);

    public UserProfile findOne(String uzivatelskeMeno);
}
