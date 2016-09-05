package interfaces;

import java.util.List;

import model.MovieDTO;
import model.ResponseGenresDTO;
import model.ResponseMovieDTO;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Vlade Ilievski on 9/2/2016.
 */
public interface MovieAPI {

    String IMAGE_BASE_SIZE = "w500";

    String API_KEY = "112604bed3ac9ecbea0a53e9170ea2c0";
    String THEMOVIIEDB_URL = "http://api.themoviedb.org/3/";
    String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/" + IMAGE_BASE_SIZE + "/";

    @GET("/json/movies.json")
    void getMovie(Callback<List<MovieDTO>> callback);




   /* @GET("/watch")
    void getYoutuneVideo(@Query("v") String v, Callback<Object> callback);*/

    @GET("/movie/{id}/images")
    void getMovieImages(@Query("api_key") String apiKey, @Path("id") int id, Callback<ResponseMovieDTO> callback);

    @GET("/genre/movie/list")
    void getMovieGenres(@Query("api_key") String apiKey, Callback<ResponseGenresDTO> callback);

    @GET("/movie/now_playing")
    void getNowPlaying(@Query("api_key") String apiKey, @Query("page") int page, Callback<ResponseMovieDTO> callback);

    @GET("/movie/popular")
    void getMostPopular(@Query("api_key") String apiKey, @Query("page") int page, Callback<ResponseMovieDTO> callback);

    @GET("/movie/top_rated")
    void getTopRated(@Query("api_key") String apiKey, @Query("page") int page, Callback<ResponseMovieDTO> callback);
}
