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
// @DataJpaTest 어노테이션이 붙어있으면 @Entity (table) 이 모두 생성되고
// 테스트가 끝나면 drop 한다.
class RepositoriesTests @Autowired constructor ( // @Autowired annotation 을 통해 생성자 주입
    val entityManager: TestEntityManager, // TestEntityManager 는 데이터를 임시로 저장하는 방법
    val userRepository: UserRepository,
    val articleRepository: ArticleRepository
) {
    @Test
    fun `When findByIdOrNull then return  Article`(){
        val user = UserInfo("Y", "ho", "lee")
        entityManager.persist(user) // UserInfo 엔티티를 영속성 컨텍스트에 저장하는 메소드 (단, 쿼리는 생기지 않는다.)
        val article = Article("2024 open", "2024", "hello 2024 world", user)
        entityManager.persist(article)
        entityManager.flush() // 위에서 persist한 엔티티의 쿼리가 실제로 DB에 반영되는 메소드
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