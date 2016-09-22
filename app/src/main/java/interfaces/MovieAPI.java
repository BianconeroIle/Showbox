package interfaces;

import model.Movie.ResponseGenresDTO;
import model.Movie.ResponseMovieDTO;
import model.Movie.ResponseMovieImagesDTO;
import model.TV.ResponseTVDTO;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Vlade Ilievski on 9/2/2016.
 */
public interface MovieAPI {

    @GET("/movie/{id}/images")
    void getMovieImages(@Query("api_key") String apiKey, @Path("id") int id, Callback<ResponseMovieImagesDTO> callback);

    @GET("/genre/movie/list")
    void getMovieGenres(@Query("api_key") String apiKey, Callback<ResponseGenresDTO> callback);

    @GET("/movie/now_playing")
    void getNowPlaying(@Query("api_key") String apiKey, @Query("page") int page, Callback<ResponseMovieDTO> callback);

    @GET("/movie/popular")
    void getMostPopular(@Query("api_key") String apiKey, @Query("page") int page, Callback<ResponseMovieDTO> callback);

    @GET("/movie/top_rated")
    void getTopRated(@Query("api_key") String apiKey, @Query("page") int page, Callback<ResponseMovieDTO> callback);


    @GET("/search/movie")
    void searchMovies(@Query("api_key") String apiKey, @Query("query") String query, @Query("page") int page, Callback<ResponseMovieDTO> callback);

    @GET("/movie/{movie_id}/similar")
    void getSimilarMovies(@Query("api_key") String apiKey, @Path("movie_id") int movie_id, Callback<ResponseMovieDTO> callback);
}
