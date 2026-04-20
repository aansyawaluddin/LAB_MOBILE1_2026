package com.example.tp4_mobile;

import android.net.Uri;
import java.io.Serializable;

public class Book implements Serializable {
    private String id;
    private String title;
    private String author;
    private int year;
    private String blurb;
    private String imageUri; // Store as String for easier handling
    private boolean isFavorite;
    private float rating;
    private String genre;

    public Book(String id, String title, String author, int year, String blurb, String imageUri, boolean isFavorite, float rating, String genre) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.blurb = blurb;
        this.imageUri = imageUri;
        this.isFavorite = isFavorite;
        this.rating = rating;
        this.genre = genre;
    }

    // Getters and Setters
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getYear() { return year; }
    public String getBlurb() { return blurb; }
    public String getImageUri() { return imageUri; }
    public boolean isFavorite() { return isFavorite; }
    public float getRating() { return rating; }
    public String getGenre() { return genre; }

    public void setFavorite(boolean favorite) { isFavorite = favorite; }
    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setYear(int year) { this.year = year; }
    public void setBlurb(String blurb) { this.blurb = blurb; }
    public void setImageUri(String imageUri) { this.imageUri = imageUri; }
}
