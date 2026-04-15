package com.example.tp2_mobile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.example.tp2_mobile.databinding.ActivityAddPostBinding;

public class AddPostActivity extends AppCompatActivity {
    private ActivityAddPostBinding binding;
    private Uri selectedImageUri;

    private final ActivityResultLauncher<Intent> galleryLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    selectedImageUri = result.getData().getData();
                    if (selectedImageUri != null) {
                        try {
                            // Take persistable permission to access the URI later
                            getContentResolver().takePersistableUriPermission(
                                    selectedImageUri,
                                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                            );
                            binding.ivPreview.setImageURI(selectedImageUri);
                            binding.ivPreview.setImageTintList(null);
                        } catch (SecurityException e) {
                            // If we can't take persistable permission, just show it for now
                            binding.ivPreview.setImageURI(selectedImageUri);
                            binding.ivPreview.setImageTintList(null);
                        }
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddPostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.ivPreview.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            galleryLauncher.launch(intent);
        });

        binding.btnUpload.setOnClickListener(v -> {
            String caption = binding.etCaption.getText().toString();

            if (selectedImageUri == null) {
                Toast.makeText(this, "Please select an image first", Toast.LENGTH_SHORT).show();
                return;
            }

            if (caption.isEmpty()) {
                Toast.makeText(this, "Please enter a caption", Toast.LENGTH_SHORT).show();
                return;
            }

            // Create new post
            Post newPost = new Post("fera", R.drawable.fera_profile, selectedImageUri.toString(), caption);

            // Add to the global data source
            DataSource.feraUser.getPosts().add(0, newPost);

            Toast.makeText(this, "Post uploaded!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
