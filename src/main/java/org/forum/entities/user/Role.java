package org.forum.entities.user;

import javax.persistence.Embeddable;


public enum Role {
    UNDEFINED("UNDEFINED"),
    USER("USER"),
    ADMIN("ADMIN");

    private String name;


    Role() {
    }

    Role(String name) {
        this.name = name;
    }
}
