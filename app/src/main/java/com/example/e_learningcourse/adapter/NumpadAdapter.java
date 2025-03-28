package com.example.e_learningcourse.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_learningcourse.R;
import com.example.e_learningcourse.model.NumpadKey;

import java.util.List;

public class NumpadAdapter extends RecyclerView.Adapter<NumpadAdapter.KeyViewHolder> {

    private final List<NumpadKey> keys;
    private final OnKeyPressListener listener;

    public interface OnKeyPressListener {
        void onKeyPress(NumpadKey key);
    }

    public NumpadAdapter(List<NumpadKey> keys, OnKeyPressListener listener) {
        this.keys = keys;
        this.listener = listener;
    }

    @NonNull
    @Override
    public KeyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_numpad_key, parent, false);
        return new KeyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KeyViewHolder holder, int position) {
        NumpadKey key = keys.get(position);
        holder.btnKey.setText(key.getLabel());

        holder.btnKey.setOnClickListener(v -> listener.onKeyPress(key));
    }

    @Override
    public int getItemCount() {
        return keys.size();
    }

    static class KeyViewHolder extends RecyclerView.ViewHolder {
        AppCompatButton btnKey;

        public KeyViewHolder(@NonNull View itemView) {
            super(itemView);
            btnKey = itemView.findViewById(R.id.btn_key);
        }
    }
}