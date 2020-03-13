package com.daddyrusher.learntesting.model

data class Course(
        var id: String? = "",
        val name: String? = "",
        val description: String? = "",
        val steps: List<String>? = listOf()
)