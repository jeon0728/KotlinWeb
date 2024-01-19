package com.example.KotlinWeb.security

import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
@EnableWebSecurity //해당 config클래스가 FilterChain이라는 것을 알려주는 어노테이션
class SecurityConfig {
    private val allowList = arrayOf("/", "/api/**", "/article/**")
    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()
    @Bean
    fun filterChain(http: HttpSecurity) =
        http
        .csrf {
                c -> c.disable()
        }
        .headers{
                c -> c.frameOptions { f -> f.disable() }.disable()
        }	// H2 콘솔 사용을 위한 설정
        .authorizeHttpRequests {
            it.requestMatchers(*allowList).permitAll()	// requestMatchers의 인자로 전달된 url은 모두에게 허용
                .requestMatchers(PathRequest.toH2Console()).permitAll()	// H2 콘솔 접속은 모두에게 허용
                .anyRequest().authenticated()	// 그 외의 모든 요청은 인증 필요
        }
        .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }	// 세션을 사용하지 않으므로 STATELESS 설정
        .build()!!
}


// csrf().disabled() >> csrf { c -> c.disable() }
// headers().frameOptions().sameOrigin() >> headers{ c -> c.frameOptions { f -> f.disable() }.disable() }
// 모두 deprecated 되었다.
// 기존에 체이닝하여 설정이 적용되던 방법을 Customizer라는 객체에 직접 disabled() 처리해주는 방식으로 변경되었다.

// requestMatchers는 vararg 타입 가변인자를 받는 함수이다.
// 가변인자란 함수의 인자 개수를 가변적으로 늘리거나 줄여서 받을 수 있는것을 말한다.
// 함수를 호출할 때 가변일자로 넘기려면 인자 앞에 *를 붙여주면 된다.
// requestMatchers(*allowList)