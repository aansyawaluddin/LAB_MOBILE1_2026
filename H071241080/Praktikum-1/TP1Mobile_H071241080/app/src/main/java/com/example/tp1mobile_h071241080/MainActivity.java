package com.example.tp1mobile_h071241080;

import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private TextView tvFullName, tvHeadline, tvUniversity, tvLocation, tvConnections;
    private CircleImageView profileImage;
    private Button btnEditProfile;

    private final ActivityResultLauncher<Intent> editProfileLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Intent data = result.getData();

                    String firstName = data.getStringExtra("first_name");
                    String lastName = data.getStringExtra("last_name");
                    String headline = data.getStringExtra("headline");
                    String school = data.getStringExtra("school");
                    String country = data.getStringExtra("country");
                    String city = data.getStringExtra("city");
                    String imageUri = data.getStringExtra("profileImage");

                    String fullName = firstName + " " + lastName;
                    tvFullName.setText(fullName);

                    if (headline != null) tvHeadline.setText(headline);
                    if (school != null) tvUniversity.setText(school);

                    String location = city + ", " + country;
                    tvLocation.setText(location);

                    if (imageUri != null && !imageUri.isEmpty()) {
                        profileImage.setImageURI(Uri.parse(imageUri));
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        btnEditProfile.setOnClickListener(v -> openEditProfile());
    }

    private void initViews() {
        tvFullName = findViewById(R.id.tv_full_name);
        tvHeadline = findViewById(R.id.tv_headline);
        tvUniversity = findViewById(R.id.tv_university);
        tvLocation = findViewById(R.id.tv_location);
        tvConnections = findViewById(R.id.tv_connections);
        profileImage = findViewById(R.id.profile_image);
        btnEditProfile = findViewById(R.id.btn_edit_profile);
    }

    private void openEditProfile() {
        Intent intent = new Intent(MainActivity.this, EditProfileActivity.class);

        String fullName = tvFullName.getText().toString();
        String[] names = fullName.split(" ", 2);
        String firstName = names[0];
        String lastName = names.length > 1 ? names[1] : "";

        intent.putExtra("first_name", firstName);
        intent.putExtra("last_name", lastName);
        intent.putExtra("headline", tvHeadline.getText().toString());
        intent.putExtra("school", tvUniversity.getText().toString());

        String location = tvLocation.getText().toString();
        String[] locationParts = location.split(", ", 2);
        String city = locationParts[0];
        String country = locationParts.length > 1 ? locationParts[1] : "";

        intent.putExtra("city", city);
        intent.putExtra("country", country);

        editProfileLauncher.launch(intent);
    }
}