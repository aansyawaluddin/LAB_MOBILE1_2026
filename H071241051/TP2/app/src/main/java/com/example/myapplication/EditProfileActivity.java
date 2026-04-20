package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.imageview.ShapeableImageView;

public class EditProfileActivity extends AppCompatActivity {

    private EditText etName, etUsername, etBio;
    private ImageView btnSave, btnBack;
    private ShapeableImageView ivEditProfileImage;
    private TextView tvEditPhoto;
    private Uri selectedImageUri = null;

    private final ActivityResultLauncher<String> getContent = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null) {
                    selectedImageUri = uri;
                    ivEditProfileImage.setImageURI(uri);
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        etName = findViewById(R.id.etName);
        etUsername = findViewById(R.id.etUsername);
        etBio = findViewById(R.id.etBio);
        btnSave = findViewById(R.id.btnSave);
        btnBack = findViewById(R.id.btnBack);
        ivEditProfileImage = findViewById(R.id.ivEditProfileImage);
        tvEditPhoto = findViewById(R.id.tvEditPhoto);

        User user = DataRepository.getInstance().getCurrentUser();
        etName.setText(user.getName());
        etUsername.setText(user.getUsername());
        etBio.setText(user.getBio());
        if (user.getProfileImageUri() != null) {
            selectedImageUri = Uri.parse(user.getProfileImageUri());
            ivEditProfileImage.setImageURI(selectedImageUri);
        }

        tvEditPhoto.setOnClickListener(v -> getContent.launch("image/*"));
        ivEditProfileImage.setOnClickListener(v -> getContent.launch("image/*"));

        btnBack.setOnClickListener(v -> finish());

        btnSave.setOnClickListener(v -> {
            user.setName(etName.getText().toString());
            user.setUsername(etUsername.getText().toString());
            user.setBio(etBio.getText().toString());
            if (selectedImageUri != null) {
                user.setProfileImageUri(selectedImageUri.toString());
            }
            setResult(RESULT_OK);
            finish();
        });
    }
}