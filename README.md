![Cognifide logo](http://cognifide.github.io/images/cognifide-logo.png)

[![Build Status](https://travis-ci.org/Cognifide/APM.svg?branch=master)](https://travis-ci.org/Cognifide/APM)
[![Apache License, Version 2.0, January 2004](https://img.shields.io/github/license/cognifide/apm.svg?label=License)](http://www.apache.org/licenses/)

# APMT

APMT (**A**EM **P**ermission **M**atrix **T**ester) is a tool which speeds up creation of permission tests for application built on top of AEM. 

## References
* [REST-assured 4](http://rest-assured.io/)
* [JUnit 5](https://junit.org/junit5/)

## Getting started
APMT requires `apmt.yaml` file, which provides data about admin user, and author and publish instances. Admin doesn't need to be exactly admin user, but it must be the user, who is able to perform all tested actions, and revert them. So when you want to test creation of pages, admin must be able to create page under provided path, and also to delete it. 
 ```yaml
# Instance configuration
---
admin:
  username: admin
  password: admin
instances:
  author:
    name: author@local
    url: http://localhost:8080
    headers: # additional headers sent to server in all requests 
      apmt-header1: apmt-value1
      apmt-header2: apmt-value2
  publish:
    name: publish@local
    url: http://localhost:8080
    headers:
      apmt-header1: apmt-value1
      apmt-header2: apmt-value2
```
The next thing required by APMT is list of users.
```kotlin
package com.cognifide.apmt.tests

import com.cognifide.apmt.User

enum class Users(
    override val username: String,
    override val password: String
) : User {
    USER("user", "password"),
    AUTHOR("author", "password"),
    SUPER_AUTHOR("super-author", "password")
}
```
And definition of test cases.
```kotlin
package com.cognifide.apmt.tests

import com.cognifide.apmt.TestCase
import com.cognifide.apmt.TestCaseConfiguration

enum class TestCases(private val initConfig: TestCaseConfiguration.() -> Unit) : TestCase {

    CREATE_PAGE({
        paths(
            "/content/my-site/en_gl/home"
        )
        allowedUsers(
            ExampleUsers.AUTHOR,
            ExampleUsers.SUPER_AUTHOR
        )
    }),
    OPEN_PAGE({
        paths(
            "/content/my-site/en_gl/home"
        )
        allowedUsers(
            *ExampleUsers.values()
        )
    });

    override fun toTestCaseConfiguration(): TestCaseConfiguration {
        val testCaseConfiguration = TestCaseConfiguration().apply(initConfig)
        testCaseConfiguration.allUsers(ExampleUsers.values())
        return testCaseConfiguration
    }
}
```
Finally, you can add permissions tests. Here is more complicated example, test which creates a page.
```kotlin
package com.cognifide.apmt.tests

class CreatePageTest : com.cognifide.apmt.tests.page.CreatePageTest(
    TestCases.CREATE_PAGE,
    pageContent = {
        jcrTitle = "Example Page"
        slingResourceType = "apmt/components/testPage"
        cqTemplate = "apmt/templates/testPage"

        "apmtType" set "apmtTestPage"
    }
)
```
And the simplest example, test which opens a page. 
```kotlin
package com.cognifide.apmt.tests

class OpenPageTest : com.cognifide.apmt.tests.page.OpenPageTest(
    TestCases.OPEN_PAGE
)
```

## License
**APM** is licensed under [Apache License, Version 2.0 (the "License")](https://www.apache.org/licenses/LICENSE-2.0.txt)
