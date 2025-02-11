package br.com.tvshowbuddy.dto;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeriesUpdateDTO {

    @Valid
    private List<SeasonDTO> seasons;
    private boolean completed;
}