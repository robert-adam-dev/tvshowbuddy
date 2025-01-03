package br.com.tvshowbuddy.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class Season {
    private int seasonNumber;
    private int episodes;
}