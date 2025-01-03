package br.com.tvshowbuddy.controller;

import br.com.tvshowbuddy.model.Series;
import br.com.tvshowbuddy.service.SeriesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/series")
@Slf4j
@RequiredArgsConstructor
public class SerieController {

    private final SeriesService seriesService;

    @PostMapping
    public ResponseEntity<Series> createSeries(@RequestBody Series series) {
        Series createdSeries = seriesService.createANewSeries(series);
        return ResponseEntity.ok(createdSeries);
    }

    @GetMapping
    public ResponseEntity<List<Series>> getAllSeries() {
        List<Series> seriesList = seriesService.listAllSeries();
        return ResponseEntity.ok(seriesList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Series> getSeriesById(@PathVariable Long id) {
        Optional<Series> series = seriesService.findById(id);
        return series.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Series> updateSeries(@PathVariable Long id, @RequestBody Series updatedSeries) {
        Optional<Series> updated = seriesService.updateSeries(id, updatedSeries);
        return updated.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeries(@PathVariable Long id) {
        boolean deleted = seriesService.deleteSeries(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}