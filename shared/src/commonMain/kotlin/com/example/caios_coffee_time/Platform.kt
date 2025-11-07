package com.example.caios_coffee_time

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform