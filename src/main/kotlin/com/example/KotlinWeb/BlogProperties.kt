package com.example.KotlinWeb

import org.springframework.boot.Banner
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

//@ConstructorBinding //spring boot 버전 3.0대는 불필요해짐
@ConfigurationProperties("blog") //application.yml 에서 properties명이 blog 인 이름을 바인딩 시킴
data class BlogProperties(var title: String, val test: Test) {
    // properties의 깊이를 추가하여 관리할 경우
    // 최초의 프로퍼티 data 클래스의 생성자로 받으면 됨
    // blog.jjh 에 접근하기 위해서 val test: Test 를 생성자로 정의하고
    // 내부에 data class Test 를 정의함
    data class Test(val title: String?=null, val content: String)
}