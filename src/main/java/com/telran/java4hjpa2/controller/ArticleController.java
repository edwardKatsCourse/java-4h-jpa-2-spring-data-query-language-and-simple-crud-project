package com.telran.java4hjpa2.controller;

import com.telran.java4hjpa2.dto.ArticleDto;
import com.telran.java4hjpa2.dto.ArticleSearchBetweenDates;
import com.telran.java4hjpa2.dto.ArticleUpdateDto;
import com.telran.java4hjpa2.entity.Article;
import com.telran.java4hjpa2.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    @PostMapping("/between-dates")
    public List<ArticleDto> getArticlesBetween(@RequestBody ArticleSearchBetweenDates articleSearchBetweenDates) {
        List<Article> articles = articleRepository.findAllByCreatedDateBetween(
                articleSearchBetweenDates.getStart(),
                articleSearchBetweenDates.getEnd()
        );

        return articles.stream().map(x -> convertToDto(x)).collect(Collectors.toList());
    }

    @PostMapping("/by-name-or-content")
    public List<ArticleDto> getArticlesByNameOrByContent(@RequestBody ArticleUpdateDto articleUpdateDto) {

        //name == null & content == null
        if (articleUpdateDto.getArticleName() == null && articleUpdateDto.getArticleContent() == null) {
            List<Article> all = articleRepository.findAll();
            return all.stream().map(this::convertToDto).collect(Collectors.toList());
        }

        //name != null && content == null
        if (articleUpdateDto.getArticleContent() == null) {
            List<Article> allByArticleNameContaining = articleRepository.findAllByArticleNameLike(articleUpdateDto.getArticleName());
            return allByArticleNameContaining
                    .stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
        }

        //name == null && content != null
        if (articleUpdateDto.getArticleName() == null) {
            return articleRepository.findAllByArticleContentContaining(articleUpdateDto.getArticleContent())
                    .stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
        }

        //name != null && content != null
        return articleRepository.findAllByArticleNameContainingOrArticleContentContaining(articleUpdateDto.getArticleName(), articleUpdateDto.getArticleContent())
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/save")
    public ArticleDto save(@RequestBody ArticleDto articleDto) {
        Article article = articleRepository.findByArticleName(articleDto.getArticleName());
        if (article != null) {
            throw new RuntimeException("Article with such name '" + articleDto.getArticleName() + "' already exists!");
        }

        article = Article.builder()
                .articleContent(articleDto.getArticleContent())
                .articleName(articleDto.getArticleName())
                .createdDate(LocalDate.now())
                .build();
        articleRepository.save(article); //id будет доступен уже тут

        return convertToDto(article);
    }

    @PutMapping("/update/{id}")
    public ArticleDto update(@PathVariable("id") Long articleId,
                             @RequestBody ArticleUpdateDto articleUpdateDto) {

        Article article = articleRepository.findById(articleId).orElseThrow(() -> new RuntimeException("Article with such ID not found"));
        if (articleUpdateDto.getArticleContent() != null) {
            article.setArticleContent(articleUpdateDto.getArticleContent());
        }

        if (articleUpdateDto.getArticleName() != null) {
            article.setArticleName(articleUpdateDto.getArticleName());
        }
        //save = save and update
        //id == null - это новая запись, которая еще не была в базе данных - вызвать 'сохранение'
        //id != null - запись уже существует в базе данных и нужно вызвать "обновление"
        articleRepository.save(article);

        return convertToDto(article);
    }

    @GetMapping("/{id}")
    public ArticleDto getById(@PathVariable("id") Long id) {
        return articleRepository.findById(id)
                .map(this::convertToDto)
                .orElse(null);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteArticle(@PathVariable("id") Long id) {
        articleRepository.deleteById(id);
    }

    private ArticleDto convertToDto(Article article) {
        return ArticleDto.builder()
                .createdDate(article.getCreatedDate())
                .articleName(article.getArticleName())
                .articleContent(article.getArticleContent())
                .id(article.getId())
                .build();
    }

}
