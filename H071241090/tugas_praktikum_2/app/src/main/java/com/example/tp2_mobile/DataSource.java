package com.example.tp2_mobile;

import java.util.ArrayList;

public class DataSource {
    public static User feraUser;
    public static ArrayList<User> dummyUsers = new ArrayList<>();
    public static ArrayList<Post> feeds = new ArrayList<>();

    static {
        // Main User: Fera
        ArrayList<Post> feraPosts = new ArrayList<>();
        feraPosts.add(new Post("fera_kaco", R.drawable.fera_profile, R.drawable.fera_post_1, "Post 1 caption"));
        feraPosts.add(new Post("fera_kaco", R.drawable.fera_profile, R.drawable.fera_post_2, "Post 2 caption"));
        feraPosts.add(new Post("fera_kaco", R.drawable.fera_profile, R.drawable.fera_post_3, "Post 3 caption"));
        feraPosts.add(new Post("fera_kaco", R.drawable.fera_profile, R.drawable.fera_post_4, "Post 4 caption"));
        feraPosts.add(new Post("fera_kaco", R.drawable.fera_profile, R.drawable.fera_post_5, "Post 5 caption"));

        ArrayList<Integer> feraHighlights = new ArrayList<>();
        feraHighlights.add(R.drawable.fera_hl_1);
        feraHighlights.add(R.drawable.fera_hl_2);
        feraHighlights.add(R.drawable.fera_hl_3);
        feraHighlights.add(R.drawable.fera_hl_4);
        feraHighlights.add(R.drawable.fera_hl_5);
        feraHighlights.add(R.drawable.fera_hl_6);
        feraHighlights.add(R.drawable.fera_hl_7);

        feraUser = new User("fera_kaco", R.drawable.fera_profile, feraPosts, feraHighlights);
        // Dummy Users
        addDummyUser("rihanna", R.drawable.rihanna);
        addDummyUser("kali_uchis", R.drawable.kali_uchis);
        addDummyUser("mac_miller", R.drawable.mac_miller);
        addDummyUser("steve_lacy", R.drawable.steve_lacy);
        addDummyUser("the_marias", R.drawable.the_marias);
        addDummyUser("frank_ocean", R.drawable.frank_ocean);
        addDummyUser("malcolm_todd", R.drawable.malcolm_todd);
        addDummyUser("daniel_caesar", R.drawable.daniel_caesar);
        addDummyUser("kendrick_lamar", R.drawable.kendrick_lamar);
        addDummyUser("tyler_the_creator", R.drawable.tyler_the_creator);

        // Initial Home Feeds (Only Dummies)
        for (User user : dummyUsers) {
            feeds.addAll(user.getPosts());
        }
    }

    private static void addDummyUser(String name, int imageRes) {
        ArrayList<Post> posts = new ArrayList<>();
        posts.add(new Post(name, imageRes, imageRes, "Caption for " + name));
        
        ArrayList<Integer> hl = new ArrayList<>();
        hl.add(imageRes);
        hl.add(imageRes);
        hl.add(imageRes);
        hl.add(imageRes);
        hl.add(imageRes);
        hl.add(imageRes);
        hl.add(imageRes);


        User user = new User(name, imageRes, posts, hl);
        dummyUsers.add(user);
    }
}
