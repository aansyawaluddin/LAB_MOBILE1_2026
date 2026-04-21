package com.nurul.praktikum_3;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.ChipGroup;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeFragment extends Fragment implements BookAdapter.OnBookClickListener {

    private BookAdapter adapter;
    private String currentGenre = "All";
    private String searchQuery = "";
    private ProgressBar progressBar;
    private ExecutorService executorService;
    private Handler handler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView rvBooks = view.findViewById(R.id.rv_books);
        progressBar = view.findViewById(R.id.progressBarHome);

        executorService = Executors.newSingleThreadExecutor();
        handler = new Handler(Looper.getMainLooper());

        rvBooks.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new BookAdapter(new ArrayList<>(DataProvider.bookList), this);
        rvBooks.setAdapter(adapter);

        applyFilters();

        SearchView searchView = view.findViewById(R.id.searchViewHome);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchQuery = query;
                applyFilters();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchQuery = newText;
                applyFilters();
                return true;
            }
        });

        ChipGroup cgGenres = view.findViewById(R.id.cgGenres);
        cgGenres.setOnCheckedStateChangeListener((group, checkedIds) -> {
            if (checkedIds.isEmpty()) {
                currentGenre = "All";
            } else {
                int checkedId = checkedIds.get(0);
                if (checkedId == R.id.chipAll) currentGenre = "All";
                else if (checkedId == R.id.chipFiction) currentGenre = "Fiction";
                else if (checkedId == R.id.chipDystopian) currentGenre = "Dystopian";
                else if (checkedId == R.id.chipClassic) currentGenre = "Classic";
                else if (checkedId == R.id.chipRomance) currentGenre = "Romance";
                else if (checkedId == R.id.chipSciFi) currentGenre = "Sci-Fi";
            }
            applyFilters(); // Memanggil thread saat filter genre berubah
        });

        return view;
    }

    // File: HomeFragment.java

    private void applyFilters() {
        if (progressBar != null) progressBar.setVisibility(View.VISIBLE);

        executorService.execute(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            List<Book> filteredList = new ArrayList<>();
            for (Book book : DataProvider.bookList) {
                boolean matchesGenre = currentGenre.equals("All") || book.getGenre().equalsIgnoreCase(currentGenre);
                boolean matchesSearch = book.getTitle().toLowerCase().contains(searchQuery.toLowerCase()) ||
                        book.getAuthor().toLowerCase().contains(searchQuery.toLowerCase());

                if (matchesGenre && matchesSearch) {
                    filteredList.add(book);
                }
            }

            Collections.sort(filteredList, (b1, b2) -> {
                try {
                    return Integer.parseInt(b2.getYear()) - Integer.parseInt(b1.getYear());
                } catch (NumberFormatException e) { return 0; }
            });

            handler.post(() -> {
                if (progressBar != null) progressBar.setVisibility(View.GONE);
                adapter.updateData(filteredList);
            });
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        applyFilters();
    }

    @Override
    public void onBookClick(Book book) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("EXTRA_BOOK", book);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (executorService != null) {
            executorService.shutdown();
        }
    }
}