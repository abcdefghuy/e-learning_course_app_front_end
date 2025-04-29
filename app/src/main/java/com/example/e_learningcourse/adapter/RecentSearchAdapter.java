package com.example.e_learningcourse.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_learningcourse.R;

import java.util.ArrayList;
import java.util.List;

public class RecentSearchAdapter extends RecyclerView.Adapter<RecentSearchAdapter.ViewHolder> {
    private List<String> searches;
    private OnItemRemoveListener removeListener;

    public interface OnItemRemoveListener {
        void onItemRemove(int position);
    }
    public interface OnItemClickListener {
        void onItemClick(String keyword);
    }
    private OnItemClickListener itemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public RecentSearchAdapter() {
        this.searches = new ArrayList<>();
    }

    public void setOnItemRemoveListener(OnItemRemoveListener listener) {
        this.removeListener = listener;
    }

    public void setSearches(List<String> searches) {
        this.searches = searches;
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        if (position >= 0 && position < searches.size()) {
            searches.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, searches.size());
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recent_search, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String search = searches.get(position);
        holder.bind(search, position);
    }

    @Override
    public int getItemCount() {
        return searches.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvSearchText;
        private final ImageButton btnRemove;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSearchText = itemView.findViewById(R.id.tvSearchText);
            btnRemove = itemView.findViewById(R.id.btnRemove);
        }

        public void bind(String search, final int position) {
            tvSearchText.setText(search);
            btnRemove.setOnClickListener(v -> {
                if (removeListener != null) {
                    removeListener.onItemRemove(position);
                }
            });
            //search lai ket qua cu
            itemView.setOnClickListener(v -> {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(search);
                }
            });
        }
    }
} 