package ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.showbox.showbox.R;

import java.util.ArrayList;
import java.util.List;

import adapter.FavouriteAdapter;
import model.Movie.MovieDTO;
import model.TV.TVDTO;
import util.AppPreference;
import util.AppUtils;

/**
 * Created by Ilija Angeleski on 8/18/2016.
 */
public class FavoriteActivity extends AppCompatActivity implements View.OnClickListener {
    ListView favouriteListView;
    TextView infoText;
    private FavouriteAdapter adapter;

    AppPreference preference;
    //    private List<Movie> movies;
    public static final String TAG=FavoriteActivity.class.getName();
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favourite_activity);

        infoText = (TextView) findViewById(R.id.infoText);
        favouriteListView = (ListView) findViewById(R.id.favouriteListView);

        List<MovieDTO> movie = new ArrayList<>(AppUtils.getFavourites());
        List<TVDTO> tvshow = new ArrayList<>(AppUtils.getFavouritestvshow());
        if (movie == null || movie.isEmpty() && tvshow==null || tvshow.isEmpty()) {
            infoText.setText("You library is empty");
            favouriteListView.setVisibility(View.GONE);
            infoText.setVisibility(View.VISIBLE);
        }




        favouriteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(FavoriteActivity.this, MovieDetailsActivity.class);
                intent.putExtra("position", position);
                intent.putExtra(MovieDetailsActivity.REQUEST_FROM, MovieDetailsActivity.FROM_FAVORITES);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter = new FavouriteAdapter(this, R.layout.favourite_listview_item, new ArrayList<>(AppUtils.getFavourites()));      // ????????????????????
        favouriteListView.setAdapter(adapter);
        List<MovieDTO> movie = new ArrayList<>(AppUtils.getFavourites());
        if (movie == null || movie.isEmpty()) {
            infoText.setText("You library is empty");
            favouriteListView.setVisibility(View.GONE);
            infoText.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onClick(View view) {

    }
}
