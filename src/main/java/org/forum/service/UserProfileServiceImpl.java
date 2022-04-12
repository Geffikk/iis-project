package org.forum.service;

import org.forum.entities.user.User;
import org.forum.entities.user.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private SectionService sectionService;

    @Override
    public UserProfile findOne(int uzivatelId) {
        UserProfile userProfile = new UserProfile();
        User user = userService.findOne(uzivatelId);
        userProfile.setUser(user);
        userProfile.setPosts(postService.findByUser(user));
        userProfile.setTopics(topicService.findByUser(user));
        userProfile.setSections(sectionService.findByUser(user));
        return userProfile;
    }

    @Override
    public UserProfile findOne(String username) {
        return findOne(userService.findByUsername(username).getId());
    }
}
