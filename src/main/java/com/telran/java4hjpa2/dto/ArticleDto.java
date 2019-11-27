package com.telran.java4hjpa2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ArticleDto {

    private Long id;
    private String articleName;
    private String articleContent;
    private LocalDate createdDate;
    //todo add comments list
}
