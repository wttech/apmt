package com.cognifide.apmt;

import org.jetbrains.annotations.NotNull;

public enum JavaUsers implements User {
    AUTHOR("admin", "admin"),
    SUPER_AUTHOR("admin", "admin");

    private final String username;
    private final String password;

    JavaUsers(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @NotNull
    @Override
    public String getUsername() {
        return username;
    }

    @NotNull
    @Override
    public String getPassword() {
        return password;
    }

}
