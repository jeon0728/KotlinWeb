package com.example.KotlinWeb

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
// @Repository 사용이유
// 1. 스프링에서 지원하지 않는 Exception을 Spring Exception으로 전환해준다.
// 2. 가독성과 의미전달을 위해서 사용한다. 데이터 접근 계층 구현체임을 명시적으로 표현하기 때문
// 3. 스프링의 Bean으로 등록된다.

// CrudRepository 는 스프링 데이터 JPA를 사용하기 때문에 @Repository 를 생략해도 된단다.

// 코틀린에서 ':'는 상속을 나타낸다.
// ArticleRepository, UserRepository 는 CrudRepository<Article, Long> 를 상속받고 있다.
// CrudRepository 는 기본적인 crud만 가능한 repository 인터페이스
// 보통은 CrudRepository + PagingAndSortingRepository된 JpaRepository 를 사용한다.
interface ArticleRepository: CrudRepository<Article, Long> {
    fun findBySlug(slug: String) : Article?
    fun findAllByOrderByAddedAtDesc() : Iterable<Article>
}

@Repository
interface UserRepository: CrudRepository<UserInfo, Long> {
    fun findByLogin(login: String) : UserInfo?
}