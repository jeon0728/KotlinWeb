package com.example.KotlinWeb

import org.springframework.data.repository.CrudRepository

interface ArticleRepository: CrudRepository<Article, Long> {
    fun findBySlug(slug: String) : Article?
    fun findAllByOrderByAddedAtDesc() : Iterable<Article>
}

interface UserRepository: CrudRepository<UserInfo, Long> {
    fun findByLogin(login: String) : UserInfo?
}