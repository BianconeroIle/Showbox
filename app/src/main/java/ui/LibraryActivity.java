package ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.showbox.showbox.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import adapter.GridViewAdapter;
import model.Movies;
import util.AppUtils;

/**
 * Created by Vlade Ilievski on 8/15/2016.
 */
public class LibraryActivity extends AppCompatActivity implements View.OnClickListener {
    GridView gridView;
    TextView topMovies;
    Context context;
    ArrayList moviesNames;
    int clickedMovie = 1;
    ProgressBar progressBar;
    private ArrayList<Movies> movies;
    private GridViewAdapter adapter;
//    public static String[] movieNameList = {"Deadpool(2016)", "A Beautiful Mind(2001)", "The Dark Knight(2008)", "The Fighter(2010)", "Terminator Salvation(2009)",
//            "The Prestige(2006)", "Furry(2014)", "Goodfellas(1990)", "The Tree of life(2011)"};
//    public static int[] movieImages = {R.drawable.movie1, R.drawable.movie2, R.drawable.movie3, R.drawable.movie4, R.drawable.movie5, R.drawable.movie6,
//            R.drawable.movie6, R.drawable.movie7, R.drawable.movie8, R.drawable.movie9};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.library_activity);
        gridView = (GridView) findViewById(R.id.gridView);
        topMovies = (TextView) findViewById(R.id.topMovies);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);


        movies = new ArrayList<>();
        adapter = new GridViewAdapter(this, R.layout.library_movie_activity,movies);
        gridView.setAdapter(adapter);

        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey("clickedMovie")) {
            clickedMovie = getIntent().getExtras().getInt("clickedMovie");
        }
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Intent intent = new Intent(LibraryActivity.this, MovieDetailsActivity.class);
                intent.putExtra("id", position);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onClick(View view) {


    }
}
