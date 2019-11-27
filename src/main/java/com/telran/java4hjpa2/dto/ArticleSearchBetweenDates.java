package com.telran.java4hjpa2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ArticleSearchBetweenDates {

    private LocalDate start;
    private LocalDate end;
}
