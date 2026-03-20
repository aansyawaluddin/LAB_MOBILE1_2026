package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.imageview.ShapeableImageView;

public class MainActivity extends AppCompatActivity {

    private TextView tvUsernameTop, tvName, tvBio;
    private MaterialCardView btnEditProfile;
    private ShapeableImageView ivProfileImage, ivProfileBottom;
    private Uri currentProfileImageUri = null;

    private final ActivityResultLauncher<Intent> editProfileLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Intent data = result.getData();
                    String name = data.getStringExtra("name");
                    String username = data.getStringExtra("username");
                    String bio = data.getStringExtra("bio");
                    String imageUriString = data.getStringExtra("imageUri");

                    tvName.setText(name);
                    tvUsernameTop.setText(username);
                    tvBio.setText(bio);
                    
                    if (imageUriString != null) {
                        currentProfileImageUri = Uri.parse(imageUriString);
                        ivProfileImage.setImageURI(currentProfileImageUri);
                        ivProfileBottom.setImageURI(currentProfileImageUri);
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvUsernameTop = findViewById(R.id.tvUsernameTop);
        tvName = findViewById(R.id.tvName);
        tvBio = findViewById(R.id.tvBio);
        btnEditProfile = findViewById(R.id.btnEditProfile);
        ivProfileImage = findViewById(R.id.ivProfileImage);
        ivProfileBottom = findViewById(R.id.ivProfileBottom);

        btnEditProfile.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EditProfileActivity.class);
            intent.putExtra("name", tvName.getText().toString());
            intent.putExtra("username", tvUsernameTop.getText().toString());
            intent.putExtra("bio", tvBio.getText().toString());
            if (currentProfileImageUri != null) {
                intent.putExtra("imageUri", currentProfileImageUri.toString());
            }
            editProfileLauncher.launch(intent);
        });
    }
}