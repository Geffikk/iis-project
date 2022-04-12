package org.forum.service;

import org.forum.entities.Post;
import org.forum.entities.Topic;
import org.forum.entities.user.User;
import org.forum.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TopicService topicService;

    @Autowired
    private UserService userService;

    @Override
    public Post findOne(int id) {
        Optional<Post> optional = postRepository.findById(id);
        Post post = null;
        if(optional.isPresent()) {
            post = optional.get();
        }else {
            throw new RuntimeException("Prispevok s id: " + id + " nebol najdeny!");
        }
        return post;
    }

    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public Set<Post> findRecent() {
        return postRepository.findTop5ByOrderByCreationDateDesc();
    }

    @Override
    public Set<Post> findAllByOrderByCreationDateDesc() {
        return postRepository.findAllByOrderByCreationDateDesc();
    }

    @Override
    public Set<Post> findByUser(User user) {
        return postRepository.findByUser(user);
    }

    @Override
    public Set<Post> findByTopic(int idVlakna) {
        return findByTopic(topicService.findOne(idVlakna));
    }

    @Override
    public Set<Post> findByTopic(Topic topic) {
        return postRepository.findByTopic(topic);
    }

    @Override
    public void save(Post post) {
        postRepository.save(post);
    }

    @Override
    public void delete(int id) {
        delete(findOne(id));
    }

    @Override
    public void delete(Post post) {
        postRepository.delete(post);
    }

    @Override
    public void save(String content, String username, int idTopic) {
        Post post = new Post();
        post.setTopic(topicService.findOne(idTopic));
        post.setUser(userService.findByUsername(username));
        post.setContent(content);
        save(post);
    }
}
