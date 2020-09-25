package com.cognifide.apmt.config

import com.fasterxml.jackson.annotation.JsonProperty

data class Configuration(
    @field:JsonProperty("apmt-user") val apmtUser: Principal,
    @field:JsonProperty val instances: Instances
)