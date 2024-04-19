package com.example.examplecodekotlin.model

class City(name: String, weather: ArrayList<Weather>, main: Main) {

    var name: String = ""
    var weather: ArrayList<Weather>? = null
    var main: Main? = null

    init {
        this.name = name
        this.weather = weather
        this.main = main
    }
}

class Weather(description: String) {

    var description: String = ""

    init {
        this.description = description
    }
}

class Main(temp: Double) {

    var temp: Double = 0.0

    init {
        this.temp = temp
    }
}