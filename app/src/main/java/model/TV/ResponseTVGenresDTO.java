package model.TV;

import java.util.List;



/**
 * Created by Vlade Ilievski on 9/20/2016.
 */
public class ResponseTVGenresDTO {

    private List<GenreTVDTO> genres;

    public List<GenreTVDTO> getGenres() {
        return genres;
    }

    public void setGenres(List<GenreTVDTO> genres) {
        this.genres = genres;
    }

    @Override
    public String toString() {
        return "ResponseTVGenresDTO{" +
                "genres=" + genres +
                '}';
    }
}
