package com.cognifide.apmt.config

import com.fasterxml.jackson.annotation.JsonProperty

data class Instances(
    @field:JsonProperty val author: Instance,
    @field:JsonProperty val publish: Instance
)
