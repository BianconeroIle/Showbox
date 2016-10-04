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
import adapter.GridViewTVLibraryAdapter;
import interfaces.ApiConstants;
import interfaces.TVApi;
import model.Category;
import model.TV.ResponseTVDTO;
import model.TV.TVDTO;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import ui.TVDetailsActivity;
import util.AppPreference;
import util.AppUtils;
import util.EndlessScrollListener;

/**
 * Created by Vlade Ilievski on 9/15/2016.
 */
public class TVLibraryFragment extends Fragment {

    public static final String TAG = TVLibraryFragment.class.getName();
    GridView gridView;
    ProgressBar progressBar;
    TextView searchTxt;
    private GridViewTVLibraryAdapter adapter;
    private AppPreference preference;
    private List<TVDTO> tvshows = new ArrayList<>();
    Spinner spinner;
    TVApi api;
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

        //     topRated();
        return v;
    }

    private void initVariables(View v) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(ApiConstants.THEMOVIIEDB_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        api = restAdapter.create(TVApi.class);

        gridView = (GridView) v.findViewById(R.id.gridView);
        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        preference = new AppPreference(getContext());

        spinner = (Spinner) v.findViewById(R.id.spinner);
        searchTxt = (TextView) v.findViewById(R.id.searchTxt);

        CategorySpinnerAdapter spinnerAdapter = new CategorySpinnerAdapter(getContext(), R.layout.item_category, AppUtils.getTvCategories());
        spinner.setAdapter(spinnerAdapter);

        adapter = new GridViewTVLibraryAdapter(getContext(), R.layout.library_layout_item, tvshows);
        gridView.setAdapter(adapter);
    }

    private void initListeners() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(getContext(), TVDetailsActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("tvshow_object", tvshows.get(position));
                startActivity(intent);
//                TVDetailsActivity.openActivity(getActivity(),tvshows.get(position));
            }
        });


        gridView.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                if(searchedString!=null && !searchedString.equals("")){
                    searchTVShows(searchedString);
                }else{
                    Category c = (Category) spinner.getSelectedItem();
                    openSelectedSpinnerItemFromTVShows(c);
                }
                // or customLoadMoreDataFromApi(totalItemsCount);
                return true; // ONLY if more data is actually being loaded; false otherwise.
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                PAGE = 1;
                tvshows.clear();
                adapter.notifyDataSetChanged();
                Category c = (Category) adapterView.getAdapter().getItem(i);
                openSelectedSpinnerItemFromTVShows(c);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void openSelectedSpinnerItemFromTVShows(Category c) {
        switch (c.getId()) {
            case 1:
                onAirToday();
                break;
            case 2:
                topRated();
                break;
            case 3:
                popularTVShows();
                break;
            case 4:
                airingToday();
                break;
        }
    }

    /*private void getLatestTVShows() {
        progressBar.setVisibility(View.VISIBLE);
        api.getLatestTVShows(ApiConstants.API_KEY, new Callback<ResponseTVDTO>() {
            @Override
            public void success(ResponseTVDTO responseTVDTO, Response response) {
                Log.d(TAG, "success getting latest tv shows from server " + responseTVDTO);
                progressBar.setVisibility(View.GONE);
                initTVShowsList(responseTVDTO);
            }
            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "failure getting latest tv shows from server :" + error);
                progressBar.setVisibility(View.GONE);
            }
        });
    }*/


    private void airingToday() {
        progressBar.setVisibility(View.VISIBLE);
        api.airingToday(ApiConstants.API_KEY, PAGE, new Callback<ResponseTVDTO>() {
            @Override
            public void success(ResponseTVDTO responseTVDTO, Response response) {
                Log.d(TAG, "success getting get tv shows from server " + responseTVDTO);
                progressBar.setVisibility(View.GONE);
                initTVShowList(responseTVDTO);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "failure getting  getTvShows from from server " + error);
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void onAirToday() {
        progressBar.setVisibility(View.VISIBLE);
        api.onAirToday(ApiConstants.API_KEY, PAGE, new Callback<ResponseTVDTO>() {
            @Override
            public void success(ResponseTVDTO responseTVDTO, Response response) {
                Log.d(TAG, "success getting on air today tv from server " + responseTVDTO);
                progressBar.setVisibility(View.GONE);
                initTVShowList(responseTVDTO);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "failure getting on air today tv  from server " + error);
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void topRated() {
        progressBar.setVisibility(View.VISIBLE);
        api.topRated(ApiConstants.API_KEY, PAGE, new Callback<ResponseTVDTO>() {
            @Override
            public void success(ResponseTVDTO responseTVDTO, Response response) {
                Log.d(TAG, "success getting top rated tv from server " + responseTVDTO);
                progressBar.setVisibility(View.GONE);
                initTVShowList(responseTVDTO);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "failure getting top rated tv from server " + error);
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void popularTVShows() {
        api.popularTVShows(ApiConstants.API_KEY, PAGE, new Callback<ResponseTVDTO>() {
            @Override
            public void success(ResponseTVDTO responseTVDTO, Response response) {
                Log.d(TAG, "success getting popular movies from server " + responseTVDTO);
                initTVShowList(responseTVDTO);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "failure getting popular movies tv from server " + error);
            }
        });
    }

//    private void getVideos() {
//        api.popularTVShows(ApiConstants.API_KEY, 1, new Callback<ResponseTVDTO>() {
//            @Override
//            public void success(ResponseTVDTO responseTVDTO, Response response) {
//                Log.d(TAG, "success getting popular movies from server " + responseTVDTO);
//                initTVShowsList(responseTVDTO);
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                Log.d(TAG, "failure getting popular movies tv from server " + error);
//            }
//        });
//    }



    private void initTVShowList(ResponseTVDTO responseTVDTO) {
        PAGE++;
        tvshows.addAll(responseTVDTO.getTvshow());
        adapter.notifyDataSetChanged();
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
                searchTVShows(newText);
                return false;
            }
        });
    }

    private void searchTVShows(String query) {
        if (query != null && !query.equals("")) {
            spinner.setVisibility(View.GONE);
            searchTxt.setVisibility(View.VISIBLE);
            searchTxt.setText("Search results for: " + query);
            searchTVShowFromServer(query);
        } else {
            spinner.setVisibility(View.VISIBLE);
            searchTxt.setVisibility(View.GONE);
            if (spinner.getSelectedItem() instanceof Category) {
                Category c = (Category) spinner.getSelectedItem();
                openSelectedSpinnerItemFromTVShows(c);
            } else {
                Toast.makeText(getContext(), "Error casting selected item", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void searchTVShowFromServer(String query) {
        Log.d(TAG, "searchTVShows=" + query);
        progressBar.setVisibility(View.VISIBLE);
        api.searchTVShow(ApiConstants.API_KEY, query, PAGE, new Callback<ResponseTVDTO>() {
            @Override
            public void success(ResponseTVDTO responseTVDTO, Response response) {
                Log.d(TAG, "success search TV shows from server " + responseTVDTO);
                progressBar.setVisibility(View.GONE);
                tvshows.clear();
                initTVShowList(responseTVDTO);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "error search TV shows from server :" + error);
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}