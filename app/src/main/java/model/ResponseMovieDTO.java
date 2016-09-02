package model;

import java.util.List;

/**
 * Created by Vlade Ilievski on 9/2/2016.
 */
public class ResponseMovieDTO {
    private List<MovieDTO> movies;

    public List<MovieDTO> getMovies() {
        return movies;
    }

    public void setMovies(List<MovieDTO> movies) {
        this.movies = movies;
    }
}
