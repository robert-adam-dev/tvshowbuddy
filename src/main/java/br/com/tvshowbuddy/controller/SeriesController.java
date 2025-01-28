package br.com.tvshowbuddy.controller;

import br.com.tvshowbuddy.dto.SeriesCreateDTO;
import br.com.tvshowbuddy.dto.SeriesResponseDTO;
import br.com.tvshowbuddy.dto.SeriesSummaryDTO;
import br.com.tvshowbuddy.dto.SeriesUpdateDTO;
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
@RequestMapping("/v1/series")
@Slf4j
@RequiredArgsConstructor
public class SeriesController {

    public static final String APPLICATION_JSON = "application/json";
    private final SeriesService seriesService;

    @PostMapping(consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public ResponseEntity<SeriesResponseDTO> createSeries(@RequestBody SeriesCreateDTO seriesCreateDTO) {
        log.info("Received request to create a new series");
        SeriesResponseDTO series = seriesService.createANewSeries(seriesCreateDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(series.getId())
                .toUri();

        return ResponseEntity.created(location).body(series);
    }

    @GetMapping(produces = APPLICATION_JSON)
    public ResponseEntity<List<SeriesSummaryDTO>> getAllSeries() {
        log.info("Received request to retrieve all series");
        return ResponseEntity.ok(seriesService.listAllSeries());
    }

    @GetMapping(path = "/{id}", produces = APPLICATION_JSON)
    public ResponseEntity<SeriesResponseDTO> getSeriesById(@PathVariable String id) {
        log.info("Received request to retrieve series with ID: {}", id);
        return ResponseEntity.ok(seriesService.findById(id));
    }

    @PatchMapping(path = "/{id}", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public ResponseEntity<SeriesResponseDTO> updateSeries(@PathVariable String id,
                                                          @RequestBody SeriesUpdateDTO updatedSeriesDTO) {
        log.info("Received request to update series with ID: {}", id);
        return ResponseEntity.ok().body(seriesService.updateSeries(id, updatedSeriesDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSeries(@PathVariable String id) {
        log.info("Received request to delete series with ID: {}", id);
        seriesService.deleteSeries(id);
    }
}