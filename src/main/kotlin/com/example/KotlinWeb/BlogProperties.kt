package com.example.KotlinWeb

import org.springframework.boot.Banner
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

//@ConstructorBinding //spring boot 버전 3.0대는 불필요해짐
@ConfigurationProperties("blog") //application.yml 에서 properties명이 blog 인 이름을 바인딩 시킴
data class BlogProperties(var title: String, val test: Test) {
    // properties의 관리할 경우
    // data class의 생성자로 받으면 됨
    // blog.test 에 접근하기 위해서 val test: Test 를 생성자로 정의하면된다.
    // 현재 접근 가능 프로퍼티 : blog.title, blog.test

    // 내부에 data class Test 를 정의함
    data class Test(val title: String?=null, val content: String)

    // test 하위에 새로운 프로퍼티를 추가하고 싶은경우
    // data class로 Test를 정의 하는데 이때, 생성자는 test 하위에 추가된 프로퍼티로 정의한다.
    // 현재 접근 가능 프로퍼티 : blog.title, blog.test.title, blog.test.content 접근 가능

    //application.yml
    //...
    //blog:
    //  title: Blog
    //  test:
    //    title: Properties Testing
    //    content: jjh fighting
}