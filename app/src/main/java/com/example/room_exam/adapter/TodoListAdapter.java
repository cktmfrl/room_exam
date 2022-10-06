package com.example.room_exam.adapter;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.room_exam.ui.FirstFragment;
import com.example.room_exam.data.Todo;
import com.example.room_exam.databinding.ItemTodoBinding;

public class TodoListAdapter extends ListAdapter<Todo, TodoListAdapter.TodoViewHolder> {
    private ItemTodoBinding binding;
    private FirstFragment.ItemClickListener callback;

    public TodoListAdapter(@NonNull DiffUtil.ItemCallback diffCallback, FirstFragment.ItemClickListener callback) {
        super(diffCallback);
        this.callback = callback;
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemTodoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new TodoViewHolder(binding.getRoot(), callback);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        Todo item = getItem(position);
        holder.bind(item);
        holder.setOnClickListener(item);
    }

    class TodoViewHolder extends RecyclerView.ViewHolder {

        private FirstFragment.ItemClickListener listener;

        public TodoViewHolder(@NonNull View itemView, FirstFragment.ItemClickListener listener) {
            super(itemView);
            this.listener = listener;
        }

        public void bind(Todo todo) {
            binding.titleTextView.setText(todo.getTitle());
            binding.dateTextView.setText(DateFormat.format("yyyy/MM/dd", todo.getDate()));
        }

        public void setOnClickListener(Todo todo) {
            binding.getRoot().setOnClickListener(v -> listener.onClick(todo));
        }
    }

}
