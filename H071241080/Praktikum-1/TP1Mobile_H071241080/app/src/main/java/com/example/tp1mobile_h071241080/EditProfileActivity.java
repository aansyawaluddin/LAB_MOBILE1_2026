package com.example.tp1mobile_h071241080;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity {

    private EditText etFirstName, etLastName, etHeadline, etSchool, etCountry, etCity;
    private CircleImageView profileImage;
    private Button btnChangePhoto, btnSave, btnCancel;
    private Uri selectedImageUri;

    private final ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    selectedImageUri = result.getData().getData();
                    profileImage.setImageURI(selectedImageUri);
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        initViews();
        loadCurrentData();
        setupListeners();
    }

    private void initViews() {
        etFirstName = findViewById(R.id.et_first_name);
        etLastName = findViewById(R.id.et_last_name);
        etHeadline = findViewById(R.id.et_headline);
        etSchool = findViewById(R.id.et_school);
        etCountry = findViewById(R.id.et_country);
        etCity = findViewById(R.id.et_city);
        profileImage = findViewById(R.id.edit_profile_image);
        btnChangePhoto = findViewById(R.id.btn_change_photo);
        btnSave = findViewById(R.id.btn_save);
        btnCancel = findViewById(R.id.btn_cancel);
    }

    private void loadCurrentData() {
        Intent intent = getIntent();
        if (intent != null) {
            etFirstName.setText(intent.getStringExtra("first_name"));
            etLastName.setText(intent.getStringExtra("last_name"));
            etHeadline.setText(intent.getStringExtra("headline"));
            etSchool.setText(intent.getStringExtra("school"));
            etCountry.setText(intent.getStringExtra("country"));
            etCity.setText(intent.getStringExtra("city"));
        }
    }

    private void setupListeners() {
        btnChangePhoto.setOnClickListener(v -> openImagePicker());
        btnSave.setOnClickListener(v -> saveProfile());
        btnCancel.setOnClickListener(v -> finish());
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        imagePickerLauncher.launch(intent);
    }

    private void saveProfile() {
        if (etFirstName.getText().toString().isEmpty() ||
                etLastName.getText().toString().isEmpty()) {
            Toast.makeText(this, "First name and Last name are required", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent resultIntent = new Intent();
        resultIntent.putExtra("first_name", etFirstName.getText().toString());
        resultIntent.putExtra("last_name", etLastName.getText().toString());
        resultIntent.putExtra("headline", etHeadline.getText().toString());
        resultIntent.putExtra("school", etSchool.getText().toString());
        resultIntent.putExtra("country", etCountry.getText().toString());
        resultIntent.putExtra("city", etCity.getText().toString());

        if (selectedImageUri != null) {
            resultIntent.putExtra("profileImage", selectedImageUri.toString());
        }

        setResult(RESULT_OK, resultIntent);
        finish();
    }
}