package br.com.tvshowbuddy.controller;

import br.com.tvshowbuddy.dto.SeriesDTO;
import br.com.tvshowbuddy.dto.SeriesSummaryDTO;
import br.com.tvshowbuddy.dto.SeriesUpdateDTO;
import br.com.tvshowbuddy.mapper.SeriesMapper;
import br.com.tvshowbuddy.model.Series;
import br.com.tvshowbuddy.service.SeriesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/series")
@Slf4j
@RequiredArgsConstructor
public class SeriesController {

    private final SeriesService seriesService;
    private final SeriesMapper seriesMapper;

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<SeriesDTO> createSeries(@RequestBody SeriesDTO seriesDTO) {

        Series series = seriesMapper.toEntity(seriesDTO);
        Series createdSeries = seriesService.createANewSeries(series);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdSeries.getId())
                .toUri();

        return ResponseEntity.created(location).body(seriesMapper.toDTO(createdSeries));
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<SeriesSummaryDTO>> getAllSeries() {
        List<Series> seriesList = seriesService.listAllSeries();
        List<SeriesSummaryDTO> seriesSummaryDTOs = seriesList.stream()
                .map(seriesMapper::toSeriesSummaryDTO)
                .toList();

        return ResponseEntity.ok(seriesSummaryDTOs);
    }


    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<SeriesDTO> getSeriesById(@PathVariable String id) {
        Series series = seriesService.findById(id);
        return ResponseEntity.ok(seriesMapper.toDTO(series));
    }

    @PatchMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SeriesDTO> updateSeries(@PathVariable String id,
                                                  @RequestBody SeriesUpdateDTO updatedSeriesDTO) {

        Series series = seriesService.updateSeries(id, updatedSeriesDTO);
        return ResponseEntity.ok().body(seriesMapper.toDTO(series));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSeries(@PathVariable String id) {
        seriesService.deleteSeries(id);
    }
}