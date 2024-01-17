package com.example.KotlinWeb

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.http.HttpStatus

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationTests (@Autowired val restTemplate: TestRestTemplate) {
    @BeforeAll
    fun setup(){
        println(">> Setup")
    }

    @Test
    fun `Assert blog page title, content and status code`() {
        println(" >> Assert blog page test, content and status code")
        val entity = restTemplate.getForEntity<String>("/") //api 요청에 대한 응답값을 ResponseEntity로 반환받는다.
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(entity.body).contains("<h1>Blog</h1>")
    }

    @Test
    fun `Assert article page title, content and status code`() {
        println(">> Assert article page title, content and status code")
        val title = "title1"
        val entity = restTemplate.getForEntity<String>("/article/${title.toSlug()}")
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(entity.body).contains(title, "headline", "content")
    }

    @AfterAll
    fun teardown(){
        println(">> Tear down")
    }
}