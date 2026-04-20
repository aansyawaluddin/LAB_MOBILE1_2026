package com.nurul.praktikum_3;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.material.button.MaterialButton;

public class AddBookFragment extends Fragment {

    private ImageView imgPreviewCover;
    private Uri selectedImageUri = null;

    // Peluncur untuk membuka galeri gambar
    private final ActivityResultLauncher<String> selectImageLauncher = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null) {
                    selectedImageUri = uri;
                    imgPreviewCover.setImageURI(uri); // Tampilkan preview
                }
            }
    );

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_book, container, false);

        imgPreviewCover = view.findViewById(R.id.imgPreviewCover);
        MaterialButton btnPickImage = view.findViewById(R.id.btnPickImage);

        EditText etTitle = view.findViewById(R.id.etTitle);
        EditText etAuthor = view.findViewById(R.id.etAuthor);
        EditText etYear = view.findViewById(R.id.etYear);
        EditText etBlurb = view.findViewById(R.id.etBlurb);
        MaterialButton btnSaveBook = view.findViewById(R.id.btnSaveBook);

        // Buka galeri saat tombol diklik
        btnPickImage.setOnClickListener(v -> selectImageLauncher.launch("image/*"));

        btnSaveBook.setOnClickListener(v -> {
            String title = etTitle.getText().toString().trim();
            String author = etAuthor.getText().toString().trim();
            String year = etYear.getText().toString().trim();
            String blurb = etBlurb.getText().toString().trim();

            // Validasi input
            if (title.isEmpty()) {
                etTitle.setError("Judul wajib diisi!");
                etTitle.requestFocus();
                return;
            }
            if (author.isEmpty()) {
                etAuthor.setError("Penulis wajib diisi!");
                etAuthor.requestFocus();
                return;
            }

            // Buat ID unik sederhana
            String newId = String.valueOf(System.currentTimeMillis());

            // Buat objek buku baru
            Book newBook = new Book(newId, title, author, year, blurb, R.drawable.cover_dummy, 0.0, "Umum", "Belum ada review");

            // Jika ada gambar dari galeri, simpan URI-nya ke dalam objek buku
            if (selectedImageUri != null) {
                newBook.setCoverUri(selectedImageUri.toString());
            }

            // Masukkan di posisi awal agar muncul paling atas (terbaru)
            DataProvider.bookList.add(0, newBook);
            Toast.makeText(getContext(), "Buku berhasil ditambahkan! 📚", Toast.LENGTH_SHORT).show();

            // Bersihkan form setelah disimpan
            etTitle.setText("");
            etAuthor.setText("");
            etYear.setText("");
            etBlurb.setText("");
            imgPreviewCover.setImageDrawable(null);
            imgPreviewCover.setBackgroundColor(0xFFE8EAF6); // Reset background
            selectedImageUri = null;
        });

        return view;
    }
}