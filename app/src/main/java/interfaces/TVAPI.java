package interfaces;


import model.ResponseTVDTO;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Vlade Ilievski on 9/14/2016.
 */
public interface TVApi {

//    @GET("/tv/{id}")
//    void getTVShows(@Query("api_key") String apiKey,@Query("language") int id ,Callback<ResponseTVDTO> callback);

    @GET("/tv/on_the_air")
    void onAirToday(@Query("api_key") String apiKey, @Query("page") int page, Callback<ResponseTVDTO> callback);

    @GET("/tv/top_rated")
    void topRated(@Query("api_key") String apiKey, @Query("page") int page, Callback<ResponseTVDTO> callback);

    @GET("/tv/popular")
    void popularTVShows(@Query("api_key") String apiKey, @Query("page") int page, Callback<ResponseTVDTO> callback);

    @GET("/tv/latest")
    void getLatestTVShows(@Query("api_key") String apiKey,Callback<ResponseTVDTO> callback);


}
