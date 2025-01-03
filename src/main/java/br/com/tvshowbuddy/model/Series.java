package br.com.tvshowbuddy.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
public class Series {

    private Long id;
    private String name;
    private String genre;
    private int releaseYear;
    private List<Season> seasons;
}