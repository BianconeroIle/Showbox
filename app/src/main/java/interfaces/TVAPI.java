package interfaces;


import model.TV.ResponseTVDTO;
import model.TV.ResponseTVImagesDTO;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Vlade Ilievski on 9/14/2016.
 */
public interface TVApi {

    @GET("/tv/airing_today")
    void airingToday(@Query("api_key") String apiKey, @Query("page") int page, Callback<ResponseTVDTO> callback);

    @GET("/tv/on_the_air")
    void onAirToday(@Query("api_key") String apiKey, @Query("page") int page, Callback<ResponseTVDTO> callback);

    @GET("/tv/top_rated")
    void topRated(@Query("api_key") String apiKey, @Query("page") int page, Callback<ResponseTVDTO> callback);

    @GET("/tv/popular")
    void popularTVShows(@Query("api_key") String apiKey, @Query("page") int page, Callback<ResponseTVDTO> callback);

    @GET("/tv/{tv_id}/videos")
    void getVideos(@Query("api_key") String apiKey,@Path("tv_id") int tv_id,     Callback<ResponseTVDTO> callback);

    @GET("/tv/{tv_id}/images")
    void getTVImages(@Query("api_key") String apiKey,@Path("tv_id") int tv_id, Callback<ResponseTVImagesDTO> callback);




}
