package com.example.KotlinWeb.controller

import com.example.KotlinWeb.*
import com.fasterxml.jackson.module.kotlin.SequenceSerializer.serialize
import jakarta.persistence.EntityManager
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime

@Controller // @Controller는 view 페이지를 호출하기 위한 컨트롤러
// HtmlController 는 생성자로 ArticleRepository 타입의 repository를 정의한다.
// 생성자에 접근 제어자(private)를 설정하면 get/set 불가한 속성이 된다.
class HtmlController (private val articleRepository: ArticleRepository, private val userInfoRepository: UserRepository) {

    @GetMapping("/")
    // Model 객체는 request나 session 와 같은 역할
    // 정보를 담아 화면으로 반환한다.
    fun blog(model: Model): String {
        model["title"] = "Blog" //import org.springframework.ui.set 으로 사용가능
        //model.addAttribute("title", "Blog") 위 코드와 같은 성격의 코드
        model["articles"] = articleRepository.findAllByOrderByAddedAtDesc().map{
            it.render() //확장 함수 호출
        }
        return "blog"
    }

    @GetMapping("/article/{slug}")
    fun article(@PathVariable slug: String, model: Model): String {
        val article = articleRepository
            .findBySlug(slug)
            ?.run{ this.render() } // findBySlug(slug) 의 결과가 null 이 아니면 run 메소드 호출
            ?:throw ResponseStatusException(HttpStatus.NOT_FOUND, "This article does not exist") // run 메소드의 결과가 null 이면 throw 실행
        model["title"] = article.title
        model["article"] = article
        return "article"
    }

    fun Article.render() = RenderArticle(
        slug,
        title,
        headline,
        content,
        author,
        addedAt
    )

    data class RenderArticle(
        val slug: String,
        val title: String,
        val headline: String,
        val content: String,
        val author: UserInfo,
        val addedAt: LocalDateTime
    )
}