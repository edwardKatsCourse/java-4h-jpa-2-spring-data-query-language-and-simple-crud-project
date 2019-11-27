package com.telran.java4hjpa2.repository;

import com.telran.java4hjpa2.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    //select * from article where name = ''

    Article findByArticleName(String articleName);
    //ожидаем 1 запись, в реальности SQL вернет хотя бы 2, то мы "словим" exception

    List<Article> findAllByCreatedDateBetween(LocalDate start, LocalDate end);

    List<Article> findAllByArticleNameLike(String name);
    List<Article> findAllByArticleContentContaining(String content);
    List<Article> findAllByArticleNameContainingOrArticleContentContaining(String name, String content);
}
