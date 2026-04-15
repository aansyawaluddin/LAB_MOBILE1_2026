package com.nurul.praktikum_2;

import java.io.Serializable;

// Implementasikan Serializable agar objek bisa dikirim lewat Intent ke halaman Detail Story
public class Story implements Serializable {
    private String title;
    private int imageResource;

    public Story(String title, int imageResource) {
        this.title = title;
        this.imageResource = imageResource;
    }

    public String getTitle() {
        return title;
    }

    public int getImageResource() {
        return imageResource;
    }
}
