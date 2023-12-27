package com.example.KotlinWeb

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cglib.core.Local
import java.time.LocalDateTime

@SpringBootApplication
class KotlinWebApplication

fun main(args: Array<String>) {
	runApplication<KotlinWebApplication>(*args)
}
