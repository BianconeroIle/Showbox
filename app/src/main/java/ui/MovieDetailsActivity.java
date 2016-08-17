package ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.showbox.showbox.R;

import org.w3c.dom.Text;

import adapter.GridViewAdapter;

/**
 * Created by Vlade Ilievski on 8/16/2016.
 */
public class MovieDetailsActivity extends AppCompatActivity {
    int clickedTable = 1;
    TextView titleMovie;
    TextView director;
    ImageView imageMovieDetails;
    ImageButton star;
    TextView writers;
    TextView cast;
    TextView producedBy;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details_activity);

        clickedTable = getIntent().getExtras().getInt("clickedTable");
        titleMovie=(TextView)findViewById(R.id.titleMovie);
        director=(TextView)findViewById(R.id.director);
        imageMovieDetails=(ImageView)findViewById(R.id.imageMovieDetails);
        star=(ImageButton)findViewById(R.id.star);
        writers=(TextView)findViewById(R.id.writers);
        cast=(TextView)findViewById(R.id.cast);
        producedBy=(TextView)findViewById(R.id.producedBy);

        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey("clickedTable")) {


        }
    }
}
