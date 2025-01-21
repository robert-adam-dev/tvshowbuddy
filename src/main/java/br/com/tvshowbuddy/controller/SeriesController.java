package br.com.tvshowbuddy.controller;

import br.com.tvshowbuddy.dto.SeriesDTO;
import br.com.tvshowbuddy.dto.SeriesUpdateDTO;
import br.com.tvshowbuddy.mapper.SeriesMapper;
import br.com.tvshowbuddy.model.Series;
import br.com.tvshowbuddy.dto.SeriesSummaryDTO;
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
public class SeriesController {

    private final SeriesService seriesService;
    private final SeriesMapper seriesMapper;

    @PostMapping
    public ResponseEntity<SeriesDTO> createSeries(@RequestBody SeriesDTO seriesDTO) {
        Series series = seriesMapper.toEntity(seriesDTO);
        Series createdSeries = seriesService.createANewSeries(series);
        return ResponseEntity.ok(seriesMapper.toDTO(createdSeries));
    }

    @GetMapping
    public ResponseEntity<List<SeriesSummaryDTO>> getAllSeries() {
        List<Series> seriesList = seriesService.listAllSeries();
        List<SeriesSummaryDTO> seriesSummaryDTOs = seriesList.stream()
                .map(seriesMapper::toSeriesSummaryDTO)
                .toList();

        return ResponseEntity.ok(seriesSummaryDTOs);
    }


    @GetMapping("/{id}")
    public ResponseEntity<SeriesDTO> getSeriesById(@PathVariable String id) {
        Optional<Series> series = seriesService.findById(id);
        return series.map(s -> ResponseEntity.ok(seriesMapper.toDTO(s)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SeriesDTO> updateSeries(
            @PathVariable String id,
            @RequestBody SeriesUpdateDTO updatedSeriesDTO) {
        Optional<Series> updated = seriesService.updateSeries(id, updatedSeriesDTO);
        return updated.map(s -> ResponseEntity.ok(seriesMapper.toDTO(s)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeries(@PathVariable String id) {
        seriesService.deleteSeries(id);
        return ResponseEntity.noContent().build();
    }
}