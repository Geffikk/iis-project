package org.forum.service;

import org.forum.entities.Section;
import org.forum.entities.Topic;
import org.forum.entities.user.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface TopicService {

    List<Topic> findAll();

    Topic findOne(int id);

    Set<Topic> findRecent();

    Set<Topic> findAllByOrderByCreationDateDesc();

    Set<Topic> findBySection(Section section);

    Set<Topic> findBySection(String nazovSkupiny);

    Set<Topic> findBySection(int id);

    Topic save(Topic topic);

    Set<Topic> findByUser(User user);

    void delete(int id);

    void delete(Topic topic);
}
