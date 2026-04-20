package com.nurul.praktikum_3;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.ChipGroup;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HomeFragment extends Fragment implements BookAdapter.OnBookClickListener {

    private BookAdapter adapter;
    private String currentGenre = "All";
    private String searchQuery = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView rvBooks = view.findViewById(R.id.rv_books);
        rvBooks.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new BookAdapter(DataProvider.bookList, this);
        rvBooks.setAdapter(adapter);

        // Apply initial sorting
        applyFilters();

        EditText etSearch = view.findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchQuery = s.toString();
                applyFilters();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        ChipGroup cgGenres = view.findViewById(R.id.cgGenres);
        cgGenres.setOnCheckedStateChangeListener((group, checkedIds) -> {
            if (checkedIds.isEmpty()) {
                currentGenre = "All";
            } else {
                int checkedId = checkedIds.get(0);
                if (checkedId == R.id.chipAll) {
                    currentGenre = "All";
                } else if (checkedId == R.id.chipFiction) {
                    currentGenre = "Fiction";
                } else if (checkedId == R.id.chipDystopian) {
                    currentGenre = "Dystopian";
                } else if (checkedId == R.id.chipClassic) {
                    currentGenre = "Classic";
                } else if (checkedId == R.id.chipRomance) {
                    currentGenre = "Romance";
                } else if (checkedId == R.id.chipSciFi) {
                    currentGenre = "Sci-Fi";
                }
            }
            applyFilters();
        });

        return view;
    }

    private void applyFilters() {
        List<Book> filteredList = new ArrayList<>();
        for (Book book : DataProvider.bookList) {
            boolean matchesGenre = currentGenre.equals("All") || book.getGenre().equalsIgnoreCase(currentGenre);
            boolean matchesSearch = book.getTitle().toLowerCase().contains(searchQuery.toLowerCase()) ||
                                   book.getAuthor().toLowerCase().contains(searchQuery.toLowerCase());

            if (matchesGenre && matchesSearch) {
                filteredList.add(book);
            }
        }

        // Urutkan buku terbaru (tahun terbit terbesar) di atas
        Collections.sort(filteredList, (b1, b2) -> {
            try {
                int year1 = Integer.parseInt(b1.getYear());
                int year2 = Integer.parseInt(b2.getYear());
                return year2 - year1; // Descending
            } catch (NumberFormatException e) {
                return 0;
            }
        });

        adapter.updateData(filteredList);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Refresh list setiap kali fragment ditampilkan (untuk sync status like)
        applyFilters();
    }

    @Override
    public void onBookClick(Book book) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("EXTRA_BOOK", book);
        startActivity(intent);
    }
}
