package com.example.tp4_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.tp4_mobile.databinding.FragmentHomeBinding;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class HomeFragment extends Fragment implements BookAdapter.OnBookClickListener {

    private FragmentHomeBinding binding;
    private BookAdapter adapter;
    private List<Book> allBooks;
    private String currentQuery = "";
    private String currentGenre = "All";
    
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    private Runnable searchRunnable;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        allBooks = BookRepository.getInstance().getAllBooks();
        adapter = new BookAdapter(new ArrayList<>(), this);
        binding.recyclerViewHome.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewHome.setAdapter(adapter);

        setupSearchView();
        setupGenreDropdown();
        // Hapus filterBooks() di sini agar tidak dobel dengan onResume
    }

    private void setupSearchView() {
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                currentQuery = newText;
                
                if (searchRunnable != null) {
                    mainHandler.removeCallbacks(searchRunnable);
                }
                
                searchRunnable = () -> filterBooks();
                mainHandler.postDelayed(searchRunnable, 400); // Debounce 300ms
                
                return true;
            }
        });
    }

    private void setupGenreDropdown() {
        List<String> genres = new ArrayList<>();
        genres.add("All");
        genres.addAll(allBooks.stream()
                .map(Book::getGenre)
                .distinct()
                .sorted()
                .collect(Collectors.toList()));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), 
                android.R.layout.simple_dropdown_item_1line, genres);
        binding.dropdownGenres.setAdapter(adapter);
        
        binding.dropdownGenres.setText(currentGenre, false);
        
        binding.dropdownGenres.setOnItemClickListener((parent, view, position, id) -> {
            currentGenre = genres.get(position);
            filterBooks();
        });
    }

    private void filterBooks() {
        if (binding == null) return;
        binding.progressBar.setVisibility(View.VISIBLE);
        
        executorService.execute(() -> {
            // Background processing
            List<Book> filteredList = allBooks.stream()
                    .filter(book -> book.getTitle().toLowerCase().contains(currentQuery.toLowerCase()) || 
                                    book.getAuthor().toLowerCase().contains(currentQuery.toLowerCase()))
                    .filter(book -> currentGenre.equals("All") || book.getGenre().equals(currentGenre))
                    .collect(Collectors.toList());

            mainHandler.post(() -> {
                if (binding != null) {
                    adapter.setBooks(filteredList);
                    binding.progressBar.setVisibility(View.GONE);
                }
            });
        });
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
    }

    @Override
    public void onResume() {
        super.onResume();
        allBooks = BookRepository.getInstance().getAllBooks();
        setupGenreDropdown();
        filterBooks(); // Cukup dipanggil di sini saat pindah halaman
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (searchRunnable != null) {
            mainHandler.removeCallbacks(searchRunnable);
        }
        binding = null;
    }
}
