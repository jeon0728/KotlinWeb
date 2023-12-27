package com.example.KotlinWeb

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.repository.findByIdOrNull

@DataJpaTest
// @DataJpaTest annotation ??
// test package 아래에 있는 클래스 파일에서 사용가능
// annotation 이름 그대로 JPA를 사용하여 데이터를 테스트 할 수 있다.
// 실제 DB를 사용하지 않고 내장형을 사용하기 때문에 실제 DB에 영향을 주지 않는다.
class RepositoriesTests @Autowired constructor ( // @Autowired annotation 을 통해 생성자 주입
    val entityManager: TestEntityManager, // TestEntityManager 는 데이터를 임시로 저장하는 방법
    val userRepository: UserRepository,
    val articleRepository: ArticleRepository
) {
    @Test
    fun `When findByIdOrNull then return  Article`(){
        val user = UserInfo("login", "firstname", "lastname")
        entityManager.persist(user) // persist 메소드는 저장을 하는것 같으..
        val article = Article("title", "headline", "content", user)
        entityManager.persist(article)
        entityManager.flush() // flush 매소드는 oracle의 commit과 같은 역할을 하는것 같으..
        val found = articleRepository.findByIdOrNull(article.id!!) //article.id!! 는 절대 null 일 수가 없다는 뜻 왜냐? pk 이니까!!
        assertThat(found).isEqualTo(article)
    }

    @Test
    fun `When findByLogin then return User`() {
        val user = UserInfo("login", "firstname", "lastname")
        entityManager.persist(user)
        entityManager.flush()
        val found = userRepository.findByLogin(user.login)
        assertThat(found).isEqualTo(user)
    }
}