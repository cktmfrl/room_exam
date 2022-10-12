package com.example.room_exam.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.room_exam.R;
import com.example.room_exam.adapter.TodoDiffUtilCallBack;
import com.example.room_exam.adapter.TodoListAdapter;
import com.example.room_exam.databinding.FragmentTodoListBinding;
import com.example.room_exam.models.Todo;

public class TodoListFragment extends Fragment {
    private static final String TAG = "FirstFragment";
    private MainViewModel viewModel;

    private FragmentTodoListBinding binding;
    private TodoListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTodoListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new TodoListAdapter(new TodoDiffUtilCallBack(), todo -> {
            viewModel.seletedTodo = todo;
            NavHostFragment.findNavController(TodoListFragment.this).navigate(R.id.action_ListFragment_to_EditFragment);
        });

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        viewModel.todoLiveData.observe(getViewLifecycleOwner(), todos -> {
            Log.d(TAG, "onViewCreated: todos = " + todos);
            adapter.submitList(todos);
        });

        // 추가
        binding.addFab.setOnClickListener(v -> {
            viewModel.seletedTodo = null;
            NavHostFragment.findNavController(TodoListFragment.this).navigate(R.id.action_ListFragment_to_EditFragment);
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