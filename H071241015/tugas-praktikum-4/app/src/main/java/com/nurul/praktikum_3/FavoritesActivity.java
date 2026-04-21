package com.nurul.praktikum_3;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FavoritesActivity extends AppCompatActivity {

    private RecyclerView rvFavorites;
    private BookAdapter adapter;
    private ProgressBar progressBar;
    private ExecutorService executorService;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        rvFavorites = findViewById(R.id.rvFavorites);
        progressBar = findViewById(R.id.progressBarFavorites);

        executorService = Executors.newSingleThreadExecutor();
        handler = new Handler(Looper.getMainLooper());

        rvFavorites.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BookAdapter(new ArrayList<>(), book -> {
            Intent intent = new Intent(FavoritesActivity.this, DetailActivity.class);
            intent.putExtra("EXTRA_BOOK", book);
            startActivity(intent);
        });
        rvFavorites.setAdapter(adapter);

        loadFavoriteBooks();
    }

    private void loadFavoriteBooks() {
        progressBar.setVisibility(View.VISIBLE);

        executorService.execute(() -> {
            try {
                // Simulasi pemrosesan data yang berat dengan delay 2 detik
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            List<Book> favorites = new ArrayList<>();
            for (Book book : DataProvider.bookList) {
                if (book.isLiked()) {
                    favorites.add(book);
                }
            }

            // Memperbarui UI di main thread
            handler.post(() -> {
                progressBar.setVisibility(View.GONE);
                adapter.updateData(favorites);
            });
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (executorService != null) {
            executorService.shutdown();
        }
    }
}
