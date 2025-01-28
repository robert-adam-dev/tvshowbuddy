package br.com.tvshowbuddy.mapper;

import br.com.tvshowbuddy.dto.SeasonDTO;
import br.com.tvshowbuddy.dto.SeriesCreateDTO;
import br.com.tvshowbuddy.dto.SeriesResponseDTO;
import br.com.tvshowbuddy.dto.SeriesSummaryDTO;
import br.com.tvshowbuddy.model.Season;
import br.com.tvshowbuddy.model.Series;
import org.springframework.stereotype.Component;

@Component
public class SeriesMapper {

    public Series toEntity(SeriesCreateDTO dto) {
        if (dto == null) return null;

        return Series.builder()
                .name(dto.getName())
                .genre(dto.getGenre())
                .releaseYear(dto.getReleaseYear())
                .seasons(dto.getSeasons() != null ?
                        dto.getSeasons().stream()
                                .map(this::seasonToEntity)
                                .toList() :
                        null)
                .completed(dto.isCompleted())
                .build();
    }

    public SeriesResponseDTO toDTO(Series entity) {
        if (entity == null) return null;

        return SeriesResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .genre(entity.getGenre())
                .releaseYear(entity.getReleaseYear())
                .seasons(entity.getSeasons() != null ?
                        entity.getSeasons().stream()
                                .map(this::seasonToDTO)
                                .toList() :
                        null)
                .completed(entity.isCompleted())
                .build();
    }

    private Season seasonToEntity(SeasonDTO dto) {
        if (dto == null) return null;

        return Season.builder()
                .seasonNumber(dto.getSeasonNumber())
                .episodes(dto.getEpisodes())
                .build();
    }

    private SeasonDTO seasonToDTO(Season entity) {
        if (entity == null) return null;

        return SeasonDTO.builder()
                .seasonNumber(entity.getSeasonNumber())
                .episodes(entity.getEpisodes())
                .build();
    }

    public SeriesSummaryDTO toSeriesSummaryDTO(Series entity) {
        if (entity == null) return null;

        return SeriesSummaryDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .numberOfSeasons(entity.getSeasons().size())
                .completed(entity.isCompleted())
                .build();
    }
}
