package com.example.tp3_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.tp3_mobile.databinding.FragmentFavoritesBinding;
import java.util.List;

public class FavoritesFragment extends Fragment implements BookAdapter.OnBookClickListener {

    private FragmentFavoritesBinding binding;
    private BookAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new BookAdapter(BookRepository.getInstance().getFavoriteBooks(), this);
        binding.recyclerViewFavorites.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewFavorites.setAdapter(adapter);

        updateEmptyState();
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
        // Refresh the list as it's the favorites fragment
        adapter.setBooks(BookRepository.getInstance().getFavoriteBooks());
        updateEmptyState();
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.setBooks(BookRepository.getInstance().getFavoriteBooks());
        updateEmptyState();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
