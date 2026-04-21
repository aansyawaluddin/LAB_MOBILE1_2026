package com.nurul.praktikum_3;

import java.io.Serializable;

public class Book implements Serializable {
    private String id, title, author, year, blurb, genre, review, coverUri;
    private int coverImage;
    private boolean isLiked;
    private double rating;

    public Book(String id, String title, String author, String year, String blurb, int coverImage, double rating, String genre, String review) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.blurb = blurb;
        this.coverImage = coverImage;
        this.rating = rating;
        this.genre = genre;
        this.review = review;
        this.isLiked = false;
        this.coverUri = null;
    }

    // Getters
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getYear() { return year; }
    public String getBlurb() { return blurb; }
    public int getCoverImage() { return coverImage; }
    public String getCoverUri() { return coverUri; }
    public boolean isLiked() { return isLiked; }
    public double getRating() { return rating; }
    public String getGenre() { return genre; }
    public String getReview() { return review; }

    // Setters
    public void setLiked(boolean liked) { isLiked = liked; }
    public void setCoverUri(String coverUri) { this.coverUri = coverUri; }
}
