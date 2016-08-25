package ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.showbox.showbox.R;

import java.util.ArrayList;

import adapter.GridViewAdapter;
import util.AppUtils;

/**
 * Created by Vlade Ilievski on 8/15/2016.
 */
public class LibraryActivity extends AppCompatActivity implements View.OnClickListener {
    GridView gridView;
    TextView topMovies;
    Context context;
    ArrayList moviesNames;

    ProgressBar progressBar;

    private GridViewAdapter gridViewAdapter;
    //  private GridViewAdapter adapter;
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
        progressBar = (ProgressBar) findViewById(R.id.progressBar);


        gridViewAdapter=new GridViewAdapter(this, R.layout.library_movie_activity,AppUtils.getMovies());
        gridView.setAdapter(gridViewAdapter);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(LibraryActivity.this, MovieDetailsActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.favourites:
                startActivity(new Intent(this,FavoriteActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

    }
}
