package com.example.KotlinWeb

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
// @Repository �������
// 1. ���������� �������� �ʴ� Exception�� Spring Exception���� ��ȯ���ش�.
// 2. �������� �ǹ������� ���ؼ� ����Ѵ�. ������ ���� ���� ����ü���� ��������� ǥ���ϱ� ����
// 3. �������� Bean���� ��ϵȴ�.

// CrudRepository �� ������ ������ JPA�� ����ϱ� ������ @Repository �� �����ص� �ȴܴ�.

// ��Ʋ������ ':'�� ����� ��Ÿ����.
// ArticleRepository, UserRepository �� CrudRepository<Article, Long> �� ��ӹް� �ִ�.
// CrudRepository �� �⺻���� crud�� ������ repository �������̽�
// ������ CrudRepository + PagingAndSortingRepository�� JpaRepository �� ����Ѵ�.
interface ArticleRepository: CrudRepository<Article, Long> {
    fun findBySlug(slug: String) : Article?
    fun findAllByOrderByAddedAtDesc() : Iterable<Article>
}

@Repository
interface UserRepository: CrudRepository<UserInfo, Long> {
    fun findByLogin(login: String) : UserInfo?
}