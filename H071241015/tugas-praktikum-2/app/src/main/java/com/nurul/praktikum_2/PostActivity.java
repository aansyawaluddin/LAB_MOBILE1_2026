package com.nurul.praktikum_2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class PostActivity extends AppCompatActivity {
    private ImageView ivSelectedImage;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        ivSelectedImage = findViewById(R.id.ivSelectedImage);
        EditText etCaption = findViewById(R.id.etCaption);
        Button btnUpload = findViewById(R.id.btnUpload);

        ActivityResultLauncher<Intent> galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        imageUri = result.getData().getData();
                        // Penting: Ambil izin akses permanen agar gambar bisa dibaca di activity lain
                        final int takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION;
                        getContentResolver().takePersistableUriPermission(imageUri, takeFlags);
                        
                        ivSelectedImage.setImageURI(imageUri);
                    }
                }
        );

        ivSelectedImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            galleryLauncher.launch(intent);
        });

        btnUpload.setOnClickListener(v -> {
            String caption = etCaption.getText().toString().trim();
            if (caption.isEmpty()) {
                Toast.makeText(this, "Caption tidak boleh kosong", Toast.LENGTH_SHORT).show();
                return;
            }

            Post newPost;
            if (imageUri != null) {
                newPost = new Post("Nurul Marisa", R.drawable.satu, imageUri.toString(), caption);
            } else {
                newPost = new Post("Nurul Marisa", R.drawable.satu, android.R.drawable.ic_menu_gallery, caption);
            }
            
            DataSource.profilePosts.add(0, newPost);

            Toast.makeText(this, "Berhasil diupload!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
