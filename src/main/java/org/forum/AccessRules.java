package org.forum;

public class AccessRules {

    protected static final String[] FOR_EVERYONE = {
        "/error",
        "/users/**",
        "/user/**",
        "/"
    };

    protected static final String[] FOR_AUTHORIZED_USERS = {
        "/topic/new/**",
        "/topic/delete/**",
        "/section/delete/**",
        "/section/new/**",
        "/post/**",
        "/section/**/add/**",
        "/section/add/**",
        "/myprofile/**"};

    protected static final String[] FOR_ADMINS = {
        "/admin/**",
        "/topic/new/**",
        "/topic/delete/**",
        "/section/delete/**",
        "/section/new/**",
        "/post/**",
        "/section/**/add/**",
        "/section/add/**",
        "/myprofile/**"};

    protected static final String[] ADMINS_ROLES = {"ADMIN", "USER", "MODERATOR"};

}
