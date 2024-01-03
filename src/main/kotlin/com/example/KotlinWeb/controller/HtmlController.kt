package com.example.KotlinWeb.controller

import com.example.KotlinWeb.*
import com.fasterxml.jackson.module.kotlin.SequenceSerializer.serialize
import jakarta.persistence.EntityManager
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.*
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

@RestController
@RequestMapping("/api/articles")
class ArticleController (private val articleRepository: ArticleRepository) {

    @GetMapping("/")
    fun findAll() = articleRepository.findAllByOrderByAddedAtDesc()

    @GetMapping("/{slug}")
    // @PathVariable는 URL에 변수가 들어온걸 처리하는 어노테이션
    // EX) http://localhost:8080/api/user/1234 에서 "1234" 가 변수
    fun findOne(@PathVariable slug : String) =
        articleRepository.findBySlug(slug)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "this article does not exist")
}

@RestController
@RequestMapping("/api/users")
class UserController(private val userInfoRepository: UserRepository){
    @GetMapping("/")
    fun findAll() = userInfoRepository.findAll();

    @GetMapping("/{login}")
    fun findOne(@PathVariable login: String) =
        userInfoRepository.findByLogin(login)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "this user does not exist")
}