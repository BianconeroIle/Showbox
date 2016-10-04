package ui.fragments;

import android.content.Intent;
import android.support.v4.app.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.showbox.showbox.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import adapter.FavouriteListViewAdapter;
import adapter.FavouriteViewPagerAdapter;
import model.Movie.MovieDTO;
import ui.MovieDetailsActivity;
import util.AppUtils;

/**
 * Created by Vlade Ilievski on 9/14/2016.
 */
public class FavoriteFragment extends Fragment {

    ListView favouriteListView;
    TextView infoText;
    private FavouriteListViewAdapter adapter;
    int mCurrentPage;
    ViewPager pager;
    FavouriteViewPagerAdapter pagerAdapter;
//    AppPreference preference;

    public static final String TAG=FavoriteFragment.class.getName();


//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setHasOptionsMenu(true);
//
//        Bundle data = getArguments();
//        mCurrentPage = data.getInt("current_page", 0);
//
//
//
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.favourite_fragment,container,false);

   //     initListener();
        initVariables(v);


        Bundle data=getArguments();


//        List<MovieDTO> movie = new ArrayList<>(AppUtils.getFavourites());
//        if (movie == null || movie.isEmpty()) {
//            infoText.setText("You library is empty");
//            favouriteListView.setVisibility(View.GONE);
//            infoText.setVisibility(View.VISIBLE);
//        }


        return v;
    }
    private void initVariables(View v){

        pager=(ViewPager)v.findViewById(R.id.pager);
        infoText = (TextView) v.findViewById(R.id.infoText);
        favouriteListView = (ListView) v.findViewById(R.id.favouriteListView);


    }
//    private void initListener(){
//        favouriteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                Intent intent = new Intent(getActivity(), MovieDetailsActivity.class);
//                intent.putExtra("position", position);
//                intent.putExtra(MovieDetailsActivity.REQUEST_FROM, MovieDetailsActivity.FROM_FAVORITES);
//                startActivity(intent);
//            }
//        });
//    }

    @Override
    public void onResume() {
        super.onResume();
        adapter = new FavouriteListViewAdapter(getActivity(), R.layout.favourite_listview_item, new ArrayList<>(AppUtils.getFavourites()));
        favouriteListView.setAdapter(adapter);
        List<MovieDTO> movie = new ArrayList<>(AppUtils.getFavourites());
        if (movie == null || movie.isEmpty()) {
            infoText.setText("You library is empty");
            favouriteListView.setVisibility(View.GONE);
            infoText.setVisibility(View.VISIBLE);
        }
    }
    }

