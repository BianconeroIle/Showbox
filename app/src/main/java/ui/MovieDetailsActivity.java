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

import model.Movie;
import util.AppUtils;

/**
 * Created by Vlade Ilievski on 8/16/2016.
 */
public class MovieDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    TextView titleMovie;
    TextView director;
    ImageView imageMovieDetails;
    TextView cast;
    TextView producedBy;
    CheckBox favouriteStatus;
    Boolean checked = true;
    TextView description;
    //int position=0;
    private Movie movie;
    private int clickedPosition;
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

        clickedPosition = getIntent().getExtras().getInt("position");
        movie = AppUtils.movies.get(clickedPosition);


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
                }else{
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

