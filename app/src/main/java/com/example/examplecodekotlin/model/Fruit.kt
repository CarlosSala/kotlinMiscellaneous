package com.example.examplecodekotlin.model

class Fruit(name: String, price: Double, rating: Float, image: Int) {

    var name: String = ""
    var price: Double = 0.0
    var rating: Float = 0.0F
    var image: Int = 0

    init {
        this.name = name
        this.price = price
        this.rating = rating
        this.image = image
    }
}