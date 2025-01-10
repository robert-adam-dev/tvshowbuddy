package br.com.tvshowbuddy.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Setter
@Getter
@Builder
@Document(collection = "series")
public class Series {

    @Id
    private String id;
    private String name;
    private String genre;
    private int releaseYear;
    private List<Season> seasons;
}