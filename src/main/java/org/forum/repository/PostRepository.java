package org.forum.repository;

import org.forum.entities.Post;
import org.forum.entities.Topic;
import org.forum.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    Set<Post> findByUser(User user);

    Set<Post> findByTopic(Topic topic);

    Set<Post> findAllByOrderByCreationDateDesc();

    Set<Post> findTop5ByOrderByCreationDateDesc();
}
