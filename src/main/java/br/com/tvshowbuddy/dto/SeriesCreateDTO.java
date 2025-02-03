package br.com.tvshowbuddy.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Year;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeriesCreateDTO {

    @NotBlank(message = "Series name is required")
    @Size(min = 3, max = 100, message = "Series name must be between 3 and 100 characters")
    private String name;

    @NotEmpty(message = "At least one genre is required")
    @Size(max = 3, message = "Maximum of 3 genres allowed")
    private List<SeriesGenre> genres;

    @Min(value = 1900, message = "Release year must be at least 1900")
    private int releaseYear;

    @NotEmpty(message = "At least one season is required")
    @Size(max = 50, message = "Maximum of 50 seasons allowed")
    @Valid
    private List<SeasonDTO> seasons;

    @NotNull(message = "Completion status is required")
    private Boolean completed;

    @AssertTrue(message = "Release year cannot be in the future")
    public boolean isInvalidReleaseYear() {
        return releaseYear <= Year.now().getValue();
    }
}