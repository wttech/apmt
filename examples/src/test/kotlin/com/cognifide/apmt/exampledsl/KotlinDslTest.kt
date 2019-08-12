package com.cognifide.apmt.exampledsl

import com.cognifide.apmt.KotlinUsers
import com.cognifide.apmt.tests.asset.CreateAssetsTest

class KotlinDslTest : CreateAssetsTest({

    allUsers(KotlinUsers.values())
    paths(
        "/content/dam/we-retail-screens",
        "/content/dam/we-retail"
    )
    users(
        KotlinUsers.AUTHOR,
        KotlinUsers.SUPER_AUTHOR
    )
})
