package com.nurul.praktikum_2;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inisialisasi data dummy saat aplikasi pertama kali dibuka
        DataSource.initData();

        RecyclerView rvHome = findViewById(R.id.rvHome);
        rvHome.setLayoutManager(new LinearLayoutManager(this));

        HomeAdapter adapter = new HomeAdapter(DataSource.homePosts, post -> {
            // Beranda -> Halaman Profil
            // Ketika mengklik profil di feed beranda, alurnya ke ProfileActivity
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            intent.putExtra("EXTRA_POST", post);
            startActivity(intent);
        });

        rvHome.setAdapter(adapter);
    }
}
