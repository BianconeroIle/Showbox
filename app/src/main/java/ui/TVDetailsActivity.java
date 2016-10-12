package ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.showbox.showbox.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import adapter.GalleryViewPagerAdapter;
import adapter.SimilarRecyclerViewAdapter;
import interfaces.ApiConstants;
import interfaces.TVApi;
import model.Movie.GenreDTO;
import model.Movie.MovieDTO;
import model.TV.ResponseTVDTO;
import model.TV.ResponseTVGenresDTO;
import model.TV.ResponseTVImagesDTO;
import model.TV.TVDTO;
import model.TV.TVImageDTO;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import util.AppPreference;
import util.AppUtils;

/**
 * Created by Vlade Ilievski on 9/22/2016.
 */
public class TVDetailsActivity extends AppCompatActivity implements SimilarRecyclerViewAdapter.OnSimilarItemClickListener<TVDTO>, View.OnClickListener {

    public static final String TAG = TVDetailsActivity.class.getName();
    //VideoView video;
    public TVDTO tvShow = null;
    public TVDTO tvShowDetails = null;
    private List<TVDTO> simularTvShows = null;
    private List<TVDTO> items;
    TextView tvTitle;
    TextView genres;
    TextView episodeRunTime;
    TextView overviewtv;
    TextView rating_score;
    TextView seasonsnumber;
    TextView episodenumber;
    TextView onAirFirstTime;
    TVApi api;
    ProgressBar progressBar;
    GalleryViewPagerAdapter adapter;
    ViewPager viewpager;
    List<String> images = new ArrayList<>();
    ImageView rating;
    Context context = null;
    AppPreference preference;
    private RecyclerView recycler_view;
    ProgressBar progress_bar;
    private SimilarRecyclerViewAdapter similarRecyclerViewAdapter;
    CheckBox favouriteStatus;
    private int position;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tv_details_activity);

        if (getIntent() != null && getIntent().getExtras() != null && getIntent().getExtras().containsKey("tvshow_object")) {
            tvShow = (TVDTO) getIntent().getExtras().getSerializable("tvshow_object");

        }
        preference = new AppPreference(this);

        initVariables();

    }

    private void initVariables() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(ApiConstants.THEMOVIIEDB_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        api = restAdapter.create(TVApi.class);

        //video = (VideoView) v.findViewById(R.id.video);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        genres = (TextView) findViewById(R.id.genres);
        overviewtv = (TextView) findViewById(R.id.overviewtv);
        rating_score = (TextView) findViewById(R.id.rating_score);
        seasonsnumber = (TextView) findViewById(R.id.seasonsnumber);
        episodenumber = (TextView) findViewById(R.id.episodenumber);
        onAirFirstTime = (TextView) findViewById(R.id.onAirFirstTime);
        episodeRunTime = (TextView) findViewById(R.id.episodeRunTime);
        progress_bar = (ProgressBar) findViewById(R.id.progress_bar);
        adapter = new GalleryViewPagerAdapter(this, images);
        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        favouriteStatus = (CheckBox) findViewById(R.id.favouriteStatus);

        position = getIntent().getExtras().getInt("position");

        String requestFrom = getIntent().getExtras().getString("request_from");
        if (requestFrom != null && requestFrom.equals("library")) {
            tvShow = (TVDTO) getIntent().getExtras().getSerializable("tvshow_object");
        } else if (requestFrom != null && requestFrom.equals("favorites")) {
            List<TVDTO> favTVShows = new ArrayList<>(AppUtils.getFavouritestvshow());
            tvShow = favTVShows.get(position);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        RecyclerView myList = (RecyclerView) findViewById(R.id.recycler_view);
        myList.setLayoutManager(layoutManager);

        progress_bar.setVisibility(View.VISIBLE);
        viewpager.setAdapter(adapter);
        getTVGenres();
        getTVDetails(tvShow.getId());
        getTVImages(tvShow.getId());
        getTVSimular(tvShow.getId());
        //    Picasso.with(getContext()).load(ApiConstants.IMAGE_BASE_URL + tvShow.get(position).getPoster_path()).into(tvimages);

//        Uri videoPath = Uri.parse("");
//        video.setVideoURI(videoPath);
//        video.start();

        tvTitle.setText((tvShow.getName()));

        //  episodeRunTime.setText((tvShow.getEpisode_run_time())+"");
        overviewtv.setText((tvShow.getOverview()));
        rating_score.setText((tvShow.getVote_average() + ""));

        //onAirFirstTime.setText(tvShow.getFirstAirDate());
        try {
            onAirFirstTime.setText(formatReleaseDate(tvShow.getFirstAirDate()));
        } catch (ParseException e) {
            e.printStackTrace();
            onAirFirstTime.setText(tvShow.getFirstAirDate());
        }


        favouriteStatus.setOnClickListener(this);

        favouriteStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    AppUtils.addFavouriteTvShow(tvShow);
                } else {
                    AppUtils.removeFromFavouriteTvShow(tvShow);
                }

            }
        });
        favouriteStatus.setChecked(AppUtils.isTVShowInFavourites(tvShow));

    }




    private void initTvDetails(TVDTO details) {
        seasonsnumber.setText(details.getNumber_of_seasons() + "");
        episodenumber.setText(details.getNumber_of_episodes() + "");
        episodeRunTime.setText(getEpisodesRuntime(details.getEpisode_run_time()));

        genres.setText(getGenres(details.getGenre()));

    }
    private String formatReleaseDate(String movieReleaseServerDate) throws ParseException {
        Date releaseServerDate;
        releaseServerDate = new SimpleDateFormat("yyyy-MM-dd").parse(movieReleaseServerDate);
        return new SimpleDateFormat("d MMM yyyy").format(releaseServerDate);
    }

    private String getEpisodesRuntime(int[] array) {
        StringBuilder builder = new StringBuilder();
        for (int time : array
                ) {
            builder.append(time + "min");
        }
        return builder.toString();
    }

    private String getGenres(GenreDTO[] genres) {
        StringBuilder builder = new StringBuilder();
        for (GenreDTO genre : genres) {
            builder.append(genre.getName() + ", ");
        }
        return builder.toString();
    }

    private void initSimilar(List<TVDTO> similar) {
        similarRecyclerViewAdapter = new SimilarRecyclerViewAdapter<TVDTO>(this, R.layout.library_layout_item, similar, this);
        recycler_view.setAdapter(similarRecyclerViewAdapter);
    }

    private void getTVSimular(int tvId) {
        api.getSimilarTVShow(ApiConstants.API_KEY, tvId, new Callback<ResponseTVDTO>() {
            @Override
            public void success(ResponseTVDTO responseTVDTO, Response response) {
                Log.d(TAG, "success getting details from server " + responseTVDTO);
                progress_bar.setVisibility(View.GONE);
                simularTvShows = responseTVDTO.getTvshow();
                initSimilar(responseTVDTO.getTvshow());
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "success getting details from server " + error);
                progress_bar.setVisibility(View.GONE);
            }
        });
    }


    private void getTVDetails(int tvId) {
        api.getTvDetails(ApiConstants.API_KEY, tvId, new Callback<TVDTO>() {
            @Override
            public void success(TVDTO responseTVDTO, Response response) {
                Log.d(TAG, "success getting details from server " + responseTVDTO);
                tvShowDetails = responseTVDTO;
                initTvDetails(responseTVDTO);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "success getting details from server " + error);
            }
        });
    }

    private void getTVImages(int tvId) {
        api.getTVImages(ApiConstants.API_KEY, tvId, new Callback<ResponseTVImagesDTO>() {
            @Override
            public void success(ResponseTVImagesDTO responseTVImagesDTO, Response response) {
                Log.d(TAG, "success getting images from server " + responseTVImagesDTO);
                getImagePaths(responseTVImagesDTO);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "failure getting images from server " + error);
            }
        });
    }

    private void getTVGenres() {
        api.getTvGenres(ApiConstants.API_KEY, new Callback<ResponseTVGenresDTO>() {
            @Override
            public void success(ResponseTVGenresDTO responseTVGenresDTO, Response response) {
                Log.d(TAG, "success getting genres from server " + responseTVGenresDTO);
                preference.saveTVGenres(responseTVGenresDTO.getGenres());
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "success getting genres from server ");
            }
        });
    }

    private void getImagePaths(ResponseTVImagesDTO res) {
        for (TVImageDTO imgs : res.getImages()) {
            images.add(imgs.getFile_path());
        }
        adapter.notifyDataSetChanged();
    }

    public static void openActivity(Context context, TVDTO tvShow) {
        Intent intent = new Intent(context, TVDetailsActivity.class);
        intent.putExtra("tvshow_object", tvShow);
        context.startActivity(intent);
    }

    @Override
    public void onSimilarClick(TVDTO object) {
        TVDetailsActivity.openActivity(this, object);
    }

    @Override
    public void onClick(View view) {

    }
}


