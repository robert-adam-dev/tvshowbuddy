package br.com.tvshowbuddy.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeasonDTO {

    @Positive(message = "Season number must be positive")
    @Max(value = 50, message = "Season number cannot exceed 50")
    private int seasonNumber;

    @Positive(message = "Number of episodes must be positive")
    @Max(value = 100, message = "Number of episodes cannot exceed 100")
    private int episodes;
}