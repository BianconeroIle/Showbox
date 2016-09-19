package ui.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.showbox.showbox.R;

import java.util.ArrayList;
import java.util.List;

import adapter.GalleryViewPagerAdapter;
import interfaces.ApiConstants;
import interfaces.TVApi;

import model.TV.ResponseTVImagesDTO;
import model.TV.TVDTO;
import model.TV.TVImageDTO;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Vlade Ilievski on 9/16/2016.
 */
public class TVDetailsFragment extends Fragment {
    public static final String TAG = TVDetailsFragment.class.getName();
    //VideoView video;
    public TVDTO tvShow = null;
    TextView tvTitle;
    TextView genres;
    //  TextView episodeRunTime;
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


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getArguments();
        if (b.containsKey("tv_dto")) {
            tvShow = (TVDTO) b.getSerializable("tv_dto");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tv_details_activity, container, false);
        initVariables(v);

        return v;
    }

    private void initVariables(View v) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(ApiConstants.THEMOVIIEDB_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        api = restAdapter.create(TVApi.class);

        //video = (VideoView) v.findViewById(R.id.video);
        viewpager = (ViewPager) v.findViewById(R.id.viewpager);
        tvTitle = (TextView) v.findViewById(R.id.tvTitle);
        genres = (TextView) v.findViewById(R.id.genres);
        //   episodeRunTime = (TextView) v.findViewById(R.id.episodeRunTime);
        overviewtv = (TextView) v.findViewById(R.id.overviewtv);

        rating_score = (TextView) v.findViewById(R.id.rating_score);
        seasonsnumber = (TextView) v.findViewById(R.id.seasonsnumber);
        episodenumber = (TextView) v.findViewById(R.id.episodenumber);
        onAirFirstTime = (TextView) v.findViewById(R.id.onAirFirstTime);


        adapter = new GalleryViewPagerAdapter(getContext(), images);

        viewpager.setAdapter(adapter);

        getTVImages(tvShow);
        //    Picasso.with(getContext()).load(ApiConstants.IMAGE_BASE_URL + tvShow.get(position).getPoster_path()).into(tvimages);

//        Uri videoPath = Uri.parse("");
//        video.setVideoURI(videoPath);
//        video.start();

        tvTitle.setText((tvShow.getName()));
        genres.setText((tvShow.getGenre() + ""));
        //  episodeRunTime.setText((tvShow.getEpisode_run_time())+"");
        overviewtv.setText((tvShow.getOverview()));
        rating_score.setText((tvShow.getVote_average() + ""));
        seasonsnumber.setText(tvShow.getNumber_of_seasons() + "");
        episodenumber.setText(tvShow.getNumber_of_episodes() + "");
        onAirFirstTime.setText(tvShow.getFirstAirDate());

    }


    private void getTVImages(TVDTO tvshows) {
        api.getTVImages(ApiConstants.API_KEY, tvshows.getId(), new Callback<ResponseTVImagesDTO>() {
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

    private void getImagePaths(ResponseTVImagesDTO res) {
        for (TVImageDTO imgs : res.getImages()) {
            images.add(imgs.getFile_path());
        }
        adapter.notifyDataSetChanged();
    }


}

