package com.example.KotlinWeb

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
// @ManyToOne annotation ??
// DB 상의 테이블과 1:1로 매칭되는 객체 단위
class Article (
    var title:String,
    var headline: String,
    var content: String,
    @ManyToOne var author: UserInfo,
    // @ManyToOne annotation ??
    // N:1 의 관계 Article은 자신을 소유한 User가 있다.
    // 이 User는 여러개의 Article을 가질수 있따.
    var slug: String = title.toSlug(),
    var addedAt: LocalDateTime = LocalDateTime.now(),
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null
    // @Id annotation ??
    // DB 상의 pk로 보면 된다.
    // @GeneratedValue
    // DB 상의 auto increment와 같은 개념
)

@Entity
class UserInfo(
    var login: String,
    var firstName: String,
    var lastName: String,
    var description: String? = null,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null
)