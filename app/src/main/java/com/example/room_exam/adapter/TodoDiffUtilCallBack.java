package com.example.room_exam.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.example.room_exam.models.Todo;

import java.util.Objects;

public class TodoDiffUtilCallBack extends DiffUtil.ItemCallback<Todo> {

    @Override
    public boolean areItemsTheSame(@NonNull Todo oldItem, @NonNull Todo newItem) {
        return Objects.equals(oldItem.getId(), newItem.getId());
    }

    @Override
    public boolean areContentsTheSame(@NonNull Todo oldItem, @NonNull Todo newItem) {
        return oldItem.equals(newItem);
    }
}
