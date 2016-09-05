package ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.showbox.showbox.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import adapter.FavouriteAdapter;
import adapter.GridViewAdapter;
import interfaces.MovieAPI;
import model.GenreDTO;
import model.Movie;
import model.MovieDTO;
import util.AppPreference;
import util.AppUtils;

/**
 * Created by Vlade Ilievski on 8/16/2016.
 */
public class MovieDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String REQUEST_FROM = "request_from";

    public static final String FROM_LIBRARY = "library";
    public static final String FROM_FAVORITES = "favorites";

    TextView titleMovie;
    ImageView imageMovieDetails;
    CheckBox favouriteStatus;
    private MovieDTO movie;
    private int position;
    TextView rating;
    TextView releaseYear;
    TextView genres;
    TextView overViewDescription;
    AppPreference preference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details_activity);
        preference = new AppPreference(this);
        titleMovie = (TextView) findViewById(R.id.titleMovie);
        imageMovieDetails = (ImageView) findViewById(R.id.imageMovieDetails);
        favouriteStatus = (CheckBox) findViewById(R.id.favouriteStatus);
        rating = (TextView) findViewById(R.id.rating);
        genres = (TextView) findViewById(R.id.genres);
        overViewDescription=(TextView)findViewById(R.id.overViewDescription);

        position = getIntent().getExtras().getInt("position");

        String requestFrom = getIntent().getExtras().getString("request_from");
        if (requestFrom != null && requestFrom.equals(FROM_LIBRARY)) {
            //movie = AppUtils.movies.get(position);
            movie = (MovieDTO) getIntent().getExtras().getSerializable("movie_object");
        } else if (requestFrom != null && requestFrom.equals(FROM_FAVORITES)) {
            List<MovieDTO> favMovies = new ArrayList<>(AppUtils.getFavourites());
            movie = favMovies.get(position);
        }


        titleMovie.setText(movie.getTitle());
        genres.setText(getMovieGenres());
        overViewDescription.setText(movie.getOverview());
        Picasso.with(this).load(MovieAPI.IMAGE_BASE_URL + movie.getPosterPath()).into(imageMovieDetails);

        rating.setText(movie.getPopularity() + "");
        favouriteStatus.setOnClickListener(this);


        favouriteStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    AppUtils.addFavouriteMovie(movie);


                } else {
                    AppUtils.removeFromFavourites(movie);
                }

            }
        });

        favouriteStatus.setChecked(AppUtils.isMovieInFavourites(movie));
    }

    private String getMovieGenres() {
        List<GenreDTO> genres = preference.getMovieGenres();
        String movieGenres = "";
        for (int i = 0; i < movie.getGenreIds().length; i++) {
            int genreId = movie.getGenreIds()[i];
            for (GenreDTO genre : genres) {
                if (genre.getId() == genreId) {
                    movieGenres += genre.getName() + ", ";
                }
            }
        }
        return movieGenres;
    }


    @Override
    public void onClick(View view) {

    }


}

