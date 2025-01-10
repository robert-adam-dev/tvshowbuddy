package br.com.tvshowbuddy.repository;

import br.com.tvshowbuddy.model.Series;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SeriesRepository extends MongoRepository<Series, String> {
}
