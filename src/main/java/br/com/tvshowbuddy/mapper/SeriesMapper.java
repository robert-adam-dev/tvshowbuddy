package br.com.tvshowbuddy.mapper;

import br.com.tvshowbuddy.dto.SeasonDTO;
import br.com.tvshowbuddy.dto.SeriesDTO;
import br.com.tvshowbuddy.model.Season;
import br.com.tvshowbuddy.model.Series;
import org.springframework.stereotype.Component;

@Component
public class SeriesMapper {

    public Series toEntity(SeriesDTO dto) {
        if (dto == null) return null;

        return Series.builder()
                .id(dto.getId())
                .name(dto.getName())
                .genre(dto.getGenre())
                .releaseYear(dto.getReleaseYear())
                .seasons(dto.getSeasons() != null ?
                        dto.getSeasons().stream()
                                .map(this::seasonToEntity)
                                .toList() :
                        null)
                .build();
    }

    public SeriesDTO toDTO(Series entity) {
        if (entity == null) return null;

        return SeriesDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .genre(entity.getGenre())
                .releaseYear(entity.getReleaseYear())
                .seasons(entity.getSeasons() != null ?
                        entity.getSeasons().stream()
                                .map(this::seasonToDTO)
                                .toList() :
                        null)
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
}
