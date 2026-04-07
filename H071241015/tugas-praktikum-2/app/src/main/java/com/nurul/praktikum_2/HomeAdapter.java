package com.nurul.praktikum_2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private final ArrayList<Post> posts;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Post post);
    }

    public HomeAdapter(ArrayList<Post> posts, OnItemClickListener listener) {
        this.posts = posts;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feed, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.tvUsername.setText(post.getUsername());
        holder.tvCaption.setText(post.getCaption());
        holder.ivProfilePic.setImageResource(post.getUserProfileImage());
        holder.ivPostImage.setImageResource(post.getPostImage());

        // Hanya bagian header (foto profil & username) yang bisa diklik untuk ke profil
        holder.headerProfile.setOnClickListener(v -> listener.onItemClick(post));
        
        // Klik pada gambar postingan atau area lainnya tidak akan memicu navigasi ke profil
        holder.itemView.setOnClickListener(null);
    }

    @Override
    public int getItemCount() { return posts.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        View headerProfile;
        ImageView ivProfilePic, ivPostImage;
        TextView tvUsername, tvCaption;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            headerProfile = itemView.findViewById(R.id.headerProfile);
            ivProfilePic = itemView.findViewById(R.id.ivProfilePic);
            ivPostImage = itemView.findViewById(R.id.ivPostImage);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvCaption = itemView.findViewById(R.id.tvCaption);
        }
    }
}
