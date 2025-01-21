package br.com.tvshowbuddy.service;

import br.com.tvshowbuddy.dto.SeasonDTO;
import br.com.tvshowbuddy.dto.SeriesUpdateDTO;
import br.com.tvshowbuddy.model.Season;
import br.com.tvshowbuddy.model.Series;
import br.com.tvshowbuddy.repository.SeriesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SeriesService {

    private final SeriesRepository seriesRepository;

    public Series createANewSeries(Series series) {
        return seriesRepository.save(series);
    }

    public List<Series> listAllSeries() {
        return seriesRepository.findAll();
    }

    public Optional<Series> findById(String id) {
        return seriesRepository.findById(id);
    }

    @Transactional
    public Optional<Series> updateSeries(String id, SeriesUpdateDTO updatedSeries) {

        Optional<Series> optionalSeries = seriesRepository.findById(id);

        if (optionalSeries.isEmpty()) {
            return optionalSeries;
        }

        Series existingSeries = optionalSeries.get();

        existingSeries.setCompleted(updatedSeries.isCompleted());
        updateSeasons(existingSeries, updatedSeries.getSeasons());

        Series savedSeries = seriesRepository.save(existingSeries);
        return Optional.of(savedSeries);
    }

    private void updateSeasons(Series existingSeries, List<SeasonDTO> seasons) {
        if (seasons == null || seasons.isEmpty()) {
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
            } else {
                existingSeries.getSeasons().add(Season.builder()
                        .seasonNumber(updatedSeasonDTO.getSeasonNumber())
                        .episodes(updatedSeasonDTO.getEpisodes())
                        .build());
            }
        }

        existingSeries.getSeasons().sort(Comparator.comparingInt(Season::getSeasonNumber));
    }

    public void deleteSeries(String id) {
        seriesRepository.deleteById(id);
    }
}