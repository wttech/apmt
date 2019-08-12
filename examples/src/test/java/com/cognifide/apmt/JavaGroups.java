package com.cognifide.apmt;

import org.jetbrains.annotations.NotNull;

public enum JavaGroups implements Group {
    AUTHOR("author"),
    SUPER_AUTHOR("super-author");

    private final String groupName;

    JavaGroups(String groupName) {
        this.groupName = groupName;
    }

    @NotNull
    @Override
    public String getGroupName() {
        return groupName;
    }
}
