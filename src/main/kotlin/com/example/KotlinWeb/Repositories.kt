package com.example.KotlinWeb

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ArticleRepository: CrudRepository<Article, Long> {
    fun findBySlug(slug: String) : Article?
    fun findAllByOrderByAddedAtDesc() : Iterable<Article>
}

@Repository
interface UserRepository: CrudRepository<UserInfo, Long> {
    fun findByLogin(login: String) : UserInfo?
}