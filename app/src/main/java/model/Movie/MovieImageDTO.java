package model.Movie;

/**
 * Created by Vlade Ilievski on 9/6/2016.
 */
public class MovieImageDTO {
    private String file_path;
    private int height;
    private float vote_average;
    private int vote_count;
    private int width;

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public float getVote_average() {
        return vote_average;
    }

    public void setVote_average(float vote_average) {
        this.vote_average = vote_average;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public String toString() {
        return "MovieImageDTO{" +
                "file_path='" + file_path + '\'' +
                ", height=" + height +
                ", vote_average=" + vote_average +
                ", vote_count=" + vote_count +
                ", width=" + width +
                '}';
    }
}
