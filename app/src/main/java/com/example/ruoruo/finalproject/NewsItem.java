package com.example.ruoruo.finalproject;


import java.io.Serializable;

/**
 * pass the data through intent using serializable
 */

public class NewsItem implements Serializable {
    public String title;
    public String link;
    public String guid;
    public String pubDate;
    public String author;
    public String category;
    public String description;

    public NewsItem(String title, String link, String guid, String pubDate, String author, String category, String description) {
        this.title = title;
        this.link = link;
        this.guid = guid;
        this.pubDate = pubDate;
        this.author = author;
        this.category = category;
        this.description = description;
    }
}
