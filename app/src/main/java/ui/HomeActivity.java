package ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.showbox.showbox.R;

import java.util.ArrayList;

import adapter.HomeRecyclerAdapter;
import model.NewsDTO;

/**
 * Created by Vlade Ilievski on 10/12/2016.
 */

public class HomeActivity extends AppCompatActivity {
    RecyclerView recycler_view;
    HomeRecyclerAdapter homeRecyclerAdapter;
    ArrayList<NewsDTO> newsItems;
    ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        setContentView(R.layout.home_layout);

        recycler_view=(RecyclerView)findViewById(R.id.recycler_view);
        homeRecyclerAdapter=new HomeRecyclerAdapter(newsItems,this);
        recycler_view.setAdapter(homeRecyclerAdapter);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
    }
}
