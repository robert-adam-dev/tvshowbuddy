package br.com.tvshowbuddy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeriesResponseDTO {
    private String id;
    private String name;
    private List<SeriesGenre> genres;
    private int releaseYear;
    private List<SeasonDTO> seasons;
    private boolean completed;
}