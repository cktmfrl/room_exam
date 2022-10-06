package com.example.room_exam.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.room_exam.R;
import com.example.room_exam.adapter.TodoDiffUtilCallBack;
import com.example.room_exam.adapter.TodoListAdapter;
import com.example.room_exam.data.Todo;
import com.example.room_exam.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {
    private MainViewModel viewModel;

    private FragmentFirstBinding binding;
    private TodoListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new TodoListAdapter(new TodoDiffUtilCallBack(), todo -> {
            viewModel.seletedTodo = todo;
            NavHostFragment.findNavController(FirstFragment.this).navigate(R.id.action_FirstFragment_to_SecondFragment);
        });

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        viewModel._items.observe(getViewLifecycleOwner(), todos -> adapter.submitList(todos));

        binding.addFab.setOnClickListener(v -> {
            NavHostFragment.findNavController(FirstFragment.this).navigate(R.id.action_FirstFragment_to_SecondFragment);
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public interface ItemClickListener {
        void onClick(Todo todo);
    }

}