package com.nurul.praktikum_3;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FavoritesFragment extends Fragment {

    private BookAdapter adapter;
    private LinearLayout emptyState;
    private RecyclerView rvFavorites;
    private ProgressBar progressBar;
    private ExecutorService executorService;
    private Handler handler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        rvFavorites = view.findViewById(R.id.rvFavorites);
        emptyState = view.findViewById(R.id.emptyState);
        progressBar = view.findViewById(R.id.progressBarFavorites);
        
        executorService = Executors.newSingleThreadExecutor();
        handler = new Handler(Looper.getMainLooper());

        rvFavorites.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new BookAdapter(new ArrayList<>(), book -> {
            Intent intent = new Intent(getActivity(), DetailActivity.class);
            intent.putExtra("EXTRA_BOOK", book);
            startActivity(intent);
        });

        rvFavorites.setAdapter(adapter);
        
        loadFavoriteBooks();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadFavoriteBooks();
    }

    private void loadFavoriteBooks() {
        if (progressBar != null) progressBar.setVisibility(View.VISIBLE);
        
        executorService.execute(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            List<Book> favList = new ArrayList<>();
            for (Book book : DataProvider.bookList) {
                if (book.isLiked()) {
                    favList.add(book);
                }
            }

            handler.post(() -> {
                if (progressBar != null) progressBar.setVisibility(View.GONE);
                adapter.updateData(favList);
                updateEmptyState(favList);
            });
        });
    }

    private void updateEmptyState(List<Book> favBooks) {
        if (favBooks.isEmpty()) {
            emptyState.setVisibility(View.VISIBLE);
            rvFavorites.setVisibility(View.GONE);
        } else {
            emptyState.setVisibility(View.GONE);
            rvFavorites.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (executorService != null) {
            executorService.shutdown();
        }
    }
}
