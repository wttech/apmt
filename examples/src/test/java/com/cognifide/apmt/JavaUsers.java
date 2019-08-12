package com.cognifide.apmt;

import com.google.common.collect.ImmutableList;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public enum JavaUsers implements User {
    AUTHOR("admin", "admin", Collections.singletonList(KotlinGroups.AUTHOR)),
    SUPER_AUTHOR("admin", "admin", Collections.singletonList(KotlinGroups.SUPER_AUTHOR));

    private final String username;
    private final String password;
    private final List<Group> groups;

    JavaUsers(String username, String password, List<KotlinGroups> groups) {
        this.username = username;
        this.password = password;
        this.groups = ImmutableList.copyOf(groups);
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

    @NotNull
    @Override
    public List<Group> getGroups() {
        return groups;
    }
}
