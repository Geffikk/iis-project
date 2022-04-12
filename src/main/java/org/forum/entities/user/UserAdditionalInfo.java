package org.forum.entities.user;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "uzivatelske_info")
public class UserAdditionalInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @OneToOne(mappedBy = "info")
    private User user;

    @Column(name = "mobil", length = 20)
    private String phone;

    @Column(name = "meno", length = 20)
    private String name;

    @Column(name = "priezvisko", length = 30)
    private String lastName;


    @Column(name = "mesto", length = 20)
    private String city;

    @Column(name = "bibliografia", length = 150)
    private String aboutMe;

    @Column(name = "footer", length = 50)
    private String footer;

    public UserAdditionalInfo() {
    }

    public UserAdditionalInfo(String phone, String name, String lastName, String city, String aboutMe, String footer) {
        this.phone = phone;
        this.name = name;
        this.lastName = lastName;
        this.city = city;
        this.aboutMe = aboutMe;
        this.footer = footer;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }



}
