package util;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.Category;
import model.Movie.MovieDTO;
import model.TV.TVDTO;
import model.User;

/**
 * Created by Ilija Angeleski on 8/15/2016.
 */
public class AppUtils {
    public static final double LOGIN_EXPIRATION_TIME_MIN = 3600;
    public static final List<User> user = new ArrayList<>();
    public static final List<MovieDTO> movies = new ArrayList<>();
    public static final Set<MovieDTO> favourites = new HashSet<>();
    public static final List<Category> movieCategories = new ArrayList<>();
    public static final List<Category> tvCategories = new ArrayList<>();
    public static final List<TVDTO> tvshows= new ArrayList<>();
    public static final Set<TVDTO> favouritestvshow = new HashSet<>();

    static {
        user.add(new User(User.APP_USER, "ile", "ile123"));
        user.add(new User(User.APP_USER, "admin", "Admin"));

        movieCategories.add(new Category(1, "Now Playing"));
        movieCategories.add(new Category(2, "Most Popular"));
        movieCategories.add(new Category(3, "Top Rated"));

        tvCategories.add(new Category(1,"On Air Today"));
        tvCategories.add(new Category(2,"Top Rated"));
        tvCategories.add(new Category(3,"Popular TV Shows"));
        tvCategories.add(new Category(4,"Airing today"));


//        movies.add(new Movie("Deadpool(2016)","Christopher Nolan","Christopher Nolan","Christopher Nolan",8.0,"Christopher Nolan","http://media.comicbook.com/2016/02/deadpool-caturday-header-168596.jpg"));
//        movies.add(new Movie("A beautiful mind(2001)","Christopher Nolan","Christopher Nolan","Christopher Nolan",9.0,"Christopher Nolan","http://resizing.flixster.com/xSJ4-Lk-kSIkwZoow_b9vFQcaA8=/798x1064/v1.bTsxMTYxNDIzMztqOzE3MTQ3OzIwNDg7Nzk4OzEwNjQ"));
//        movies.add(new Movie("The Dark Knight(2008)","Christopher Nolan","Christopher Nolan","Christopher Nolan",9.1,"Christopher Nolan","http://ia.media-imdb.com/images/M/MV5BMTMxNTMwODM0NF5BMl5BanBnXkFtZTcwODAyMTk2Mw@@._V1_UY1200_CR90,0,630,1200_AL_.jpg"));
//        movies.add(new Movie("The Fighter(2010)","Christopher Nolan","Christopher Nolan","Christopher Nolan",5.6,"Christopher Nolan","http://www.impawards.com/2010/posters/fighter_ver3.jpg"));
//        movies.add(new Movie("Terminator Salvation(2009)","Christopher Nolan","Christopher Nolan","Christopher Nolan",3.5,"Christopher Nolan","http://www.impawards.com/2009/posters/terminator_salvation_ver7.jpg"));
//        movies.add(new Movie("The Prestige(2006)","Christopher Nolan","Christopher Nolan","Christopher Nolan",8.8,"Christopher Nolan","http://www.productwiki.com/upload/images/the_prestige_2006.jpg"));
//        movies.add(new Movie("Furry(2014)","Christopher Nolan","Christopher Nolan","Christopher Nolan",7.4,"Christopher Nolan","http://ia.media-imdb.com/images/M/MV5BMjA4MDU0NTUyN15BMl5BanBnXkFtZTgwMzQxMzY4MjE@._V1_UY1200_CR90,0,630,1200_AL_.jpg"));
//        movies.add(new Movie("Goodfellas(1990)","Christopher Nolan","Christopher Nolan","Christopher Nolan",6.3,"Christopher Nolan","https://cinemafanatic.files.wordpress.com/2010/10/goodfellas_poster.jpg"));
//        movies.add(new Movie("The Tree of life(2011)","Christopher Nolan","Christopher Nolan","Christopher Nolan",8.1,"Christopher Nolan","http://blogs.psychcentral.com/movies/files/2011/10/Tree-of-Life1.jpg"));
//        movies.add(new Movie("The Masterpiece(2016)","Christopher Nolan","Christopher Nolan","Christopher Nolan",6.3,"Christopher Nolan","http://ia.media-imdb.com/images/M/MV5BOTcyYzQwNmItY2FlMC00ODM5LThiOGMtM2E0NjZlMjIwZTE2XkEyXkFqcGdeQXVyMjc2NTc3NDA@._V1_SX640_SY720_.jpg"));
//        movies.add(new Movie("Blood Diamond(2006)","Christopher Nolan","Christopher Nolan","Christopher Nolan",7.0,"Christopher Nolan","http://film.geourdu.com/wp-content/uploads/2015/05/Blood-Diamond-2006.jpg"));
//        movies.add(new Movie("Shutter Island(2010)","Christopher Nolan","Christopher Nolan","Christopher Nolan",7.3,"Christopher Nolan","https://esulecinefilo.files.wordpress.com/2010/03/shutter_island_movie_poster2.jpg"));
//        movies.add(new Movie("The Next Three Days (2010)","Christopher Nolan","Christopher Nolan","Christopher Nolan",6.1,"Christopher Nolan","https://upload.wikimedia.org/wikipedia/en/b/bc/The_Next_Three_Days_Poster.jpg"));


    }

    public static Set<TVDTO> getFavouritestvshow() {
        return favouritestvshow;
    }

    public static List<TVDTO> getTvshows() {
        return tvshows;
    }

    public static Set<MovieDTO> getFavourites() {
        return favourites;
    }

    public static List<Category> getTvCategories() {return tvCategories;}

    public static void addSavedFavouriteMovies(List<MovieDTO> savedMovies) {movies.addAll(savedMovies);}

    public static List<Category> getMovieCategories() {return movieCategories;}

    public static void addSavedFavouriteTVShow(List<TVDTO> savedTVShow){tvshows.addAll(savedTVShow);}

    public static void addFavouriteTvShow(TVDTO tvShow){
        favouritestvshow.add(tvShow);
        Log.d("AppUtils","addFavouriteTvShow"+favouritestvshow);
    }
    public static void removeFromFavouriteTvShow(TVDTO tvShow){
        favouritestvshow.add(tvShow);
        Log.d("AppUtils","removeFromFavouriteTvShow"+favouritestvshow);
    }

    public static void addFavouriteMovie(MovieDTO movie) {
        favourites.add(movie);
        Log.d("AppUtils", "addFavouriteMovie:" + favourites);
    }

    public static void removeFromFavourites(MovieDTO movie) {
        favourites.remove(movie);
        Log.d("AppUtils", "removeFromFavourites: " + favourites);
    }

    public static List<User> getUser() {
        return user;
    }

    public static void addMovie(MovieDTO movie, AppPreference preference) {
        movies.add(movie);

        if (preference != null) {
            preference.savedMovies(getMovies());
        }
    }
    public static void deleteMovie(MovieDTO movie, AppPreference preference){
        movies.remove(movie);

        if(preference !=null){
            preference.savedMovies(getMovies());
        }
    }
    public static void addTVShow(TVDTO tvShow, AppPreference preference) {
        tvshows.add(tvShow);

        if (preference != null) {
            preference.savedTVShow(getTvshows());
        }
    }
    public static void deleteTVShow(TVDTO tvShow, AppPreference preference){
        tvshows.remove(tvShow);

        if(preference !=null){
            preference.savedTVShow(getTvshows());
        }
    }

    public static List<MovieDTO> getMovies() {
        return movies;
    }

    public static boolean checkUserExist(String username, String password) {
        for (User user : AppUtils.getUser()) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }



    public static boolean isMovieInFavourites(MovieDTO movie) {
        return favourites.contains(movie);
    }
    public static boolean isTVShowInFavourites(TVDTO tvShow){
        return favouritestvshow.contains(tvShow);
    }
}
