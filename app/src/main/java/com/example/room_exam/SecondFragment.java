package com.example.room_exam;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.room_exam.databinding.FragmentSecondBinding;


public class SecondFragment extends Fragment {
    private MainViewModel viewModel;

    private FragmentSecondBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        Todo item = viewModel.seletedTodo;
        if (item != null) {
            Log.d("SecondFragmet", "onViewCreated: seletedTodo = " + item);
            binding.todoEditText.setText(item.getTitle());
            binding.calendarView.setDate(item.getDate());
        }

        // 등록
        binding.doneFab.setOnClickListener(v -> {
            String inputText = binding.todoEditText.getText().toString();
            if (!inputText.isEmpty()) {
                if (viewModel.seletedTodo != null) {
                    viewModel.updateTodo(inputText);
                } else {
                    Long date = binding.calendarView.getDate();
                    Log.d("SecondFragmet", "onViewCreated: date = " + date);
                    viewModel.addTodo(inputText, date);
                }
                NavHostFragment.findNavController(SecondFragment.this).popBackStack();
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}