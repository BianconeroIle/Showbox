package ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.showbox.showbox.R;

import java.util.Date;
import java.util.List;

import interfaces.MovieAPI;
import model.MovieDTO;
import model.ResponseMovieDTO;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import util.AppPreference;
import util.AppUtils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView logoIcon;
    TextView usernameTextView;
    EditText username;
    TextView passwordTextView;
    EditText password;
    Button loginBtn;
    AppPreference preference;
    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preference = new AppPreference(this);

        if (AppUtils.getFavourites().isEmpty()) {
            /*List<model.Movie> favMovies = preference.getFavoriteMovies();
            if(favMovies.isEmpty()){
              // TOAST !!!
            }*/
            AppUtils.addSavedFavoeriteMovies(preference.getFavoriteMovies());
        }


        logoIcon = (ImageView) findViewById(R.id.logoIcon);
        usernameTextView = (TextView) findViewById(R.id.usernameTextView);
        username = (EditText) findViewById(R.id.usernameInputText);
        passwordTextView = (TextView) findViewById(R.id.passwordTextView);
        password = (EditText) findViewById(R.id.password);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(this);
        checkBox = (CheckBox) findViewById(R.id.checkBox);

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://api.androidhive.info/")
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        MovieAPI api = restAdapter.create(MovieAPI.class);
        Log.d("MainActivity", "trying to get movies from server");
        api.getMovie(new Callback<List<MovieDTO>>() {
            @Override
            public void success(List<MovieDTO> movies, Response response) {
                Log.d("MainActivity", "success getting movies from server ");
                for (MovieDTO movie : movies) {
                    Log.d("MainActivity", "movie : " + movie);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("MainActivity", "error getting movies from server " + error);
            }
        });
    }


    public void validateLogin() {
        username.setError(null);
        password.setError(null);

        String u = username.getText().toString();
        String p = password.getText().toString();

        if (u.equals("")) {
            username.setError("This field is required!");
        }
        if (p.equals("")) {
            password.setError("This field is required!");
        }

        if (AppUtils.checkUserExist(u, p)) {
            if (checkBox.isChecked()) {
                preference.setUserLogged(true);
                preference.setExpirationDate(new Date());
                preference.setUsername(u);
            }
            openLibraryMoviesActivity();
        } else {
            Toast.makeText(this, "The username or password you have entered is invalid.", Toast.LENGTH_LONG).show();
        }
    }

    private void openLibraryMoviesActivity() {
        Intent intent = new Intent(MainActivity.this, LibraryActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.loginBtn:
                validateLogin();
                break;
        }

    }


}


