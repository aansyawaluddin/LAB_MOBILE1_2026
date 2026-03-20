package com.example.threadsprofile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class EditProfileActivity extends AppCompatActivity {

    EditText etName, etUsername, etBio;
    Button btnDone, btnCancel, btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        etName     = findViewById(R.id.etName);
        etUsername = findViewById(R.id.etUsername);
        etBio      = findViewById(R.id.etBio);
        btnDone    = findViewById(R.id.btnDone);
        btnCancel  = findViewById(R.id.btnCancel);
        btnSave    = findViewById(R.id.btnSave);

        Intent incoming        = getIntent();
        String currentName     = incoming.getStringExtra("name");
        String currentUsername = incoming.getStringExtra("username");
        String currentBio      = incoming.getStringExtra("bio");

        if (currentName != null)     etName.setText(currentName);
        if (currentUsername != null) etUsername.setText(currentUsername);
        if (currentBio != null)      etBio.setText(currentBio);

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAndReturn();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAndReturn();
            }
        });
    }

    private void saveAndReturn() {
        String newName     = etName.getText().toString().trim();
        String newUsername = etUsername.getText().toString().trim();
        String newBio      = etBio.getText().toString().trim();

        Intent resultIntent = new Intent();
        resultIntent.putExtra("name",     newName);
        resultIntent.putExtra("username", newUsername);
        resultIntent.putExtra("bio",      newBio);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}