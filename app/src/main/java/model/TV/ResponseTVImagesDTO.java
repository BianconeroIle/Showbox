package model.TV;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Vlade Ilievski on 9/19/2016.
 */
public class ResponseTVImagesDTO {

    private long id;

    @SerializedName("backdrops")
    private List<TVImageDTO> images;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<TVImageDTO> getImages() {
        return images;
    }

    public void setImages(List<TVImageDTO> images) {
        this.images = images;
    }
}
