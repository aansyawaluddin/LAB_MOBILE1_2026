package com.example.tp3_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.tp3_mobile.databinding.FragmentHomeBinding;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HomeFragment extends Fragment implements BookAdapter.OnBookClickListener {

    private FragmentHomeBinding binding;
    private BookAdapter adapter;
    private List<Book> allBooks;
    private String currentQuery = "";
    private String currentGenre = "All";

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
        adapter = new BookAdapter(allBooks, this);
        binding.recyclerViewHome.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewHome.setAdapter(adapter);

        setupSearchView();
        setupGenreDropdown();
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
                filterBooks();
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
        
        binding.dropdownGenres.setText("All", false);
        
        binding.dropdownGenres.setOnItemClickListener((parent, view, position, id) -> {
            currentGenre = genres.get(position);
            filterBooks();
        });
    }

    private void filterBooks() {
        List<Book> filteredList = allBooks.stream()
                .filter(book -> book.getTitle().toLowerCase().contains(currentQuery.toLowerCase()) || 
                                book.getAuthor().toLowerCase().contains(currentQuery.toLowerCase()))
                .filter(book -> currentGenre.equals("All") || book.getGenre().equals(currentGenre))
                .collect(Collectors.toList());
        adapter.setBooks(filteredList);
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
        filterBooks();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
