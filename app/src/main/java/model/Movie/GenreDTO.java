package model.Movie;

import java.io.Serializable;

/**
 * Created by Vlade Ilievski on 9/5/2016.
 */
public class GenreDTO implements Serializable{

    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "GenreDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
