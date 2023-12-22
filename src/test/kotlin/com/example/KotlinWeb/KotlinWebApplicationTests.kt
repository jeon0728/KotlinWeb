package com.example.KotlinWeb

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.BDDAssertions.then
import org.assertj.core.api.BDDAssumptions.given
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.http.HttpStatus

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) //내장된 톰캣의 포트를 랜덤으로 설정하여 띄우겠다
class KotlinWebApplicationTests(@Autowired val restTemplate: TestRestTemplate) { //@Autowired 어노테이션을 통해 TestRestTemplate 타입의 생성자를 주입받는다.

	@BeforeAll
	fun setup(){
		println(">> Setup")
	}

	@Test
	// 함수명을 ``로 감싸면 local final 함수가 된다.
	fun `Assert blog page title, content and status code`() {
		val entity = restTemplate.getForEntity<String>("/")
		assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
		assertThat(entity.body).contains("<h1>Blog</h1>")
	}

	@Test
	fun `Assert article page title, content and status code`() {
		println(">> Assert article page title, content and status code")
		val entity = restTemplate.getForEntity<String>("/article")
		assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
		assertThat(entity.body).contains("<h1>Article</h1>")
	}

	@AfterAll
	fun teardown(){
		println(">> Tear down")
	}

}
