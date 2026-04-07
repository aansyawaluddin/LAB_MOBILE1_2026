package com.example.tp2_mobile;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tp2_mobile.databinding.ItemGridPostBinding;
import java.util.ArrayList;

public class ProfilePostAdapter extends RecyclerView.Adapter<ProfilePostAdapter.ViewHolder> {
    private final ArrayList<Post> posts;

    public ProfilePostAdapter(ArrayList<Post> posts) {
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemGridPostBinding binding = ItemGridPostBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        
        if (post.getPostImageRes() != null) {
            holder.binding.ivGridPost.setImageResource(post.getPostImageRes());
        } else if (post.getPostImageUri() != null) {
            holder.binding.ivGridPost.setImageURI(Uri.parse(post.getPostImageUri()));
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), DetailFeedActivity.class);
            intent.putExtra("post", post);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ItemGridPostBinding binding;
        ViewHolder(ItemGridPostBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
