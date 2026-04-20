package com.example.tp4_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.tp4_mobile.databinding.FragmentFavoritesBinding;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FavoritesFragment extends Fragment implements BookAdapter.OnBookClickListener {

    private FragmentFavoritesBinding binding;
    private BookAdapter adapter;
    
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new BookAdapter(new ArrayList<>(), this);
        binding.recyclerViewFavorites.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewFavorites.setAdapter(adapter);
    }

    private void loadFavorites() {
        if (binding == null) return;
        
        binding.progressBar.setProgress(0);
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.recyclerViewFavorites.setVisibility(View.GONE);
        binding.tvEmptyFavorites.setVisibility(View.GONE);

        executorService.execute(() -> {
            // Simulasi progress loading 0% - 100%
            for (int i = 0; i <= 100; i += 10) {
                final int progress = i;
                try {
                    Thread.sleep(100); // Delay total 1 detik (10 x 100ms)
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                
                mainHandler.post(() -> {
                    if (binding != null) {
                        binding.progressBar.setProgress(progress);
                    }
                });
            }

            // Ambil data dari repository
            List<Book> favoriteBooks = BookRepository.getInstance().getFavoriteBooks();

            mainHandler.post(() -> {
                if (binding != null) {
                    adapter.setBooks(favoriteBooks);
                    binding.progressBar.setVisibility(View.GONE);
                    updateEmptyState();
                }
            });
        });
    }

    private void updateEmptyState() {
        if (adapter.getItemCount() == 0) {
            binding.tvEmptyFavorites.setVisibility(View.VISIBLE);
            binding.recyclerViewFavorites.setVisibility(View.GONE);
        } else {
            binding.tvEmptyFavorites.setVisibility(View.GONE);
            binding.recyclerViewFavorites.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBookClick(Book book) {
        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra("book", book);
        startActivity(intent);
    }

    @Override
    public void onFavoriteClick(Book book) {
        BookRepository.getInstance().updateBook(book);
        loadFavorites(); 
    }

    @Override
    public void onResume() {
        super.onResume();
        loadFavorites();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
