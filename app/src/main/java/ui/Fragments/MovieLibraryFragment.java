package ui.fragments;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.showbox.showbox.R;

import java.util.ArrayList;
import java.util.List;

import adapter.CategorySpinnerAdapter;
import adapter.GridViewAdapter;
import interfaces.ApiConstants;
import interfaces.MovieAPI;
import model.Category;
import model.Movie.MovieDTO;
import model.Movie.ResponseGenresDTO;
import model.Movie.ResponseMovieDTO;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import ui.MovieDetailsActivity;
import util.AppPreference;
import util.AppUtils;
import util.EndlessScrollListener;

/**
 * Created by Vlade Ilievski on 9/14/2016.
 */
public class MovieLibraryFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = MovieLibraryFragment.class.getName();
    GridView gridView;
    ProgressBar progressBar;
    TextView searchTxt;
    private GridViewAdapter gridViewAdapter;
    private AppPreference preference;
    private List<MovieDTO> movies = new ArrayList<>();
    Spinner spinner;
    MovieAPI api;
    private int PAGE = 1;
    private String searchedString="";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.library_layout, container, false);

        initVariables(v);
        initListeners();


        getMovieGenres();
        getNowPlayingMovies();


       /* User u = preference.getFacebookUser();
        if (u.getUserType() == User.FACEBOOK_USER) {
            Toast.makeText(getContext(), "Welcome " + u.getFirstName() + " " + u.getLastName(), Toast.LENGTH_LONG).show();
        }*/

        return v;
    }

    private void initVariables(View v) {


        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(ApiConstants.THEMOVIIEDB_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        api = restAdapter.create(MovieAPI.class);

        gridView = (GridView) v.findViewById(R.id.gridView);
        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        preference = new AppPreference(getContext());

        spinner = (Spinner) v.findViewById(R.id.spinner);
        searchTxt = (TextView) v.findViewById(R.id.searchTxt);

        CategorySpinnerAdapter spinnerAdapter = new CategorySpinnerAdapter(getContext(), R.layout.item_category, AppUtils.getMovieCategories());
        spinner.setAdapter(spinnerAdapter);

        gridViewAdapter = new GridViewAdapter(getContext(), R.layout.library_layout_item, movies);
        gridView.setAdapter(gridViewAdapter);
    }


    private void initListeners() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(getActivity(), MovieDetailsActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("movie_object", movies.get(position));
                intent.putExtra(MovieDetailsActivity.REQUEST_FROM, MovieDetailsActivity.FROM_LIBRARY);
                startActivity(intent);
              //  MovieDetailsActivity.openActivity(getActivity(),movies.get(position));

            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                PAGE = 1;
                movies.clear();
                gridViewAdapter.notifyDataSetChanged();
                Category c = (Category) adapterView.getAdapter().getItem(i);
                openSelectedSpinnerItem(c);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        gridView.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                if(searchedString!=null && !searchedString.equals("")){
                    searchMovies(searchedString);
                }else{
                    Category c = (Category) spinner.getSelectedItem();
                    openSelectedSpinnerItem(c);
                }
                // or customLoadMoreDataFromApi(totalItemsCount);
                return true; // ONLY if more data is actually being loaded; false otherwise.
            }
        });
    }

    private void openSelectedSpinnerItem(Category c) {
        switch (c.getId()) {
            case 1:
                getNowPlayingMovies();
                break;
            case 2:
                getTopRatedMovies();
                break;
            case 3:
                getMostPopularMovies();
                break;
        }
    }

    private void getMostPopularMovies() {
        progressBar.setVisibility(View.VISIBLE);
        api.getMostPopular(ApiConstants.API_KEY, PAGE, new Callback<ResponseMovieDTO>() {
            @Override
            public void success(ResponseMovieDTO responseMovieDTO, Response response) {
                Log.d(TAG, "success getting movies from server " + responseMovieDTO);
                progressBar.setVisibility(View.GONE);
                initMoviesList(responseMovieDTO);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "success getting movies from server ");
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void getNowPlayingMovies() {
        progressBar.setVisibility(View.VISIBLE);
        api.getNowPlaying(ApiConstants.API_KEY, PAGE, new Callback<ResponseMovieDTO>() {
            @Override
            public void success(ResponseMovieDTO responseMovieDTO, Response response) {
                Log.d(TAG, "success getting movies from server " + responseMovieDTO);
                progressBar.setVisibility(View.GONE);
                initMoviesList(responseMovieDTO);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "success getting movies from server ");
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void getTopRatedMovies() {
        progressBar.setVisibility(View.VISIBLE);
        api.getTopRated(ApiConstants.API_KEY, PAGE, new Callback<ResponseMovieDTO>() {
            @Override
            public void success(ResponseMovieDTO responseMovieDTO, Response response) {
                Log.d(TAG, "success getting movies from server " + responseMovieDTO);
                progressBar.setVisibility(View.GONE);
                initMoviesList(responseMovieDTO);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "success getting movies from server ");
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void getMovieGenres() {
        api.getMovieGenres(ApiConstants.API_KEY, new Callback<ResponseGenresDTO>() {
            @Override
            public void success(ResponseGenresDTO responseMovieDTO, Response response) {
                Log.d(TAG, "success getting movies from server " + responseMovieDTO);
                preference.saveMovieGenres(responseMovieDTO.getGenres());
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "success getting movies from server ");
            }
        });
    }


    private void initMoviesList(ResponseMovieDTO responseMovieDTO) {
        PAGE++;
        //movies = responseMovieDTO.getMovies();
        movies.addAll(responseMovieDTO.getMovies());
        gridViewAdapter.notifyDataSetChanged();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu, menu);

        SearchManager searchManager =
                (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getActivity().getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                PAGE = 1;
                searchMovies(newText);
                return false;
            }
        });
    }

    private void searchMovies(String query) {
        searchedString = query;
        if (query != null && !query.equals("")) {
            spinner.setVisibility(View.GONE);
            searchTxt.setVisibility(View.VISIBLE);
            searchTxt.setText("Search results for: " + query);
            searchMovieFromServer(query);
        } else {
            spinner.setVisibility(View.VISIBLE);
            searchTxt.setVisibility(View.GONE);
            movies.clear();
            if (spinner.getSelectedItem() instanceof Category) {
                Category c = (Category) spinner.getSelectedItem();
                openSelectedSpinnerItem(c);
            } else {
                Toast.makeText(getContext(), "Error casting selected item", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void searchMovieFromServer(String query) {
        Log.d(TAG, "searchMovies=" + query);
        progressBar.setVisibility(View.VISIBLE);
        api.searchMovies(ApiConstants.API_KEY, query, 1, new Callback<ResponseMovieDTO>() {
            @Override
            public void success(ResponseMovieDTO responseMovieDTO, Response response) {
                Log.d(TAG, "success search movies from server " + responseMovieDTO);
                progressBar.setVisibility(View.GONE);
                movies.clear();
                initMoviesList(responseMovieDTO);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "error search movies from server :" + error);
                progressBar.setVisibility(View.GONE);
            }
        });
    }


    @Override
    public void onClick(View view) {

    }
}
