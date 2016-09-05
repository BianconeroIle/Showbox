package util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import model.GenreDTO;
import model.Movie;
import model.MovieDTO;
import ui.MainActivity;

/**
 * Created by Vlade Ilievski on 9/1/2016.
 */
public class AppPreference {
    public static final String TAG = AppPreference.class.getName();

    Context context;
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    public AppPreference(Context context) {
        this.context = context;
        this.sp = context.getSharedPreferences("Showbox.preference", Context.MODE_PRIVATE);
        this.editor = sp.edit();
    }

    public void setUsername(String username) {
        editor.putString("app.username", username);
        editor.commit();
    }

    public void setUserLogged(boolean logged) {
        editor.putBoolean("app.logged", logged);
        editor.commit();
    }

    public void saveFavoriteMovies(List<Movie> movies) {
        Log.d("AppPreferences", "saveMovie()");
        Gson gson = new Gson();
        editor.putString("app.favMovies", gson.toJson(movies));
        editor.commit();
    }

    public void saveMovieGenres(List<GenreDTO> genres) {
        Log.d("AppPreferences", "saveMovieGenres()");
        Gson gson = new Gson();
        editor.putString("app.movieGenres", gson.toJson(genres));
        editor.commit();
    }

    public List<GenreDTO> getMovieGenres() {
        Log.d("AppPreferences", "getMovieGenres()");
        Gson gson = new Gson();
        String jsonString = sp.getString("app.movieGenres", "");
        if (!"".equals(jsonString)) {
            Type collectionType = new TypeToken<List<GenreDTO>>() {
            }.getType();

            List<GenreDTO> listObj = gson.fromJson(jsonString, collectionType);
            return listObj;
        }
        return Collections.emptyList();
    }

    public List<MovieDTO> getFavoriteMovies() {
        Log.d("AppPreferences", "getFavoriteMovies()");
        Gson gson = new Gson();
        String jsonString = sp.getString("app.favMovies", "");
        if (!"".equals(jsonString)) {
            Type collectionType = new TypeToken<List<Movie>>() {
            }.getType();

            List<MovieDTO> listObj = gson.fromJson(jsonString, collectionType);
            return listObj;
        }
        return Collections.emptyList();
    }

    public void setExpirationDate(Date date) {
        editor.putLong("app.expirationDate", date.getTime());
        editor.commit();
    }

    public long getExpirationDate() {
        return sp.getLong("app.expirationDate", 0);
    }


    private boolean compareAuthorizationDates(Date d1, Date d2) {
        long passedTimeInMilliseconds = d1.getTime() - d2.getTime();
        Log.d(TAG, "compareAuthorizationDates, passedTime in min=" + ((passedTimeInMilliseconds / (1000 * 60)) % 60));
        if (passedTimeInMilliseconds >= (AppUtils.LOGIN_EXPIRATION_TIME_MIN * 60 * 1000)) {
            return false;
        }
        return true;
    }

    public boolean isAuthorised() {
        boolean authorised = false;
        Date currentDate = new Date();
        Date loggingDate = new Date(getExpirationDate());
        authorised = compareAuthorizationDates(currentDate, loggingDate);
        return authorised;
    }

    public boolean checkIsAuthenticatedAndLogout() {
        if (!isAuthorised()) {
            Intent i = new Intent(context, MainActivity.class);
            i.putExtra("loginExpired", true);
            context.startActivity(i);
            return false;
        }
        return true;
    }
}
