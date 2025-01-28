package br.com.tvshowbuddy.service;

import br.com.tvshowbuddy.dto.*;
import br.com.tvshowbuddy.exception.SeriesNotFoundException;
import br.com.tvshowbuddy.mapper.SeriesMapper;
import br.com.tvshowbuddy.model.Season;
import br.com.tvshowbuddy.model.Series;
import br.com.tvshowbuddy.repository.SeriesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SeriesService {

    private final SeriesRepository seriesRepository;
    private final SeriesMapper seriesMapper;

    public SeriesResponseDTO createANewSeries(SeriesCreateDTO seriesCreateDTO) {
        Series series = seriesMapper.toEntity(seriesCreateDTO);
        Series createdSeries = seriesRepository.save(series);
        log.info("Series created successfully with ID: {}", createdSeries.getId());
        return seriesMapper.toDTO(createdSeries);
    }

    public List<SeriesSummaryDTO> listAllSeries() {
        List<Series> seriesList = seriesRepository.findAll();
        log.info("Successfully retrieved {} series", seriesList.size());
        return seriesList.stream()
                .map(seriesMapper::toSeriesSummaryDTO)
                .toList();
    }

    public SeriesResponseDTO findById(String id) {
        Series series = findSeriesOrThrowException(id);
        log.info("Series with ID: {} retrieved successfully", id);
        return seriesMapper.toDTO(series);
    }

    @Transactional
    public SeriesResponseDTO updateSeries(String id, SeriesUpdateDTO updatedSeries) {
        Series existingSeries = findSeriesOrThrowException(id);
        log.debug("Updating series with ID: {}. New status: {}, Seasons: {}",
                id, updatedSeries.isCompleted(), updatedSeries.getSeasons());
        existingSeries.setCompleted(updatedSeries.isCompleted());
        updateSeasons(existingSeries, updatedSeries.getSeasons());
        Series updatedEntity = seriesRepository.save(existingSeries);
        log.info("Series with ID: {} updated successfully", id);
        return seriesMapper.toDTO(updatedEntity);
    }

    private void updateSeasons(Series existingSeries, List<SeasonDTO> seasons) {
        if (seasons == null || seasons.isEmpty()) {
            log.debug("No seasons to update for series ID: {}", existingSeries.getId());
            return;
        }

        if (existingSeries.getSeasons() == null) {
            existingSeries.setSeasons(new ArrayList<>());
        }

        for (SeasonDTO updatedSeasonDTO : seasons) {
            Optional<Season> optionalSeason = existingSeries.getSeasons()
                    .stream()
                    .filter(s -> s.getSeasonNumber() == updatedSeasonDTO.getSeasonNumber())
                    .findFirst();

            if (optionalSeason.isPresent()) {
                Season seasonToUpdate = optionalSeason.get();
                seasonToUpdate.setEpisodes(updatedSeasonDTO.getEpisodes());
                log.debug("Updated season {} for series ID: {}", seasonToUpdate.getSeasonNumber(), existingSeries.getId());
            } else {
                Season newSeason = Season.builder()
                        .seasonNumber(updatedSeasonDTO.getSeasonNumber())
                        .episodes(updatedSeasonDTO.getEpisodes())
                        .build();
                existingSeries.getSeasons().add(newSeason);
                log.debug("Added new season {} for series ID: {}", newSeason.getSeasonNumber(), existingSeries.getId());
            }
        }

        existingSeries.getSeasons().sort(Comparator.comparingInt(Season::getSeasonNumber));
        log.info("Seasons updated and sorted for series ID: {}", existingSeries.getId());
    }

    public void deleteSeries(String id) {
        findSeriesOrThrowException(id);
        seriesRepository.deleteById(id);
        log.info("Series with ID: {} deleted successfully", id);
    }

    private Series findSeriesOrThrowException(String id) {
        return seriesRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Series with ID: {} not found", id);
                    return new SeriesNotFoundException(id);
                });
    }
}
