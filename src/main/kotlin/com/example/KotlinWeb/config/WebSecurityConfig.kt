package com.example.KotlinWeb.config

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity

@EnableWebSecurity
class WebSecurityConfig(private val jwtProvider: JwtProvider) : WebSecurityConfigurerAdapter() { //스프링 3.0 부터 없어짐 대체 코드 활용해야함

    // 필터를 안 타는 url들 설정
    override fun configure(web: WebSecurity) {
        web.ignoring().antMatchers(
            "/h2-console/**",
            "/sign-up",
            "/sign-in",
            "/don't-pass-filter"
        )
    }

    // http 관련 설정
    override fun configure(http: HttpSecurity) {
        http.httpBasic().disable()
        http.cors().disable()
        http.csrf().disable()
        http.formLogin().disable()
        http.addFilterBefore(JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter::class.java)
    }
}