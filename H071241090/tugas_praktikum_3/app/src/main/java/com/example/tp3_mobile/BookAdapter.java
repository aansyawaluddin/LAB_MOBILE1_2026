package com.example.tp3_mobile;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.tp3_mobile.databinding.ItemBookBinding;
import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private List<Book> books;
    private final OnBookClickListener listener;

    public interface OnBookClickListener {
        void onBookClick(Book book);
        void onFavoriteClick(Book book);
    }

    public BookAdapter(List<Book> books, OnBookClickListener listener) {
        this.books = new ArrayList<>(books);
        this.listener = listener;
    }

    public void setBooks(List<Book> newBooks) {
        this.books = new ArrayList<>(newBooks);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBookBinding binding = ItemBookBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new BookViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = books.get(position);
        holder.bind(book, listener);
        holder.itemView.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.item_animation_fall_down));
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    static class BookViewHolder extends RecyclerView.ViewHolder {
        private final ItemBookBinding binding;

        public BookViewHolder(ItemBookBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Book book, OnBookClickListener listener) {
            binding.bookTitle.setText(book.getTitle());
            binding.bookAuthor.setText(book.getAuthor());
            binding.bookYear.setText(String.valueOf(book.getYear()));
            binding.bookRating.setRating(book.getRating());
            
            Glide.with(itemView.getContext())
                    .load(book.getImageUri())
                    .placeholder(android.R.drawable.ic_menu_gallery)
                    .error(android.R.drawable.ic_menu_report_image)
                    .into(binding.bookCover);

            binding.btnFavorite.setImageResource(book.isFavorite() ? android.R.drawable.btn_star_big_on : android.R.drawable.btn_star_big_off);

            itemView.setOnClickListener(v -> listener.onBookClick(book));
            binding.btnFavorite.setOnClickListener(v -> {
                book.setFavorite(!book.isFavorite());
                binding.btnFavorite.setImageResource(book.isFavorite() ? android.R.drawable.btn_star_big_on : android.R.drawable.btn_star_big_off);
                listener.onFavoriteClick(book);
            });
        }
    }
}
