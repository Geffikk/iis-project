package org.forum.entities.user.activation;

import org.forum.entities.user.User;


import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "activation_code")
public class ActivationCode {

    @Id
    @Column(name = "id")
    private String id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_uzivatela")
    private User user;

    @Column(name = "date", nullable = false)
    private Date timestamp;

    public ActivationCode(String id) {
        this.id = id;
        this.timestamp = new Date();
    }

    public ActivationCode() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "ActivationCode{" +
                "id='" + id + '\'' +
                ", uzivatel=" + user +
                ", timestamp=" + timestamp +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ActivationCode)) return false;
        ActivationCode that = (ActivationCode) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(user, that.user) &&
                Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, timestamp);
    }
}
