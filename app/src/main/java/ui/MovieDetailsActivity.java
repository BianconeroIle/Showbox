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
import model.Movie;
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
    private Movie movie;
    private int position;
    TextView rate;
    TextView directorNames;
    TextView actors;
    TextView producers;
    TextView descritionMovie;
    TextView rating;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details_activity);

        titleMovie = (TextView) findViewById(R.id.titleMovie);
        imageMovieDetails = (ImageView) findViewById(R.id.imageMovieDetails);
        favouriteStatus = (CheckBox) findViewById(R.id.favouriteStatus);
        directorNames = (TextView) findViewById(R.id.directorNames);
        actors = (TextView) findViewById(R.id.actors);
        producers = (TextView) findViewById(R.id.producers);
        descritionMovie = (TextView) findViewById(R.id.descritionMovie);
        rating = (TextView) findViewById(R.id.rating);

        position = getIntent().getExtras().getInt("position");
        String requestFrom = getIntent().getExtras().getString("request_from");
        if (requestFrom != null && requestFrom.equals(FROM_LIBRARY)) {
            movie = AppUtils.movies.get(position);
        } else if (requestFrom != null && requestFrom.equals(FROM_FAVORITES)) {
            List<Movie> favMovies = new ArrayList<>(AppUtils.getFavourites());
            movie = favMovies.get(position);
        }


        titleMovie.setText(movie.getTitle());
        directorNames.setText(movie.getDirector());
        Picasso.with(this).load(movie.getMoviePicture()).into(imageMovieDetails);
        actors.setText(movie.getActors());
        producers.setText(movie.getProducer());
        descritionMovie.setText(movie.getDescription());
        rating.setText(movie.getRate() + "");
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


    @Override
    public void onClick(View view) {

    }


}

