package model.Movie;

import java.util.List;

/**
 * Created by Vlade Ilievski on 9/5/2016.
 */
public class ResponseGenresDTO {

    private List<GenreDTO> genres;

    public List<GenreDTO> getGenres() {
        return genres;
    }

    public void setGenres(List<GenreDTO> genres) {
        this.genres = genres;
    }

    @Override
    public String toString() {
        return "ResponseGenresDTO{" +
                "genres=" + genres +
                '}';
    }
}
