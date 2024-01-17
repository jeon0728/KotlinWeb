package com.example.KotlinWeb.security

import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy

@Configuration
@EnableWebSecurity //�ش� configŬ������ FilterChain�̶�� ���� �˷��ִ� ������̼�
class SecurityConfig {
    @Bean
    fun filterChain(http: HttpSecurity) =
        http
        .csrf {
                c -> c.disable()
        }
        .headers{
                c -> c.frameOptions { f -> f.disable() }.disable()
        }	// H2 �ܼ� ����� ���� ����
        .authorizeHttpRequests {
            it.requestMatchers("/", "/api/**", "/article/**").permitAll()	// requestMatchers�� ���ڷ� ���޵� url�� ��ο��� ���
                .requestMatchers(PathRequest.toH2Console()).permitAll()	// H2 �ܼ� ������ ��ο��� ���
                .anyRequest().authenticated()	// �� ���� ��� ��û�� ���� �ʿ�
        }
        .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }	// ������ ������� �����Ƿ� STATELESS ����
        .build()!!


    // csrf().disabled() >> csrf { c -> c.disable() }
    // headers().frameOptions().sameOrigin() >> headers{ c -> c.frameOptions { f -> f.disable() }.disable() }
    // ��� deprecated �Ǿ���.
    // ������ ü�̴��Ͽ� ������ ����Ǵ� ����� Customizer��� ��ü�� ���� disabled() ó�����ִ� ������� ����Ǿ���.
}