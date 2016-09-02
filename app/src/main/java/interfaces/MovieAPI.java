package interfaces;

import java.util.List;

import model.MovieDTO;
import model.ResponseMovieDTO;
import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by Vlade Ilievski on 9/2/2016.
 */
public interface MovieAPI {


    @GET("/json/movies.json")
    void getMovie(Callback<List<MovieDTO>> callback);

    @GET("/json/movies.json")
    ResponseMovieDTO getMovie();


   /* @GET("/watch")
    void getYoutuneVideo(@Query("v") String v, Callback<Object> callback);*/
}
