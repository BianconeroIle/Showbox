package ui;

import android.app.ActionBar;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import interfaces.MovieAPI;
import model.Category;
import model.MovieDTO;
import model.ResponseGenresDTO;
import model.ResponseMovieDTO;
import model.User;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import util.AppPreference;
import util.AppUtils;

/**
 * Created by Ilija Angeleski on 8/15/2016.
 */
public class LibraryActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = LibraryActivity.class.getName();
    GridView gridView;
    ProgressBar progressBar;
    TextView searchTxt;
    private GridViewAdapter gridViewAdapter;
    private AppPreference preference;
    private List<MovieDTO> movies;
    Spinner spinner;
    MovieAPI api;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.library_activity);
        //getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        initVariables();


        initListeners();


        getMovieGenres();
        getNowPlayingMovies();

        User u = preference.getFacebookUser();
        if (u.getUserType() == User.FACEBOOK_USER) {
            Toast.makeText(this, "Welcome " + u.getFirstName() + " " + u.getLastName(), Toast.LENGTH_LONG).show();
        }

    }

    private void initVariables() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(MovieAPI.THEMOVIIEDB_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        api = restAdapter.create(MovieAPI.class);

        gridView = (GridView) findViewById(R.id.gridView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        preference = new AppPreference(this);

        spinner = (Spinner) findViewById(R.id.spinner);
        searchTxt = (TextView) findViewById(R.id.searchTxt);

        CategorySpinnerAdapter spinnerAdapter = new CategorySpinnerAdapter(this, R.layout.item_category, AppUtils.getCategories());
        spinner.setAdapter(spinnerAdapter);
    }

    private void initListeners() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(LibraryActivity.this, MovieDetailsActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("movie_object", movies.get(position));
                intent.putExtra(MovieDetailsActivity.REQUEST_FROM, MovieDetailsActivity.FROM_LIBRARY);
                startActivity(intent);
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Category c = (Category) adapterView.getAdapter().getItem(i);
                openSelectedSpinnerItem(c);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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
        api.getMostPopular(MovieAPI.API_KEY, 1, new Callback<ResponseMovieDTO>() {
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
        api.getNowPlaying(MovieAPI.API_KEY, 1, new Callback<ResponseMovieDTO>() {
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
        api.getTopRated(MovieAPI.API_KEY, 1, new Callback<ResponseMovieDTO>() {
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
        api.getMovieGenres(MovieAPI.API_KEY, new Callback<ResponseGenresDTO>() {
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
        movies = responseMovieDTO.getMovies();
        gridViewAdapter = new GridViewAdapter(this, R.layout.library_movie_activity, movies);
        gridView.setAdapter(gridViewAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchMovies(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


    private void searchMovies(String query) {
        if (query != null && !query.equals("")) {
            spinner.setVisibility(View.GONE);
            searchTxt.setVisibility(View.VISIBLE);
            searchTxt.setText("Search results for: " + query);
            searchMovieFromServer(query);
        } else {
            spinner.setVisibility(View.VISIBLE);
            searchTxt.setVisibility(View.GONE);
            if (spinner.getSelectedItem() instanceof Category) {
                Category c = (Category) spinner.getSelectedItem();
                openSelectedSpinnerItem(c);
            } else {
                Toast.makeText(this, "Error casting selected item", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void searchMovieFromServer(String query) {
        Log.d(TAG, "searchMovies=" + query);
        progressBar.setVisibility(View.VISIBLE);
        api.searchMovies(MovieAPI.API_KEY, query, 1, new Callback<ResponseMovieDTO>() {
            @Override
            public void success(ResponseMovieDTO responseMovieDTO, Response response) {
                Log.d(TAG, "success search movies from server " + responseMovieDTO);
                progressBar.setVisibility(View.GONE);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.favourites:
                startActivity(new Intent(this, FavoriteActivity.class));
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

    }
}
