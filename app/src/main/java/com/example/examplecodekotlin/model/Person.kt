package com.example.examplecodekotlin.model

class Person(name: String, country: String, state: String, experience: Int) {

    var name: String = ""
    var country: String = ""
    var state: String = ""
    var experience: Int = 0

    init {
        this.name = name
        this.country = country
        this.state = state
        this.experience = experience
    }
}