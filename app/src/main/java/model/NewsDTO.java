package model;

import java.io.Serializable;

/**
 * Created by Vlade Ilievski on 10/4/2016.
 */

public class NewsDTO implements Serializable {

    String title;
    String link;
    String description;
    String pubDate;



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }


}
