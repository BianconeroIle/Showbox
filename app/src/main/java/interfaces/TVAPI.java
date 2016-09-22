package interfaces;


import model.Movie.ResponseMovieDTO;
import model.TV.ResponseTVDTO;
import model.TV.ResponseTVGenresDTO;
import model.TV.ResponseTVImagesDTO;
import model.TV.TVDTO;
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
    void getVideos(@Query("api_key") String apiKey, @Path("tv_id") int tv_id, Callback<ResponseTVDTO> callback);

    @GET("/tv/{tv_id}/images")
    void getTVImages(@Query("api_key") String apiKey, @Path("tv_id") int tv_id, Callback<ResponseTVImagesDTO> callback);


    @GET("/tv/{tv_id}")
    void getTvDetails(@Query("api_key") String apiKey, @Path("tv_id") int tv_id, Callback<TVDTO> callback);

    @GET("/genre/tv/list")
    void getTvGenres(@Query("api_key") String apiKey, Callback<ResponseTVGenresDTO> callback);

    @GET("/tv/{tv_id}/similar")
    void getSimilarTVShow(@Query("api_key") String apiKey,@Path("tv_id") int tv_id , Callback<ResponseTVDTO> callback);

    @GET("/search/tv")
    void searchTVShow(@Query("api_key") String apiKey, @Query("query") String query, @Query("page") int page, Callback<ResponseTVDTO> callback);

}
