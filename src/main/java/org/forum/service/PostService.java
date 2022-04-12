package org.forum.service;

import org.forum.entities.Post;
import org.forum.entities.Topic;
import org.forum.entities.user.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface PostService {

    Post findOne(int id);

    List<Post> findAll();

    Set<Post> findRecent();

    Set<Post> findAllByOrderByCreationDateDesc();

    Set<Post> findByUser(User user);

    Set<Post> findByTopic(int idVlakna);

    Set<Post> findByTopic(Topic topic);

    void save(Post post);

    void delete(int id);

    void delete(Post post);

    void save(String content,
              String username,
              int idTopic);
}
