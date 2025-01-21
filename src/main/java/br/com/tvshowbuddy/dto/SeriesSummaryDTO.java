package br.com.tvshowbuddy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeriesSummaryDTO {
    private String id;
    private String name;
    private int numberOfSeasons;
    private boolean completed;
}
