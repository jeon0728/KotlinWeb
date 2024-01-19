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
// BlogProperties 라는 data class 를 생성자로 정의함으로써 application.yml에 있는 properties 에 접근이 가능해짐
// 생성자에 접근 제어자(private)를 설정하면 get/set 불가한 속성이 된다.
class HtmlController (private val articleRepository: ArticleRepository,
                      private val userInfoRepository: UserRepository,
                      private val properties: BlogProperties) {

    @GetMapping("/")
    // Model 객체는 request나 session 와 같은 역할
    // 정보를 담아 화면으로 반환한다.
    fun blog(model: Model): String {
        model["title"] = "Blog" //import org.springframework.ui.set 으로 사용가능
        //model.addAttribute("title", "Blog") 위 코드와 같은 성격의 코드


        model["banner"] = properties.test
        model["articles"] = articleRepository.findAllByOrderByAddedAtDesc().map{ (it)
            it.aaaa() //확장 함수 호출
        }
        return "blog"
    }

    // map 함수를 호출하는 본체의 반환값이 무엇이냐에 따라 자동으로 인자가 결정되는것 같다.
    // articleRepository.findAllByOrderByAddedAtDesc() 의 반환값은 Article 이기 때문에 단일 인자 it로 자동 결정됨
    // 만약 map 을 호출하는 본체의 타입 or 함수의 반환값이 key, value 타입의 map 형태였다면 인자도 key, value 형태로 구성할 수 있다.
    // val peopleToAge = mapOf("Alice" to 20, "Bob" to 21)
    // println(peopleToAge.map { (name, age) -> "$name is $age years old" }) // [Alice is 20 years old, Bob is 21 years old]
    // println(peopleToAge.map { it.value }) // [20, 21]

    // run 함수는 특별할것 없이 블록 내부에 있는 값을 반환하는 역할을 한다.
    // run 함수는 블록안에 있는 가장 마지막줄에 대한 값을 반환하기 때문에 render()의 값을 반환하기 위해서는 블록안에 여러 값이 들어가면 안됨
    // run {
    //  this.render()
    //  "aaa"
    // }
    // 이렇게 블록안에 마지막줄이 "aaa" 라면 "aaa"를 반환한다..
    // 굳이 run을 쓰지 않고 articleRepository.findBySlug(slug)?.render() << 이렇게 써도 될 것같은데..

    @GetMapping("/article/{slug}")
    fun article(@PathVariable slug: String, model: Model): String {
        val article = articleRepository //변수 article에 담길 데이터 타입은 RenderArticle or null 둘 중 하나
            .findBySlug(slug)
            ?.run{ this.render() } // findBySlug(slug) 의 결과가 null 이 아니면 run 메소드 호출 > run 블록안에 있는 render 함수 호출 > this는 Article Entity 객체
            ?:throw ResponseStatusException(HttpStatus.NOT_FOUND, "This article does not exist") // run 메소드의 결과가 null 이면 throw 실행
        model["title"] = article.title
        model["article"] = article
        return "article"
    }

    // 확장함수 정의
    // Article class에 aaaa라는 확장 함수를 정의하는 과정
    // 확장함수는 외부 라이브러리에서 정의된 클래스를 확장할 수도 있지만
    // 내가 정의한 클래스도 확장 가능하다.
    // this는 확장함수를 확장한 클래스의 타입
    fun Article.aaaa() = RenderArticle(
        this.slug, //Article.slug
        this.title, //Article.title
        this.headline,
        this.content,
        this.author,
        this.addedAt
    )

    // 위 코드를 풀어서 쓴 형태
    // render() 함수가 호출되면 Article 타입의 객체가 RenderArticle라는 data class에 담겨 반환됨
    // RenderArticle 형태의 data class를 반환함
    fun Article.render(): RenderArticle {
        return RenderArticle(this.slug,
            this.title,
            this.headline,
            this.content,
            this.author,
            this.addedAt)
    }

    //RenderArticle 이라는 data class 정의
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