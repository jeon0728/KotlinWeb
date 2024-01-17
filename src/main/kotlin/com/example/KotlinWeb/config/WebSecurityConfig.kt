package com.example.KotlinWeb.config

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity

@EnableWebSecurity
class WebSecurityConfig(private val jwtProvider: JwtProvider) : WebSecurityConfigurerAdapter() { //������ 3.0 ���� ������ ��ü �ڵ� Ȱ���ؾ���

    // ���͸� �� Ÿ�� url�� ����
    override fun configure(web: WebSecurity) {
        web.ignoring().antMatchers(
            "/h2-console/**",
            "/sign-up",
            "/sign-in",
            "/don't-pass-filter"
        )
    }

    // http ���� ����
    override fun configure(http: HttpSecurity) {
        http.httpBasic().disable()
        http.cors().disable()
        http.csrf().disable()
        http.formLogin().disable()
        http.addFilterBefore(JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter::class.java)
    }
}