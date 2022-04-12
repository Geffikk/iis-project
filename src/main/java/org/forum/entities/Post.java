package org.forum.entities;

import org.forum.entities.user.User;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "prispevok")
public class Post {

    /** ID PRISPEVKU **/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    /** VLAKNO PRISPEVKU**/
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "id_vlakna")
    private Topic topic;

    /** ZAKLADATEL PRISPEVKU **/
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "id_uzivatela")
    private User user;

    /** OBSAH PRISPEVKU **/
    @Column(name = "obsah")
    private String content;

    /** RANKING PRISPEVKU **/
    @Column(name = "ranking")
    private Integer ranking;

    @Column(name = "datum_zalozenia", updatable = false, nullable = false)
    private Date creationDate;

    @Column(name = "posledny_datum_upravy", nullable = false)
    private Date lastUpdateDate;

    /** CONSTRUCTORS **/
    public Post() {
    }


    @PrePersist
    protected void onCreate() {
        this.creationDate = new Date();
        this.lastUpdateDate = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.lastUpdateDate = new Date();
    }

    public Post(String obsah, Integer ranking) {
        this.content = obsah;
        this.ranking = ranking;
    }

    /** GETTERS AND SETTERS **/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", topic=" + topic +
                ", user=" + user +
                ", content='" + content + '\'' +
                ", ranking=" + ranking +
                ", creationDate=" + creationDate +
                ", lastUpdateDate=" + lastUpdateDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Post)) return false;
        Post post = (Post) o;
        return id == post.id &&
                Objects.equals(topic, post.topic) &&
                Objects.equals(user, post.user) &&
                Objects.equals(content, post.content) &&
                Objects.equals(ranking, post.ranking) &&
                Objects.equals(creationDate, post.creationDate) &&
                Objects.equals(lastUpdateDate, post.lastUpdateDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, topic, user, content, ranking, creationDate, lastUpdateDate);
    }
}
