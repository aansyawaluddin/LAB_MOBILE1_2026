package com.nurul.praktikum_2;

import java.util.ArrayList;

public class DataSource {
    public static ArrayList<Post> homePosts = new ArrayList<>();
    public static ArrayList<Post> profilePosts = new ArrayList<>();
    public static ArrayList<Story> profileStories = new ArrayList<>();

    private static boolean isInitialized = false;

    public static void initData() {
        if (isInitialized) return;

        // Daftar Nama Teman
        String[] names = {"Adel", "Adit", "Anas", "Devi", "Dennis", "Budi", "Santi", "Eko", "Maya", "Rian"};
        
        // Daftar Drawable Foto Profil (adel, adit, anas, devi, dennis)
        int[] profilePics = {
            R.drawable.adel, R.drawable.adit, R.drawable.anas, 
            R.drawable.devi, R.drawable.dennis,
            R.drawable.adel, R.drawable.adit, R.drawable.anas, 
            R.drawable.devi, R.drawable.dennis
        };

        // Daftar Resource Name untuk Postingan
        String[] postResourceNames = {
            "satu", "dua", "tiga", "empat", "lima",
            "enam", "tujuh", "delapan", "sembilan", "sepuluh"
        };

        // Daftar Drawable Postingan
        int[] postImages = new int[10];
        for (int i = 0; i < 10; i++) {
            postImages[i] = getResId(postResourceNames[i]);
        }

        // --- Data Home (10 item) ---
        for (int i = 0; i < 10; i++) {
            int postResId = postImages[i];
            if (postResId == 0) postResId = android.R.drawable.ic_menu_gallery; // fallback
            
            homePosts.add(new Post(
                names[i], 
                profilePics[i], 
                postResId, 
                "Keindahan alam di postingan ke-" + (i + 1)
            ));
        }

        // --- Data Profile Feed (6 item milik Nurul Marisa) ---
        for (int i = 0; i < 6; i++) {
            int postResId = postImages[i];
            if (postResId == 0) postResId = android.R.drawable.ic_menu_gallery;
            
            profilePosts.add(new Post(
                "Nurul Marisa", 
                R.drawable.satu, 
                postResId, 
                "Koleksi foto feed Nurul #" + (i + 1)
            ));
        }

        // --- Data Highlight Story (7 item) ---
        String[] highlights = {"Holiday", "Friends", "Food", "Nature", "Work", "Life", "Music"};
        String[] storyResourceNames = {"s1", "s2", "s3", "s4", "s5", "s6", "s7"};
        for (int i = 0; i < 7; i++) {
            profileStories.add(new Story(highlights[i], getResId(storyResourceNames[i])));
        }

        isInitialized = true;
    }

    // Helper untuk mengambil resource ID berdasarkan string nama
    private static int getResId(String resName) {
        try {
            return R.drawable.class.getField(resName).getInt(null);
        } catch (Exception e) {
            return 0;
        }
    }
}
