package model.Movie;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Vlade Ilievski on 10/13/2016.
 */

public class ResponseMovieVideoDTO {

    private long id;

    @SerializedName("results")
    private List<MovieVideoDTO> video;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<MovieVideoDTO> getVideo() {
        return video;
    }

    public void setVideo(List<MovieVideoDTO> video) {
        this.video = video;
    }
}
