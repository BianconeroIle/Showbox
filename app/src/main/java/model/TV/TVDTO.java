package model.TV;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Arrays;

import model.Movie.GenreDTO;

/**
 * Created by Vlade Ilievski on 9/14/2016.
 */
public class TVDTO implements Serializable {

    @SerializedName("first_air_date")
    private String firstAirDate;

    @SerializedName("genres")
    private GenreDTO[] genre;
    private int id;
    private int[] episode_run_time;
    private String name;
    private String original_language;
    private String original_name;
    private String overview;
    private String[] origin_country;
    private String poster_path;
    private float popularity;
    private String homepage;
    private String[] languages;
    private String last_air_date;
    private int number_of_episodes;
    private int number_of_seasons;


    private double vote_average;

    private int vote_count;

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public GenreDTO[] getGenre() {
        return genre;
    }

    public void setGenre(GenreDTO[] genre) {
        this.genre = genre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEpisode_run_time(int[]  episode_run_time) {
        this.episode_run_time = episode_run_time;
    }

    public int[] getEpisode_run_time() {
        return episode_run_time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getOriginal_name() {
        return original_name;
    }

    public void setOriginal_name(String original_name) {
        this.original_name = original_name;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String[] getOrigin_country() {
        return origin_country;
    }

    public void setOrigin_country(String[] origin_country) {
        this.origin_country = origin_country;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public float getPopularity() {
        return popularity;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String[] getLanguages() {
        return languages;
    }

    public void setLanguages(String[] languages) {
        this.languages = languages;
    }

    public String getLast_air_date() {
        return last_air_date;
    }

    public void setLast_air_date(String last_air_date) {
        this.last_air_date = last_air_date;
    }

    public int getNumber_of_episodes() {
        return number_of_episodes;
    }

    public void setNumber_of_episodes(int number_of_episodes) {
        this.number_of_episodes = number_of_episodes;
    }

    public int getNumber_of_seasons() {
        return number_of_seasons;
    }

    public void setNumber_of_seasons(int number_of_seasons) {
        this.number_of_seasons = number_of_seasons;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    @Override
    public String toString() {
        return "TVDTO{" +
                "firstAirDate='" + firstAirDate + '\'' +
                ", genre=" + Arrays.toString(genre) +
                ", id=" + id +
                ", episode_run_time=" + (episode_run_time) +
                ", name='" + name + '\'' +
                ", original_language='" + original_language + '\'' +
                ", original_name='" + original_name + '\'' +
                ", overview='" + overview + '\'' +
                ", origin_country='" + origin_country + '\'' +
                ", poster_path='" + poster_path + '\'' +
                ", popularity=" + popularity +
                ", homepage='" + homepage + '\'' +
                ", languages=" + Arrays.toString(languages) +
                ", last_air_date='" + last_air_date + '\'' +
                ", number_of_episodes=" + number_of_episodes +
                ", number_of_seasons=" + number_of_seasons +
                ", rating=" + vote_average +
                ", vote_count=" + vote_count +
                '}';
    }
}