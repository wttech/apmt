package com.cognifide.apmt.config

data class Instance(
    val name: String,
    val url: String,
    val headers: Map<String, String> = mapOf()
)
