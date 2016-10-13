package model.Movie;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Vlade Ilievski on 9/6/2016.
 */
public class ResponseMovieImagesDTO {

    private long id;

    @SerializedName("backdrops")
    private List<MoviеImageDTO> images;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<MoviеImageDTO> getImages() {
        return images;
    }

    public void setImages(List<MoviеImageDTO> images) {
        this.images = images;
    }
}
