package ui;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.showbox.showbox.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import adapter.GalleryViewPagerAdapter;
import adapter.SimilarRecyclerViewAdapter;
import interfaces.ApiConstants;
import interfaces.MovieAPI;
import model.Movie.GenreDTO;
import model.Movie.MovieDTO;
import model.Movie.MoviеImageDTO;
import model.Movie.ResponseMovieDTO;
import model.Movie.ResponseMovieImagesDTO;
import model.Movie.ResponseMovieVideoDTO;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import util.AppPreference;
import util.AppUtils;

import static android.view.View.GONE;

/**
 * Created by Vlade Ilievski on 8/16/2016.
 */
public class MovieDetailsActivity extends AppCompatActivity implements View.OnClickListener, SimilarRecyclerViewAdapter.OnSimilarItemClickListener<MovieDTO> {
    public static final String REQUEST_FROM = "request_from";

    public static final String FROM_LIBRARY = "library";
    public static final String FROM_FAVORITES = "favorites";
    public static final String TAG = MovieDetailsActivity.class.getName();
    TextView titleMovie;
    //ImageView imageMovieDetails;
    CheckBox favouriteStatus;
    private MovieDTO movie = null;
    private int position;
    private List<MovieDTO> similarMovies = null;
    TextView rating;
    TextView genres;
    TextView overViewDescription;
    AppPreference preference;
    MovieAPI api;
    List<String> images = new ArrayList<>();
    GalleryViewPagerAdapter adapter;
    ViewPager viewPager;
    TextView releaseDate;
    ProgressBar movieprogress_bar;
    RecyclerView movierecycler_view;
    SimilarRecyclerViewAdapter recyclerViewAdapterMovie;
    private MediaController mediaControls;
    ImageView playImageYT;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details_activity);

        initVariables();
    }

    private void initVariables() {
        preference = new AppPreference(this);
        titleMovie = (TextView) findViewById(R.id.titleMovie);
        //imageMovieDetails = (ImageView) findViewById(R.id.imageMovieDetails);
        favouriteStatus = (CheckBox) findViewById(R.id.favouriteStatus);
        rating = (TextView) findViewById(R.id.rating);
        genres = (TextView) findViewById(R.id.genres);
        overViewDescription = (TextView) findViewById(R.id.overViewDescription);
        releaseDate = (TextView) findViewById(R.id.releaseDate);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        playImageYT=(ImageView)findViewById(R.id.playImageYT);
        movieprogress_bar = (ProgressBar) findViewById(R.id.movieprogress_bar);
        movierecycler_view = (RecyclerView) findViewById(R.id.movierecycler_view);
        movieprogress_bar.setVisibility(View.VISIBLE);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        if (mediaControls == null) {
            mediaControls = new MediaController(MovieDetailsActivity.this);
        }

        RecyclerView myList = (RecyclerView) findViewById(R.id.movierecycler_view);
        myList.setLayoutManager(layoutManager);



        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(ApiConstants.THEMOVIIEDB_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        api = restAdapter.create(MovieAPI.class);


        position = getIntent().getExtras().getInt("position");

        String requestFrom = getIntent().getExtras().getString("request_from");
        if (requestFrom != null && requestFrom.equals(FROM_FAVORITES)) {
            List<MovieDTO> favMovies = new ArrayList<>(AppUtils.getFavourites());
            movie = favMovies.get(position);
        } else {
            movie = (MovieDTO) getIntent().getExtras().getSerializable("movie_object");
        }


        //images.add(movie.getPosterPath()); //SET ORIGINAL IMAGE AS FIRST
        adapter = new

                GalleryViewPagerAdapter(this, images);

        viewPager.setAdapter(adapter);

//        getVideo(movie.getId());
        getMovieImages(movie.getId()

        );

        getMovieSimilar(movie.getId()

        );
        titleMovie.setText(movie.getTitle());
        genres.setText(

                getMovieGenres()


        );
        overViewDescription.setText(movie.getOverview());

        try {
            releaseDate.setText(formatReleaseDate(movie.getReleaseDate()));
        } catch (ParseException e) {
            e.printStackTrace();
            releaseDate.setText(movie.getReleaseDate());
        }

        //releaseDate.setText(movie.getReleaseDate());
        //Picasso.with(this).load(MovieAPI.IMAGE_BASE_URL + movie.getPosterPath()).into(imageMovieDetails);

        rating.setText(movie.getVoteAverage() + "");
        favouriteStatus.setOnClickListener(this);

        favouriteStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                       @Override
                                                       public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                                           if (b) {
                                                               Toast.makeText(MovieDetailsActivity.this, "Added to favourite movies library", Toast.LENGTH_LONG).show();
                                                               AppUtils.addFavouriteMovie(movie);
                                                           } else {
                                                               Toast.makeText(MovieDetailsActivity.this, "Removed from favourite movies library", Toast.LENGTH_LONG).show();
                                                               AppUtils.removeFromFavourites(movie);
                                                           }
                                                       }
                                                   }

        );
        favouriteStatus.setChecked(AppUtils.isMovieInFavourites(movie));
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }

    private String formatReleaseDate(String movieReleaseServerDate) throws ParseException {
        Date releaseServerDate;
        releaseServerDate = new SimpleDateFormat("yyyy-MM-dd").parse(movieReleaseServerDate);
        return new SimpleDateFormat("d MMM yyyy").format(releaseServerDate);
    }

    private void initSimilar(List<MovieDTO> similar) {
        recyclerViewAdapterMovie = new SimilarRecyclerViewAdapter<MovieDTO>(this, R.layout.library_layout_item, similar, this);
        movierecycler_view.setAdapter(recyclerViewAdapterMovie);
    }

    private void getVideo(int movie_id) {
        api.getMovieVideo(ApiConstants.API_KEY, movie_id, new Callback<ResponseMovieVideoDTO>() {
            @Override
            public void success(ResponseMovieVideoDTO responseMovieVideoDTO, Response response) {
                Log.d(TAG, "success getting movie video from server " + responseMovieVideoDTO);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "failure getting movie video from server ");

            }
        });
    }

    private void getMovieSimilar(int movie_id) {
        api.getSimilarMovies(ApiConstants.API_KEY, movie_id, new Callback<ResponseMovieDTO>() {
            @Override
            public void success(ResponseMovieDTO responseMovieDTO, Response response) {
                Log.d(TAG, "success getting details from server " + responseMovieDTO);
                movieprogress_bar.setVisibility(GONE);
                similarMovies = responseMovieDTO.getMovies();
                initSimilar(responseMovieDTO.getMovies());
                if(similarMovies.isEmpty()){
                    Toast.makeText(MovieDetailsActivity.this, "No similar movies", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "success getting details from server " + error);
                movieprogress_bar.setVisibility(GONE);
            }
        });
    }

    private void getMovieImages(int id) {
        api.getMovieImages(ApiConstants.API_KEY, id, new Callback<ResponseMovieImagesDTO>() {
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
        for (MoviеImageDTO img : res.getImages()) {
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

    public static void openActivity(Context context, MovieDTO movie) {
        Intent intent = new Intent(context, MovieDetailsActivity.class);
        intent.putExtra("movie_object", movie);
        context.startActivity(intent);
    }

    @Override
    public void onSimilarClick(MovieDTO object) {
        MovieDetailsActivity.openActivity(this, object);
        finish();
    }
}

