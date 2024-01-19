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

    //@MockBean // @MockBean annotaion : Mock �� ���� ����ϰڴٴ� ��
    //private lateinit var articleRepository: ArticleRepository
    // lateinit : ���� �ʱ�ȭ
    // kotlin�� null ����� �����Ѵ�.
    // �׷��� ������ null�� �ʱ�ȭ ���� �ʰ� lateinit�� ����Ͽ� ���� �ʱ�ȭ�Ͽ� ����� �� �ִ�.
    // lateinit ���� ����� �� ���� �ʱ�ȭ�� ���ϸ� ������ ���ٴµ�
    // test �ڵ�� �׷��� ������ ���� �ʴ´�..

    //���� ���� ����� �ڵ�
    private val articleRepository = mockk<ArticleRepository>()

    private val userRepository = mockk<UserRepository>()

    @Test
    fun `List articles`() {
        val user = UserInfo("login", "firstName", "lastName")
        val article1 = Article("title1", "headline1", "content1", user)
        val article2 = Article("title2", "headline2", "content2", user)

        // mokk�� every �޼ҵ带 �̿��� ���ϰ��� �����Ѵ�.
        // articleRepository.findAllByOrderByAddedAtDesc() ���� �ϸ� ������ article1, article2 ��ȯ��
        every { articleRepository.findAllByOrderByAddedAtDesc() } returns
                listOf(article1, article2)

        // mockMvc�� �̿� �Ͽ� API ȣ��
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

        // mokk�� every �޼ҵ带 �̿��� ���ϰ��� �����Ѵ�.
        // userRepository.findAll() ���� �ϸ� ������ user1, user2 ��ȯ��
        every { userRepository.findAll() } returns listOf(user1, user2)

        mockMvc.perform(get("/api/users/").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("\$.[0].login").value(user1.login))
            .andExpect(jsonPath("\$.[1].login").value(user2.login))
    }
}