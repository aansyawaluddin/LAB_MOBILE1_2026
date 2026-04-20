package com.nurul.praktikum_3;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private List<Book> bookList;
    private final OnBookClickListener listener;

    public interface OnBookClickListener {
        void onBookClick(Book book);
    }

    public BookAdapter(List<Book> bookList, OnBookClickListener listener) {
        this.bookList = bookList;
        this.listener = listener;
    }

    public void updateData(List<Book> newList) {
        this.bookList = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = bookList.get(position);
        holder.tvTitle.setText(book.getTitle());
        holder.tvAuthor.setText(book.getAuthor());
        holder.tvRating.setText(String.valueOf(book.getRating()));

        // Set genre badge
        holder.tvGenre.setText(book.getGenre());
        applyGenreStyle(holder.tvGenre, book.getGenre());

        if (book.getCoverUri() != null) {
            holder.imgCover.setImageURI(Uri.parse(book.getCoverUri()));
        } else {
            holder.imgCover.setImageResource(book.getCoverImage());
        }

        holder.itemView.setOnClickListener(v -> listener.onBookClick(book));
    }

    private void applyGenreStyle(TextView tvGenre, String genre) {
        int bgColor, textColor;
        switch (genre) {
            case "Fiction":
                bgColor = R.color.genre_fiction;
                textColor = R.color.genre_fiction_text;
                break;
            case "Dystopian":
                bgColor = R.color.genre_dystopian;
                textColor = R.color.genre_dystopian_text;
                break;
            case "Classic":
                bgColor = R.color.genre_classic;
                textColor = R.color.genre_classic_text;
                break;
            case "Romance":
                bgColor = R.color.genre_romance;
                textColor = R.color.genre_romance_text;
                break;
            case "Sci-Fi":
                bgColor = R.color.genre_scifi;
                textColor = R.color.genre_scifi_text;
                break;
            default:
                bgColor = R.color.genre_default;
                textColor = R.color.genre_default_text;
                break;
        }
        tvGenre.setBackgroundTintList(tvGenre.getContext().getResources().getColorStateList(bgColor));
        tvGenre.setTextColor(tvGenre.getContext().getResources().getColor(textColor));
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    static class BookViewHolder extends RecyclerView.ViewHolder {
        ImageView imgCover;
        TextView tvTitle, tvAuthor, tvRating, tvGenre;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCover = itemView.findViewById(R.id.imgItemCover);
            tvTitle = itemView.findViewById(R.id.tvItemTitle);
            tvAuthor = itemView.findViewById(R.id.tvItemAuthor);
            tvRating = itemView.findViewById(R.id.tvItemRating);
            tvGenre = itemView.findViewById(R.id.tvItemGenre);
        }
    }
}
