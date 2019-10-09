package com.cognifide.apmt.config

import com.fasterxml.jackson.annotation.JsonProperty

data class Configuration(
    @field:JsonProperty val admin: Admin,
    @field:JsonProperty val instances: Instances
)