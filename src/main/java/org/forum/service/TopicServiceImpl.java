package org.forum.service;

import org.forum.entities.Section;
import org.forum.entities.Topic;
import org.forum.entities.user.User;
import org.forum.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private SectionService sectionService;


    @Override
    public List<Topic> findAll() {
        return topicRepository.findAll();
    }

    @Override
    public Topic findOne(int id) {
        Optional<Topic> optional = topicRepository.findById(id);
        Topic topic = null;
        if(optional.isPresent()) {
            topic = optional.get();
        }else {
            throw new RuntimeException("Vlakno s id: " + id + " nebolo najdene!");
        }
        return topic;
    }

    @Override
    public Set<Topic> findRecent() {
        return topicRepository.findTop5ByOrderByCreationDateDesc();
    }

    @Override
    public Set<Topic> findAllByOrderByCreationDateDesc() {
        return topicRepository.findAllByOrderByCreationDateDesc();
    }

    @Override
    public Set<Topic> findBySection(Section section) {
        return topicRepository.findBySection(section);
    }

    @Override
    public Set<Topic> findBySection(String sectionName) {
        return findBySection(sectionService.findByName(sectionName));
    }

    @Override
    public Set<Topic> findBySection(int id) {
        return findBySection(sectionService.findOne(id));
    }

    @Override
    public Topic save(Topic topic) {
        return topicRepository.save(topic);
    }

    @Override
    public Set<Topic> findByUser(User user) {
        return topicRepository.findByUser(user);
    }

    @Override
    public void delete(int id) {
        delete((findOne(id)));
    }

    @Override
    public void delete(Topic topic) {
        topicRepository.delete(topic);
    }
}
