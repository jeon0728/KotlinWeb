package com.example.KotlinWeb

import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
// @Configuration 어노테이션이란?
// @Bean 을 사용하기 위해서 필요한 어노테이션
// Bean을 관리해주고, singleton 유지시켜줌
// singleton이란 ? 자바 빈으로 등록된 객체를 한개만 유지시켜주는것
class BlogConfiguration {
    /*@Bean //bean 등록은 하였지만, 주입 받지는 않은 상태
    fun databaseInitializer(userRepository: UserRepository,
                            articleRepository: ArticleRepository) = ApplicationRunner{
            val user = userRepository.save(UserInfo("login", "JUNHO", "JEON", "전준호 짱!"))
            articleRepository.save(Article("title1", "headline1", "content1", user))
            articleRepository.save(Article("title2", "headline2", "content2", user))
    }*/

    // ApplicationRunner 는 interface인데 내부에 run이라는 메소드가 정의되어있다.
    // ApplicationRunner 의 run 메소드는 프로젝트가 실행될 때 같이 실행된다.
    // 테스트 코드, 실제 코드 가릴 것 없이 springboot 가 실행되면 안에 코드가 실행됨
    // 그렇기 때문에 프로젝트가 실행될 때 UserInfo, Article에 데이터 저장을 함
}