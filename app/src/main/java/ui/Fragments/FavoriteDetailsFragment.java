package ui.fragments;

import android.content.Intent;
import android.support.v4.app.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.showbox.showbox.R;

import java.util.ArrayList;
import java.util.List;

import adapter.FavouriteListViewAdapter;
import model.Movie.MovieDTO;
import model.TV.TVDTO;
import ui.MovieDetailsActivity;
import ui.TVDetailsActivity;
import util.AppUtils;

import static util.AppUtils.movies;
import static util.AppUtils.tvshows;

/**
 * Created by Vlade Ilievski on 9/14/2016.
 */
public class FavoriteDetailsFragment extends Fragment {

    public static final int MOVIE_FAV = 1;
    public static final int TV_FAV = 2;
    ListView favouriteListView;
    TextView infoText;
    private FavouriteListViewAdapter adapter;
    private int selectedPage;
    private int favoritesType;

    public static final String TAG = FavoriteDetailsFragment.class.getName();

    public static FavoriteDetailsFragment newInstance(int favoritesType) {
        FavoriteDetailsFragment fragmentFirst = new FavoriteDetailsFragment();
        Bundle args = new Bundle();
        args.putInt("favoritesType", favoritesType);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        favoritesType = getArguments().getInt("favoritesType", MOVIE_FAV);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.favourite_details_fragment, container, false);
        initVariables(v);

        return v;
    }

    private void initVariables(View v) {
        infoText = (TextView) v.findViewById(R.id.infoText);
        favouriteListView = (ListView) v.findViewById(R.id.favouriteListView);

        if (favoritesType == MOVIE_FAV) {
            adapter = new FavouriteListViewAdapter(getActivity(), R.layout.favourite_listview_item, new ArrayList<>(AppUtils.getFavourites()));
            favouriteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    MovieDetailsActivity.openActivity(getActivity(),movies.get(position));
                }
            });
        } else if (favoritesType == TV_FAV) {
            adapter = new FavouriteListViewAdapter(getActivity(), R.layout.favourite_listview_item, new ArrayList<>(AppUtils.getFavouritestvshow()));
            favouriteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TVDetailsActivity.openActivity(getActivity(),tvshows.get(position));
                }
            });

        }

        favouriteListView.setAdapter(adapter);

        isLibraryEmpty();
    }



    private void isLibraryEmpty() {
        if (favoritesType == MOVIE_FAV) {
            if (AppUtils.getFavourites() == null || AppUtils.getFavourites().isEmpty()) {
                emptyLibrary();
            }
        } else if (favoritesType == TV_FAV) {
            if (AppUtils.getFavouritestvshow() == null || AppUtils.getFavouritestvshow().isEmpty()) {
                emptyLibrary();
            }
        }
    }

    private void emptyLibrary() {
        infoText.setText("You library is empty");
        favouriteListView.setVisibility(View.GONE);
        infoText.setVisibility(View.VISIBLE);
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}

