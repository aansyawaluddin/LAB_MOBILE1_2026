package com.nurul.praktikum_3;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class FavoritesFragment extends Fragment {

    private BookAdapter adapter;
    private LinearLayout emptyState;
    private RecyclerView rvFavorites;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        rvFavorites = view.findViewById(R.id.rvFavorites);
        emptyState = view.findViewById(R.id.emptyState);
        rvFavorites.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new BookAdapter(getFavoriteBooks(), book -> {
            Intent intent = new Intent(getActivity(), DetailActivity.class);
            intent.putExtra("EXTRA_BOOK", book);
            startActivity(intent);
        });

        rvFavorites.setAdapter(adapter);
        updateEmptyState();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Perbarui daftar setiap kali fragment ditampilkan (sesuai syarat tugas)
        adapter.updateData(getFavoriteBooks());
        updateEmptyState();
    }

    private void updateEmptyState() {
        List<Book> favBooks = getFavoriteBooks();
        if (favBooks.isEmpty()) {
            emptyState.setVisibility(View.VISIBLE);
            rvFavorites.setVisibility(View.GONE);
        } else {
            emptyState.setVisibility(View.GONE);
            rvFavorites.setVisibility(View.VISIBLE);
        }
    }

    private List<Book> getFavoriteBooks() {
        List<Book> favList = new ArrayList<>();
        for (Book book : DataProvider.bookList) {
            if (book.isLiked()) {
                favList.add(book);
            }
        }
        return favList;
    }
}