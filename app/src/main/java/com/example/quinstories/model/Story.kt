package com.example.quinstories.model

data class Story(
    val description: String? = "",
    val image: String? = ""
)

data class Stories(
    val storieslist: List<Story>?
)