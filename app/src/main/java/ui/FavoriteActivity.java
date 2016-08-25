package ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.showbox.showbox.R;

import java.util.List;

import adapter.FavouriteAdapter;
import model.Movie;
import util.AppUtils;

/**
 * Created by Vlade Ilievski on 8/18/2016.
 */
public class FavoriteActivity extends AppCompatActivity implements View.OnClickListener {
    ListView favouriteListView;
    TextView infoText;
    private List<Movie> movie;
    private int clickedPosition;
    FavouriteAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.favourite_activity);
        super.onCreate(savedInstanceState);


        favouriteListView = (ListView) findViewById(R.id.favouriteListView);

        movie = AppUtils.getMovies();
        if (movie == null || movie.isEmpty()) {
            infoText.setText("You library is empty");
            favouriteListView.setVisibility(View.GONE);
            infoText.setVisibility(View.VISIBLE);
        }
        adapter = new FavouriteAdapter(this, R.layout.favourite_listview_item, AppUtils.getMovies());
        favouriteListView.setAdapter(adapter);


    }

    @Override
    public void onClick(View view) {

    }
}
