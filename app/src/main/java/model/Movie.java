package model;

import android.widget.ImageView;

import java.io.Serializable;

/**
 * Created by Vlade Ilievski on 8/15/2016.
 */
public class Movie implements Serializable {
    private String Title;
    private String ReleaseYear;
    private String Producer;
    private String Director;
    private String Actors;
    private double Rate;
    private String description;
    private String moviePicture;
    private String Gerne;

    public Movie(String title, String producer, String director, String actors, double rate, String description, String moviePicture,String gerne,String releaseYear) {
        Title = title;
        Producer = producer;
        Director = director;
        Actors = actors;
        Rate = rate;
        Gerne=gerne;
        ReleaseYear=releaseYear;
        this.description = description;
        this.moviePicture = moviePicture;
    }

    public String getReleaseYear() {return ReleaseYear;}

    public void setReleaseYear(String releaseYear) {ReleaseYear = releaseYear;}

    public String getGerne() {return Gerne;}

    public void setGerne(String gerne) {Gerne = gerne;}

    public String getTitle() {return Title;}

    public void setTitle(String title) {
        Title = title;
    }

    public String getProducer() {
        return Producer;
    }

    public void setProducer(String producer) {
        Producer = producer;
    }

    public String getDirector() {
        return Director;
    }

    public void setDirector(String director) {
        Director = director;
    }

    public String getActors() {
        return Actors;
    }

    public void setActors(String actors) {
        Actors = actors;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRate() {
        return Rate;
    }

    public void setRate(double rate) {
        Rate = rate;
    }

    public String getMoviePicture() {return moviePicture;}

    public void setMoviePicture(String moviePicture) {
        this.moviePicture = moviePicture;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        if (Double.compare(movie.Rate, Rate) != 0) return false;
        if (Title != null ? !Title.equals(movie.Title) : movie.Title != null) return false;
        if (Producer != null ? !Producer.equals(movie.Producer) : movie.Producer != null)
            return false;
        if (Director != null ? !Director.equals(movie.Director) : movie.Director != null)
            return false;
        if (Actors != null ? !Actors.equals(movie.Actors) : movie.Actors != null) return false;
        if (description != null ? !description.equals(movie.description) : movie.description != null)
            return false;
        return moviePicture != null ? moviePicture.equals(movie.moviePicture) : movie.moviePicture == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = Title != null ? Title.hashCode() : 0;
        result = 31 * result + (Producer != null ? Producer.hashCode() : 0);
        result = 31 * result + (Director != null ? Director.hashCode() : 0);
        result = 31 * result + (Actors != null ? Actors.hashCode() : 0);
        temp = Double.doubleToLongBits(Rate);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (moviePicture != null ? moviePicture.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "Title='" + Title + '\'' +
                ", Producer='" + Producer + '\'' +
                ", Director='" + Director + '\'' +
                ", Actors='" + Actors + '\'' +
                ", Rate=" + Rate +
                ", description='" + description + '\'' +
                ", moviePicture='" + moviePicture + '\'' +
                '}';
    }
}