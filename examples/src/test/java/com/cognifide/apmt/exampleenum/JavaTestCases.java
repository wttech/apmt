package com.cognifide.apmt.exampleenum;

import com.cognifide.apmt.*;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public enum JavaTestCases implements TestCase {

    ADD_ASSETS(
            newArrayList(
                    "/content/dam/we-retail-screens",
                    "/content/dam/we-retail"
            ),
            newArrayList(
                    JavaUsers.AUTHOR,
                    JavaUsers.SUPER_AUTHOR
            )
    );

    private final List<String> paths;
    private final List<User> users;

    JavaTestCases(List<String> paths, List<User> users) {
        this.paths = ImmutableList.copyOf(paths);
        this.users = ImmutableList.copyOf(users);
    }

    @NotNull
    @Override
    public TestCaseConfiguration toTestCaseConfiguration() {
        return new TestCaseConfiguration(Lists.newArrayList(KotlinUsers.values()), users, paths);
    }
}
