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

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.showbox.showbox.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import interfaces.ApiConstants;
import interfaces.MovieAPI;
import model.Movie.MovieDTO;
import model.Movie.ResponseMovieDTO;
import model.TV.TVDTO;
import model.User;
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
    private LoginButton loginButton;
    private CallbackManager callbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_main);
        AppEventsLogger.activateApp(this);

        preference = new AppPreference(this);
        List<MovieDTO> savedMovies = preference.getSavedMovies();
        List<TVDTO> savedTVShows=preference.getSavedTVShow();
        if (!savedMovies.isEmpty() && !savedTVShows.isEmpty()) {
            AppUtils.addSavedFavouriteMovies(savedMovies);
            AppUtils.addSavedFavouriteTVShow(savedTVShows);
        } else {
//            Toast.makeText(MainActivity.this,
//                    "Empty library!", Toast.LENGTH_LONG).show();
        }

        if (AppUtils.getFavourites().isEmpty() && AppUtils.getFavouritestvshow().isEmpty()) {
            /*List<model.Movie.Movie> favMovies = preference.getFavoriteMovies();
            if(favMovies.isEmpty()){
              // TOAST !!!
            }*/
            AppUtils.addSavedFavouriteMovies(preference.getFavoriteMovies());
            AppUtils.addSavedFavouriteTVShow(preference.getFavoriteTVShow());
        }


        logoIcon = (ImageView) findViewById(R.id.logoIcon);
        usernameTextView = (TextView) findViewById(R.id.usernameTextView);
        username = (EditText) findViewById(R.id.usernameInputText);
        passwordTextView = (TextView) findViewById(R.id.passwordTextView);
        password = (EditText) findViewById(R.id.password);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(this);
        checkBox = (CheckBox) findViewById(R.id.checkBox);

        loginButton = (LoginButton) findViewById(R.id.login_button);

        onFacebookLogin();


        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(ApiConstants.THEMOVIIEDB_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        MovieAPI api = restAdapter.create(MovieAPI.class);
        Log.d("MainActivity", "trying to get movies from server");
        /*api.getMovie(new Callback<List<MovieDTO>>() {
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
        });*/


        api.getMostPopular(ApiConstants.API_KEY, 1, new Callback<ResponseMovieDTO>() {
            @Override
            public void success(ResponseMovieDTO responseMovieDTO, Response response) {
                Log.d("MainActivity", "success getting movies from server " + responseMovieDTO);

            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("MainActivity", "success getting movies from server ");
            }
        });
    }


    private void onFacebookLogin() {
        callbackManager = CallbackManager.Factory.create();

        // Set permissions
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "user_photos", "public_profile"));

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d("MainActivity", "onSuccess");
                        GraphRequest.newMeRequest(
                                loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject json, GraphResponse response) {
                                        Log.d("MainActivity", "onCompleted GraphRequest");

                                        try {
                                            Log.d("MainActivity", "JSON Result" + json);

                                           /* String str_email = json.getString("email");
                                            String str_id = json.getString("id");
                                            String str_firstname = json.getString("first_name");
                                            String str_lastname = json.getString("last_name");*/

                                            User u = new User(User.FACEBOOK_USER);
                                            String[] name = json.getString("name").split(" ");
                                            String firstName = name[0] != null ? name[0] : "John";
                                            String lastName = name[1] != null ? name[1] : "Smith";
                                            u.setFirstName(json.has("first_name") ? json.getString("first_name") : firstName);
                                            u.setLastName(json.has("last_name") ? json.getString("last_name") : lastName);
                                            u.setFbId(json.has("id") ? json.getString("id") : "");
                                            u.setEmail(json.has("email") ? json.getString("email") : "");
                                            u.setFbImage("https://graph.facebook.com/" + u.getFbId() + "/picture?type=large");

                                            preference.saveFacebookUser(u);

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        openNavMainActivity();
                                    }

                                }).executeAsync();

                    }

                    @Override
                    public void onCancel() {
                        Log.d("MainActivity", "On cancel");
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.d("MainActivity", error.toString());
                        Toast.makeText(MainActivity.this,
                                "Invalid login, Please try again", Toast.LENGTH_LONG).show();
                    }
                });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
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
            openNavMainActivity();
        } else {
            Toast.makeText(this, "The username or password you have entered is invalid.", Toast.LENGTH_LONG).show();
        }
    }

    private void openLibraryMoviesActivity() {
        Intent intent = new Intent(MainActivity.this, LibraryActivity.class);
        startActivity(intent);
        finish();
    }

    private void openNavMainActivity() {
        Intent intent = new Intent(MainActivity.this, NavigationMainActivity.class);
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


