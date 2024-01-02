package com.example.KotlinWeb.controller

import com.example.KotlinWeb.Article
import com.example.KotlinWeb.ArticleRepository
import com.example.KotlinWeb.UserRepository
import com.example.KotlinWeb.result
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController // @RestController�� json ��ü�� ��ȯ�ϱ� ���� ��Ʈ�ѷ�
@RequestMapping("/api")
class ApiController(val userRepository: UserRepository, val articleRepository: ArticleRepository) {

    var rst = result("1", "success")
    @PostMapping("/save")
    fun save(@RequestBody article: Article, model: Model): result {
        userRepository.save(article.author)
        val articleRst = articleRepository.save(article)
        if (articleRst.id != null) {
            rst.rstCode = "0"
            rst.rstMsg = "���� ��ȣ"
        } else {
            rst.rstCode = "9"
            rst.rstMsg = "���� �ޱ�"
        }
        return rst
    }
}