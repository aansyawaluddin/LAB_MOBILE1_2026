package com.example.tp2_mobile;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tp2_mobile.databinding.ItemPostBinding;
import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private final ArrayList<Post> posts;

    public HomeAdapter(ArrayList<Post> posts) {
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPostBinding binding = ItemPostBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.binding.ivProfilePost.setImageResource(post.getProfileImage());
        holder.binding.tvUsernamePost.setText(post.getUsername());
        
        if (post.getPostImageRes() != null) {
            holder.binding.ivPostImage.setImageResource(post.getPostImageRes());
        } else if (post.getPostImageUri() != null) {
            holder.binding.ivPostImage.setImageURI(Uri.parse(post.getPostImageUri()));
        }
        
        holder.binding.tvCaption.setText(post.getUsername() + ": " + post.getCaption());

        View.OnClickListener toProfile = v -> {
            Intent intent = new Intent(v.getContext(), ProfileActivity.class);
            User targetUser = DataSource.feraUser;
            if (!post.getUsername().equals("fera_kaco")) {
                for (User u : DataSource.dummyUsers) {
                    if (u.getUsername().equals(post.getUsername())) {
                        targetUser = u;
                        break;
                    }
                }
            }
            intent.putExtra("user", targetUser);
            v.getContext().startActivity(intent);
        };

        holder.binding.ivProfilePost.setOnClickListener(toProfile);
        holder.binding.tvUsernamePost.setOnClickListener(toProfile);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ItemPostBinding binding;
        ViewHolder(ItemPostBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
