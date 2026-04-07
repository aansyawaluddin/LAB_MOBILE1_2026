package com.example.tp2_mobile;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tp2_mobile.databinding.ItemHighlightBinding;
import java.util.ArrayList;

public class HighlightAdapter extends RecyclerView.Adapter<HighlightAdapter.ViewHolder> {
    private final ArrayList<Integer> highlights;

    public HighlightAdapter(ArrayList<Integer> highlights) {
        this.highlights = highlights;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemHighlightBinding binding = ItemHighlightBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int resId = highlights.get(position);
        holder.binding.ivHighlight.setImageResource(resId);
        holder.binding.tvHighlightName.setText("Highlight " + (position + 1));

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), DetailStoryActivity.class);
            intent.putExtra("image", resId);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return highlights.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ItemHighlightBinding binding;
        ViewHolder(ItemHighlightBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
