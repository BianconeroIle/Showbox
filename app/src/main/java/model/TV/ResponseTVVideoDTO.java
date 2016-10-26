package model.TV;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Vlade Ilievski on 9/19/2016.
 */
public class ResponseTVVideoDTO {
    private int id;

    @SerializedName("results")
    private List<TVVideoDTO> videos;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<TVVideoDTO> getVideos() {
        return videos;
    }

    public void setVideos(List<TVVideoDTO> videos) {
        this.videos = videos;
    }

}
