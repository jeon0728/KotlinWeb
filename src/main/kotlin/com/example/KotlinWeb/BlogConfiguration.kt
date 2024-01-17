package com.example.KotlinWeb

import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
// @Configuration ������̼��̶�?
// @Bean �� ����ϱ� ���ؼ� �ʿ��� ������̼�
// Bean�� �������ְ�, singleton ����������
// singleton�̶� ? �ڹ� ������ ��ϵ� ��ü�� �Ѱ��� ���������ִ°�
class BlogConfiguration {
    /*@Bean //bean ����� �Ͽ�����, ���� ������ ���� ����
    fun databaseInitializer(userRepository: UserRepository,
                            articleRepository: ArticleRepository) = ApplicationRunner{
            val user = userRepository.save(UserInfo("login", "JUNHO", "JEON", "����ȣ ¯!"))
            articleRepository.save(Article("title1", "headline1", "content1", user))
            articleRepository.save(Article("title2", "headline2", "content2", user))
    }*/

    // ApplicationRunner �� interface�ε� ���ο� run�̶�� �޼ҵ尡 ���ǵǾ��ִ�.
    // ApplicationRunner �� run �޼ҵ�� ������Ʈ�� ����� �� ���� ����ȴ�.
    // �׽�Ʈ �ڵ�, ���� �ڵ� ���� �� ���� springboot �� ����Ǹ� �ȿ� �ڵ尡 �����
    // �׷��� ������ ������Ʈ�� ����� �� UserInfo, Article�� ������ ������ ��
}