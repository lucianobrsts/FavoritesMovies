package unit7.dev.favoritesmovies.api.entity;

import java.util.List;

public class MovieListingDTO {

    private int page;
    private List<MovieListDTO> results;

    public int getPage() {
        return page;
    }

    public List<MovieListDTO> getResults() {
        return results;
    }
}
