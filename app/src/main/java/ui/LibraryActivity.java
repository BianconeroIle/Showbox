package ui;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Movie;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.showbox.showbox.R;

import java.util.ArrayList;
import java.util.List;

import adapter.GridViewAdapter;
import util.AppUtils;

/**
 * Created by Vlade Ilievski on 8/15/2016.
 */
public class LibraryActivity extends AppCompatActivity implements View.OnClickListener {
    GridView gridView;
    TextView topMovies;

    ProgressBar progressBar;
    private GridViewAdapter gridViewAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.library_activity);
        gridView = (GridView) findViewById(R.id.gridView);
        topMovies = (TextView) findViewById(R.id.topMovies);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);


        gridViewAdapter = new GridViewAdapter(this, R.layout.library_movie_activity, AppUtils.getMovies());
        gridView.setAdapter(gridViewAdapter);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(LibraryActivity.this, MovieDetailsActivity.class);
                intent.putExtra("position", position);
                intent.putExtra(MovieDetailsActivity.REQUEST_FROM, MovieDetailsActivity.FROM_LIBRARY);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);

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
