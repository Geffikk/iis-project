package org.forum.entities.user;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserCreationForm {

    @Email(message = "{Email.User.email.validation}")
    @NotEmpty(message = "{NotEmpty.User.email.validation}")
    private String email;


    @Pattern(regexp = "(.{8,64})", message = "Pattren.User.password.validation")
    private String password;

    @Size(min = 3, max = 50, message = "{Size.User.username.validation}")
    @Pattern(regexp = "[a-zA-Z0-9_\\-]*", message = "{Pattern.User.username.validation}")
    private String username;

    @Size(min = 2, max = 20)
    private String name;

    @Size(min = 2, max = 30)
    private String lastName;

    @Size(max = 30)
    private String isPublic;

    @JsonIgnore
    public User getNewUserEntity() {
        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password);

        UserAdditionalInfo userAdditionalInfo = new UserAdditionalInfo();
        userAdditionalInfo.setName(name);
        userAdditionalInfo.setLastName(lastName);
        userAdditionalInfo.setUser(user);

        user.setInfo(userAdditionalInfo);

        return user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(String isPublic) {
        this.isPublic = isPublic;
    }
}
