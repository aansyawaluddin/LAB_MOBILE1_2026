package com.example.tp4_mobile;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.example.tp4_mobile.databinding.FragmentAddBookBinding;
import java.util.UUID;

public class AddBookFragment extends Fragment {

    private FragmentAddBookBinding binding;
    private Uri selectedImageUri;

    private final ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    selectedImageUri = result.getData().getData();
                    binding.ivCoverPreview.setImageURI(selectedImageUri);
                    binding.ivCoverPreview.setVisibility(View.VISIBLE);
                }
            }
    );

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAddBookBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnSelectImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            imagePickerLauncher.launch(intent);
        });

        binding.btnSubmit.setOnClickListener(v -> validateAndSubmit());
    }

    private void validateAndSubmit() {
        String title = binding.etTitle.getText().toString().trim();
        String author = binding.etAuthor.getText().toString().trim();
        String yearStr = binding.etYear.getText().toString().trim();
        String genre = binding.etGenre.getText().toString().trim();
        String blurb = binding.etBlurb.getText().toString().trim();
        float rating = binding.rbRating.getRating();

        if (title.isEmpty() || author.isEmpty() || yearStr.isEmpty() || genre.isEmpty() || blurb.isEmpty()) {
            Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int year;
        try {
            year = Integer.parseInt(yearStr);
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Invalid year", Toast.LENGTH_SHORT).show();
            return;
        }

        String imageUriStr = (selectedImageUri != null) ? selectedImageUri.toString() : "https://via.placeholder.com/150";

        Book newBook = new Book(
                UUID.randomUUID().toString(),
                title,
                author,
                year,
                blurb,
                imageUriStr,
                false,
                rating,
                genre
        );

        BookRepository.getInstance().addBook(newBook);
        Toast.makeText(getContext(), "Book added successfully!", Toast.LENGTH_SHORT).show();
        
        // Navigate back to Home
        Navigation.findNavController(requireView()).navigate(R.id.navigation_home);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
