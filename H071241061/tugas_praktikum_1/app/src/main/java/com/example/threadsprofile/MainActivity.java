package com.example.threadsprofile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_EDIT_PROFILE = 1;

    TextView tvName, tvUsername, tvBio;
    Button btnEditProfile;
    ImageView ivAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvName         = findViewById(R.id.tvName);
        tvUsername     = findViewById(R.id.tvUsername);
        tvBio          = findViewById(R.id.tvBio);
        btnEditProfile = findViewById(R.id.btnEditProfile);
        ivAvatar       = findViewById(R.id.ivAvatar);

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditProfileActivity.class);
                intent.putExtra("name",     tvName.getText().toString());
                intent.putExtra("username", tvUsername.getText().toString());
                intent.putExtra("bio",      tvBio.getText().toString());
                startActivityForResult(intent, REQUEST_EDIT_PROFILE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_EDIT_PROFILE && resultCode == RESULT_OK && data != null) {

            String newName = data.getStringExtra("name");
            if (newName != null && !newName.isEmpty()) {
                tvName.setText(newName);
            }

            String newUsername = data.getStringExtra("username");
            if (newUsername != null && !newUsername.isEmpty()) {
                tvUsername.setText(newUsername);
            }

            String newBio = data.getStringExtra("bio");
            if (newBio != null) {
                tvBio.setText(newBio);
            }
        }
    }
}