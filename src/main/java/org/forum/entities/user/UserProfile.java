package org.forum.entities.user;

import org.forum.entities.Post;
import org.forum.entities.Section;
import org.forum.entities.Topic;

import java.util.Set;

public class UserProfile {

    private User user;

    private Set<Post> posts;

    private Set<Topic> topics;

    private Set<Section> sections;



    public UserProfile() {
    }

    public UserProfile(User user, Set<Post> posts, Set<Topic> topics, Set<Section> sections) {
        this.user = user;
        this.posts = posts;
        this.topics = topics;
        this.sections = sections;
    }



    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }

    public Set<Topic> getTopics() {
        return topics;
    }

    public void setTopics(Set<Topic> topics) {
        this.topics = topics;
    }

    public Set<Section> getSections() {
        return sections;
    }

    public void setSections(Set<Section> sections) {
        this.sections = sections;
    }
}
