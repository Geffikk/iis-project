package org.forum.entities;

import org.forum.entities.user.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.*;
import java.util.*;


@Entity
@Table(name = "skupina")
public class Section {

    /** ID SKUPINY**/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    /** ZAKLADATEL SKUPINY **/
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "id_uzivatela")
    private User user;

    /** NAZOV SKUPINY **/
    @Column(name = "nazov", length = 50)
    private String name;

    /** POPIS SKUPINY **/
    @Column(name = "popis", length = 150)
    private String description;

    @Column(name = "datum_zalozenia", updatable = false, nullable = false)
    private Date creationDate;

    @Column(name = "je_verejna")
    private int isPublic = 1;

    @ManyToMany
    @JoinTable(
            name = "moderatori_skupiny",
            joinColumns = @JoinColumn(name = "id_skupiny"),
            inverseJoinColumns = @JoinColumn(name = "id_uzivatela")
    )

    private List<User> moderators;

    @Column(name = "list_moderatorov")
    private String moderators_list = "";

    @ManyToMany
    @JoinTable(
            name = "clenovia_skupiny",
            joinColumns = @JoinColumn(name = "id_skupiny"),
            inverseJoinColumns = @JoinColumn(name = "id_uzivatela")
    )
    private List<User> members;

    @Column(name = "list_register_user")
    private String application_registers_user = "";

    @Column(name = "list_register_moderator")
    private String application_registers_moderator = "";

    /** CONSTRUCTORS **/
    public Section() {
    }

    public Section(String nazov, String popis) {
        this.name = nazov;
        this.description = popis;
    }

    @PrePersist
    protected void onCreate() {
        this.creationDate = new Date();
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public int getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(int isPublic) {
        this.isPublic = isPublic;
    }

    public List<User> getModerators() {
        return moderators;
    }

    public void setModerators(List<User> moderators) {
        this.moderators = moderators;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    public String getModerators_list() {
        return moderators_list;
    }

    public void setModerators_list(String moderators_list) {
        this.moderators_list = moderators_list;
    }

    public String getApplication_registers_user() {
        return application_registers_user;
    }

    public void setApplication_registers_user(String application_registers_user) {
        this.application_registers_user = application_registers_user;
    }

    public String getApplication_registers_moderator() {
        return application_registers_moderator;
    }

    public void setApplication_registers_moderator(String application_registers_moderator) {
        this.application_registers_moderator = application_registers_moderator;
    }

    @Override
    public String toString() {
        return "Section{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Section)) return false;
        Section section = (Section) o;
        return id == section.id &&
                Objects.equals(name, section.name) &&
                Objects.equals(description, section.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }

    public List<User> getMembersNotModerators() {
        List<User> moderators = getModerators();
        List<User> members = getMembers();

        members.removeAll(moderators);
        return members;
    }

    public List<String> getModeratorsAsList() {
        List<String> temp_application_registers_user = new ArrayList<>();
        if(this.application_registers_user.length() > 0) {
            temp_application_registers_user = Arrays.asList(this.application_registers_user.split(","));
            return new ArrayList<>(temp_application_registers_user);
        }
        return new ArrayList<>();
    }


    public List<String> getMembersUsername() {
        List<User> users = getMembers();
        List<String> usernames = new ArrayList<>();

        for (User value : users) {
            usernames.add(value.getUsername());
        }
        return usernames;
    }

    public List<String> getModeratorsUsername() {
        List<User> moderators = getModerators();
        List<String> usernames = new ArrayList<>();

        for (User value : moderators) {
            usernames.add(value.getUsername());
        }
        return usernames;
    }

    public void setModeratorsFromListToString(List<User> moderators) {
        StringBuilder moderatorsAsString = new StringBuilder();
        for(int i = 0; i<moderators.size(); i++) {
            if (i < moderators.size()-1) {
                moderatorsAsString.append(moderators.get(i).getUsername()).append(",");
            } else {
                moderatorsAsString.append(moderators.get(i).getUsername());
            }
        }
        setModerators_list(moderatorsAsString.toString());
    }

    public void setApplicationFromListToString(List<String> application) {
        StringBuilder applicationAsString = new StringBuilder();
        for(int i = 0; i<application.size(); i++) {
            if (i < application.size()-1) {
                applicationAsString.append(application.get(i)).append(",");
            } else {
                applicationAsString.append(application.get(i));
            }
        }
        setApplication_registers_user(applicationAsString.toString());
    }

    public List<String> getRegisterApplicationAsList() {
        List<String> temp_register_application = new ArrayList<>();
        if(this.application_registers_user.length() > 0) {
            temp_register_application = Arrays.asList(this.application_registers_user.split(","));
            return new ArrayList<>(temp_register_application);
        }
        return new ArrayList<>();
    }

    public void setApplicationModeratorFromListToString(List<String> application) {
        StringBuilder applicationAsString = new StringBuilder();
        for(int i = 0; i<application.size(); i++) {
            if (i < application.size()-1) {
                applicationAsString.append(application.get(i)).append(",");
            } else {
                applicationAsString.append(application.get(i));
            }
        }
        setApplication_registers_moderator(applicationAsString.toString());
    }

    public List<String> getRegisterModeratorApplicationAsList() {
        List<String> temp_register_application = new ArrayList<>();
        if(this.application_registers_moderator.length() > 0) {
            temp_register_application = Arrays.asList(this.application_registers_moderator.split(","));
            return new ArrayList<>(temp_register_application);
        }
        return new ArrayList<>();
    }

    public boolean canRegister() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(!getMembersUsername().contains(auth.getName())) {
            return true;
        }
        return false;
    }
}
