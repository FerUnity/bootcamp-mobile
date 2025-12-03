package com.example.micalendariov4.model.api

import kotlinx.serialization.Serializable

@Serializable
class Holiday(
    val date: String = "",
    val localName: String = "",
    val name: String = "",
    val countryCode: String = "",
    val fixed: Boolean = false,
    val global: Boolean = false,
    val counties: List<String>? = null,
    val launchYear: Int? = null,
    val types: List<String> = emptyList()
)
