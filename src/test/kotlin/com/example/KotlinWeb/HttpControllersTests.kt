package com.example.KotlinWeb

import com.example.KotlinWeb.controller.ApiController
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*


@AutoConfigureMockMvc
@SpringBootTest
//@AutoConfigureMockMvc
//@WebMvcTest
class HttpControllersTests(@Autowired val mockMvc: MockMvc) {

    //@MockBean // @MockBean annotaion : Mock 용 빈을 등록하겠다는 뜻
    //private lateinit var articleRepository: ArticleRepository
    // lateinit : 늦은 초기화
    // kotlin은 null 사용을 자제한다.
    // 그렇기 때문에 null로 초기화 하지 않고 lateinit을 사용하여 늦은 초기화하여 사용할 수 있다.
    // lateinit 예약어를 사용한 후 늦은 초기화를 안하면 에러가 난다는데
    // test 코드라 그런지 에러가 나진 않는다..

    //위와 같은 기능의 코드
    private val articleRepository = mockk<ArticleRepository>()

    private val userRepository = mockk<UserRepository>()

    @Test
    fun `List articles`() {
        val user = UserInfo("login", "firstName", "lastName")
        val article1 = Article("title1", "headline1", "content1", user)
        val article2 = Article("title2", "headline2", "content2", user)

        // mokk의 every 메소드를 이용해 리턴값을 지정한다.
        // articleRepository.findAllByOrderByAddedAtDesc() 실행 하면 무조건 article1, article2 반환함
        every { articleRepository.findAllByOrderByAddedAtDesc() } returns
                listOf(article1, article2)

        // mockMvc를 이용 하여 API 호출
        mockMvc.perform(get("/api/articles/")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("\$.[0].author.login").value(user.login))
            .andExpect(jsonPath("\$.[0].slug").value(article1.slug))
            .andExpect(jsonPath("\$.[1].author.login").value(article2.author.login))
            .andExpect(jsonPath("\$.[1].slug").value(article2.slug))
    }

    @Test
    fun `List users`() {
        val user1 = UserInfo("login", "firstName1", "lastName1")
        val user2 = UserInfo("Y", "firstName2", "lastName2")

        // mokk의 every 메소드를 이용해 리턴값을 지정한다.
        // userRepository.findAll() 실행 하면 무조건 user1, user2 반환함
        every { userRepository.findAll() } returns listOf(user1, user2)

        mockMvc.perform(get("/api/users/").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("\$.[0].login").value(user1.login))
            .andExpect(jsonPath("\$.[1].login").value(user2.login))
    }
}