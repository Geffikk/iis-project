package org.forum.entities;


import org.forum.entities.user.User;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "vlakno")
public class Topic {

    /** ID VLAKNA **/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    /** ZAKLADATEL VLAKNA **/
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "id_uzivatela")
    private User user;

    /** VLAKNO PATRI DO SKUPINY **/
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "id_skupiny")
    private Section section;


    /** PREDMET VLAKNA **/
    @Column(name = "predmet", length = 50)
    private String title;

    @Column(name = "kontent", columnDefinition = "TEXT")
    private String content;

    @Column(name = "pohlady")
    private int views;

    @Column(name = "datum_zalozenia", updatable = false, nullable = false)
    private Date creationDate;

    @Column(name = "posledny_datum_upravy")
    private Date lastUpdateDate;

    @Column(name = "je_zavrete")
    private boolean closed;

    /** CONSTRUCTORS **/
    public Topic() {
    }

    public Topic(String predmet, String kontent) {
        this.content = kontent;
        this.title = predmet;
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



    /** GETTERS AND SETTERS **/
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
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

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    @Override
    public String toString() {
        return "Topic{" +
                "id=" + id +
                ", user=" + user +
                ", section=" + section +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", views=" + views +
                ", creationDate=" + creationDate +
                ", lastUpdateDate=" + lastUpdateDate +
                ", closed=" + closed +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Topic)) return false;
        Topic topic = (Topic) o;
        return id == topic.id &&
                views == topic.views &&
                closed == topic.closed &&
                Objects.equals(user, topic.user) &&
                Objects.equals(section, topic.section) &&
                Objects.equals(title, topic.title) &&
                Objects.equals(content, topic.content) &&
                Objects.equals(creationDate, topic.creationDate) &&
                Objects.equals(lastUpdateDate, topic.lastUpdateDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, section, title, content, views, creationDate, lastUpdateDate, closed);
    }
}
