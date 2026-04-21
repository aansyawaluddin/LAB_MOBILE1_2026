package com.example.tp4_mobile;

import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.tp4_mobile.databinding.ActivityDetailBinding;

public class DetailActivity extends AppCompatActivity {

    private ActivityDetailBinding binding;
    private Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
        }
        binding.toolbar.setNavigationOnClickListener(v -> finish());

        book = (Book) getIntent().getSerializableExtra("book");
        if (book != null) {
            displayBookDetails();
        }

        binding.fabFavorite.setOnClickListener(v -> toggleFavorite());
    }

    private void displayBookDetails() {
        binding.tvDetailTitle.setText(book.getTitle());
        binding.tvDetailAuthorYear.setText(book.getAuthor() + " | " + book.getYear());
        binding.tvDetailBlurb.setText(book.getBlurb());
        binding.detailRating.setRating(book.getRating());
        
        updateFavoriteFab();

        Glide.with(this)
                .load(book.getImageUri())
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_menu_report_image)
                .into(binding.ivDetailCover);
    }

    private void toggleFavorite() {
        book.setFavorite(!book.isFavorite());
        BookRepository.getInstance().updateBook(book);
        updateFavoriteFab();
    }

    private void updateFavoriteFab() {
        binding.fabFavorite.setImageResource(book.isFavorite() ? 
                android.R.drawable.btn_star_big_on : android.R.drawable.btn_star_big_off);
    }
}
