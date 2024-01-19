package com.example.KotlinWeb

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
// @Entity 어노테이션이란? DB 상의 테이블이라는 개념과 1:1로 매칭되는 객체 단위
class Article ( //TABLE
    var title:String, //COLUMN
    var headline: String,
    var content: String,
    @ManyToOne var author: UserInfo,
    var slug: String = title.toSlug(),
    var addedAt: LocalDateTime = LocalDateTime.now(),
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null


    // @Id 어노테이션이란? DB 상의 pk로 보면 된다.
    // @GeneratedValue 어노테이션이란? DB 상의 auto increment와 같은 개념

    // @ManyToOne 어노테이션이란?
    // N:1 의 관계,
    // N:1의 관계에서 N = 어노테이션이 붙은 변수를 가진 Entity(Article), 1 = 어노테이션이 붙은 변수의 데이터 타입(다른 Entity = UserInfo)
    // UserInfo는 여러개의 Article을 가질 수 있다.

    // Article Entity 생성(create table)시 author_id 컬럼이 자동으로 생성된다.
    // author_id는 UserInfo 의 id와 매칭
    // 그렇기 때문에 Article에 데이터를 저장하기 위해선 UserInfo가 먼저 저장되어야 함.
)

@Entity
class UserInfo( //TABLE
    var login: String, //COLUMN
    var firstName: String,
    var lastName: String,
    var description: String? = null,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null
)