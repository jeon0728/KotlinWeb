package com.example.KotlinWeb.security

import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
@EnableWebSecurity //�ش� configŬ������ FilterChain�̶�� ���� �˷��ִ� ������̼�
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
        }	// H2 �ܼ� ����� ���� ����
        .authorizeHttpRequests {
            it.requestMatchers(*allowList).permitAll()	// requestMatchers�� ���ڷ� ���޵� url�� ��ο��� ���
                .requestMatchers(PathRequest.toH2Console()).permitAll()	// H2 �ܼ� ������ ��ο��� ���
                .anyRequest().authenticated()	// �� ���� ��� ��û�� ���� �ʿ�
        }
        .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }	// ������ ������� �����Ƿ� STATELESS ����
        .build()!!
}


// csrf().disabled() >> csrf { c -> c.disable() }
// headers().frameOptions().sameOrigin() >> headers{ c -> c.frameOptions { f -> f.disable() }.disable() }
// ��� deprecated �Ǿ���.
// ������ ü�̴��Ͽ� ������ ����Ǵ� ����� Customizer��� ��ü�� ���� disabled() ó�����ִ� ������� ����Ǿ���.

// requestMatchers�� vararg Ÿ�� �������ڸ� �޴� �Լ��̴�.
// �������ڶ� �Լ��� ���� ������ ���������� �ø��ų� �ٿ��� ���� �� �ִ°��� ���Ѵ�.
// �Լ��� ȣ���� �� �������ڷ� �ѱ���� ���� �տ� *�� �ٿ��ָ� �ȴ�.
// requestMatchers(*allowList)