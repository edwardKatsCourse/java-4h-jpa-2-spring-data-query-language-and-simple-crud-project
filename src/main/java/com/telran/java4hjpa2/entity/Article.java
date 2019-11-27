package com.telran.java4hjpa2.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "ARTICLE")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Article {

    //new Article();
    //.save(article);
    //-> hibernate - спроси у базы данных, какой id там присвоили
    //mysql -> hibernate - держи свой id
    //hibernate -> article.setId(id from mysql)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    //update article set content = 'asdasd', created_date = '2019..';
    //insert into article (content, created_date) values ('asda', '2019..');

    //                                    может быть null - нет
    @Column(name = "NAME", unique = true, nullable = false /*, updatable = false, insertable = false*/)
    private String articleName; //null

    @Column(name = "CONTENT")
    private String articleContent;

    @Column(name = "CREATED_DATE")
    private LocalDate createdDate;
}
