package br.com.tvshowbuddy.service;

import br.com.tvshowbuddy.model.Season;
import br.com.tvshowbuddy.model.Series;
import br.com.tvshowbuddy.repository.SeriesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public Optional<Series> updateSeries(String id, Series updatedSeries) {
        return seriesRepository.findById(id)
                .map(existingSeries -> {

                    if (updatedSeries.getName() != null && !updatedSeries.getName().trim().isEmpty()) {
                        existingSeries.setName(updatedSeries.getName());
                    }

                    if (updatedSeries.getGenre() != null && !updatedSeries.getGenre().trim().isEmpty()) {
                        existingSeries.setGenre(updatedSeries.getGenre());
                    }

                    if (updatedSeries.getReleaseYear() > 0) {
                        existingSeries.setReleaseYear(updatedSeries.getReleaseYear());
                    }

                    if (updatedSeries.getSeasons() != null && !updatedSeries.getSeasons().isEmpty()) {
                        if (existingSeries.getSeasons() == null) {
                            existingSeries.setSeasons(new ArrayList<>());
                        }

                        for (Season updatedSeason : updatedSeries.getSeasons()) {
                            Optional<Season> existingSeason = existingSeries.getSeasons()
                                    .stream()
                                    .filter(s -> s.getSeasonNumber() == updatedSeason.getSeasonNumber())
                                    .findFirst();

                            if (existingSeason.isPresent()) {
                                Season seasonToUpdate = existingSeason.get();

                                if (updatedSeason.getEpisodes() > 0) {
                                    seasonToUpdate.setEpisodes(updatedSeason.getEpisodes());
                                }
                            } else {

                                if (updatedSeason.getSeasonNumber() > 0 && updatedSeason.getEpisodes() > 0) {
                                    existingSeries.getSeasons().add(updatedSeason);
                                }
                            }
                        }

                        existingSeries.getSeasons().sort(Comparator.comparingInt(Season::getSeasonNumber));
                    }

                        return seriesRepository.save(existingSeries);
                });
    }

    public void deleteSeries(String id) {
        seriesRepository.deleteById(id);
    }
}