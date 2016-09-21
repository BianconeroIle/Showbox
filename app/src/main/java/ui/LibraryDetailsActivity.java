package ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.showbox.showbox.R;

import java.util.ArrayList;
import java.util.List;

import adapter.GalleryViewPagerAdapter;
import interfaces.ApiConstants;
import interfaces.MovieAPI;
import model.Movie.GenreDTO;
import model.Movie.MovieDTO;
import model.Movie.MovieImageDTO;
import model.Movie.ResponseMovieImagesDTO;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import util.AppPreference;
import util.AppUtils;

/**
 * Created by Vlade Ilievski on 8/16/2016.
 */
public class LibraryDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String REQUEST_FROM = "request_from";

    public static final String FROM_LIBRARY = "library";
    public static final String FROM_FAVORITES = "favorites";
    public static final String TAG = LibraryDetailsActivity.class.getName();
    TextView titleMovie;
    //ImageView imageMovieDetails;
    CheckBox favouriteStatus;
    private MovieDTO movie;
    private int position;
    TextView rating;
    TextView genres;
    TextView overViewDescription;
    AppPreference preference;
    MovieAPI api;
    List<String> images = new ArrayList<>();
    GalleryViewPagerAdapter adapter;
    ViewPager viewPager;
    TextView releaseDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details_activity);
        preference = new AppPreference(this);
        titleMovie = (TextView) findViewById(R.id.titleMovie);
        //imageMovieDetails = (ImageView) findViewById(R.id.imageMovieDetails);
        favouriteStatus = (CheckBox) findViewById(R.id.favouriteStatus);
        rating = (TextView) findViewById(R.id.rating);
        genres = (TextView) findViewById(R.id.genres);
        overViewDescription = (TextView) findViewById(R.id.overViewDescription);
        releaseDate = (TextView) findViewById(R.id.releaseDate);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(ApiConstants.THEMOVIIEDB_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        api = restAdapter.create(MovieAPI.class);


        position = getIntent().getExtras().getInt("position");

        String requestFrom = getIntent().getExtras().getString("request_from");
        if (requestFrom != null && requestFrom.equals(FROM_LIBRARY)) {
            //movie = AppUtils.movies.get(position);
            movie = (MovieDTO) getIntent().getExtras().getSerializable("movie_object");
        } else if (requestFrom != null && requestFrom.equals(FROM_FAVORITES)) {
            List<MovieDTO> favMovies = new ArrayList<>(AppUtils.getFavourites());
            movie = favMovies.get(position);
        }


        //images.add(movie.getPosterPath()); //SET ORIGINAL IMAGE AS FIRST
        adapter = new GalleryViewPagerAdapter(this, images);

        viewPager.setAdapter(adapter);

        getMovieImages(movie);

        titleMovie.setText(movie.getTitle());
        genres.setText(getMovieGenres());
        overViewDescription.setText(movie.getOverview());
        releaseDate.setText(movie.getReleaseDate());
        //Picasso.with(this).load(MovieAPI.IMAGE_BASE_URL + movie.getPosterPath()).into(imageMovieDetails);

        rating.setText(movie.getVoteAverage() + "");
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

    private void getMovieImages(MovieDTO movie) {
        api.getMovieImages(ApiConstants.API_KEY, movie.getId(), new Callback<ResponseMovieImagesDTO>() {
            @Override
            public void success(ResponseMovieImagesDTO responseMovieImagesDTO, Response response) {
                Log.d(TAG, "success getting images from server " + responseMovieImagesDTO);
                getImagePaths(responseMovieImagesDTO);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "success getting images from server " + error);
            }
        });
    }

    private void getImagePaths(ResponseMovieImagesDTO res) {
        for (MovieImageDTO img : res.getImages()) {
            images.add(img.getFile_path());
        }
        adapter.notifyDataSetChanged();
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

