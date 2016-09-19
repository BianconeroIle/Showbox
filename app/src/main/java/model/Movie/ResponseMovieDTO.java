package model.Movie;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ilija Angeleski on 9/2/2016.
 */
public class ResponseMovieDTO {

    private int page;

    @SerializedName("results")
    private List<MovieDTO> movies;

    @SerializedName("total_pages")
    private int totalPages;

    @SerializedName("total_results")
    private int totalResults;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<MovieDTO> getMovies() {
        return movies;
    }

    public void setMovies(List<MovieDTO> movies) {
        this.movies = movies;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    @Override
    public String toString() {
        return "ResponseMovieDTO{" +
                "page=" + page +
                ", movies=" + movies +
                ", totalPages=" + totalPages +
                ", totalResults=" + totalResults +
                '}';
    }
}
