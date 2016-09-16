package model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Vlade Ilievski on 9/14/2016.
 */
public class ResponseTVDTO {

    @SerializedName("results")
    List<TVDTO> tvshows;

    private String name;
    private String profile_path;

    public List<TVDTO> getTvshow() {
        return tvshows;
    }

    public void setTvshows(List<TVDTO> tvshow){
        this.tvshows = tvshow;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }

    @Override
    public String toString() {
        return "ResponseTVDTO{" +
                "tvshows=" + tvshows +
                ", name='" + name + '\'' +
                ", profile_path='" + profile_path + '\'' +
                '}';
    }
}

