package com.nurul.praktikum_3;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class DetailActivity extends AppCompatActivity {

    private Book currentBook;
    private MaterialButton btnLike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Menerima data dari Intent
        currentBook = (Book) getIntent().getSerializableExtra("EXTRA_BOOK");

        // Back button
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        ImageView imgCover = findViewById(R.id.imgDetailCover);
        TextView tvTitle = findViewById(R.id.tvDetailTitle);
        TextView tvAuthor = findViewById(R.id.tvDetailAuthor);
        TextView tvYear = findViewById(R.id.tvDetailYear);
        TextView tvRating = findViewById(R.id.tvDetailRating);
        TextView tvGenre = findViewById(R.id.tvDetailGenre);
        TextView tvBlurb = findViewById(R.id.tvDetailBlurb);
        TextView tvReview = findViewById(R.id.tvDetailReview);
        btnLike = findViewById(R.id.btnLike);

        if (currentBook != null) {
            tvTitle.setText(currentBook.getTitle());
            tvAuthor.setText(currentBook.getAuthor());
            tvYear.setText("Tahun Terbit: " + currentBook.getYear());
            tvRating.setText(String.valueOf(currentBook.getRating()));
            tvGenre.setText(currentBook.getGenre());
            tvBlurb.setText(currentBook.getBlurb());
            tvReview.setText("\"" + currentBook.getReview() + "\"");

            // CEK APAKAH GAMBAR DARI GALERI ATAU DUMMY
            if (currentBook.getCoverUri() != null) {
                imgCover.setImageURI(android.net.Uri.parse(currentBook.getCoverUri()));
            } else {
                imgCover.setImageResource(currentBook.getCoverImage());
            }

            updateLikeButtonStatus();

            btnLike.setOnClickListener(v -> toggleLikeStatus());
        }
    }

    private void toggleLikeStatus() {
        // Cari buku asli di DataProvider dan update statusnya
        for (Book b : DataProvider.bookList) {
            if (b.getId().equals(currentBook.getId())) {
                boolean newStatus = !b.isLiked();
                b.setLiked(newStatus);
                currentBook.setLiked(newStatus); // Update objek lokal juga
                updateLikeButtonStatus();

                String message = newStatus ? "Ditambahkan ke Favorit ❤️" : "Dihapus dari Favorit";
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }

    private void updateLikeButtonStatus() {
        if (currentBook.isLiked()) {
            btnLike.setText(getString(R.string.remove_from_favorites));
            btnLike.setIconResource(R.drawable.ic_heart);
            btnLike.setBackgroundTintList(getResources().getColorStateList(R.color.like_inactive));
        } else {
            btnLike.setText(getString(R.string.add_to_favorites));
            btnLike.setIconResource(R.drawable.ic_heart_outline);
            btnLike.setBackgroundTintList(getResources().getColorStateList(R.color.like_active));
        }
    }
}
