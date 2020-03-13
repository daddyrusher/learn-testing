package com.daddyrusher.learntesting.model

data class Student(
        val id: String? = "",
        val name: String? = "",
        val description: String? = "",
        val courses: MutableList<Course>? = mutableListOf()
)