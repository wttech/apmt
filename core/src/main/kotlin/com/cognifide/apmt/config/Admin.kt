package com.cognifide.apmt.config

import com.fasterxml.jackson.annotation.JsonProperty

data class Admin(
    @field:JsonProperty val username: String,
    @field:JsonProperty val password: String
)