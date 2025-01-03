package br.com.tvshowbuddy.service;

import br.com.tvshowbuddy.model.Series;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SeriesService {

    private final List<Series> seriesDatabase = new ArrayList<>();
    private Long nextId = 1L;

    public Series createANewSeries(Series series) {
        series.setId(nextId++);
        seriesDatabase.add(series);
        return series;
    }

    public List<Series> listAllSeries() {
        return seriesDatabase;
    }

    public Optional<Series> findById(Long id) {
        return seriesDatabase.stream().filter(series -> series.getId().equals(id)).findFirst();
    }

    public Optional<Series> updateSeries(Long id, Series updatedSeries) {
        return findById(id).map(series -> {
            if (updatedSeries.getName() != null && !updatedSeries.getName().isEmpty()) {
                series.setName(updatedSeries.getName());
            }
            if (updatedSeries.getGenre() != null && !updatedSeries.getGenre().isEmpty()) {
                series.setGenre(updatedSeries.getGenre());
            }
            if (updatedSeries.getReleaseYear() > 0) {
                series.setReleaseYear(updatedSeries.getReleaseYear());
            }
            if (updatedSeries.getSeasons() != null && !updatedSeries.getSeasons().isEmpty()) {
                series.setSeasons(updatedSeries.getSeasons());
            }
            return series;
        });
    }


    public boolean deleteSeries(Long id) {
        return seriesDatabase.removeIf(series -> series.getId().equals(id));
    }
}