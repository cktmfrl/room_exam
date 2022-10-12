package com.example.room_exam.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.room_exam.MainActivity;
import com.example.room_exam.R;
import com.example.room_exam.databinding.FragmentEditTodoBinding;
import com.example.room_exam.models.Todo;

import java.util.Calendar;

public class EditTodoFragment extends Fragment {
    private MainViewModel viewModel;
    private FragmentEditTodoBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditTodoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        Calendar calendar = Calendar.getInstance();
        Todo item = viewModel.seletedTodo;

        // 선택된 할 일이 없을 때
        if (item == null) {
            ((MainActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.add_label));
            binding.deleteFab.setVisibility(View.GONE);
        } else {
            ((MainActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.edit_label));
            binding.todoEditText.setText(item.getTitle());
            binding.calendarView.setDate(item.getDate());
        }

        binding.calendarView.setOnDateChangeListener((calendarView, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            if (item != null) {
                item.setDate(calendar.getTime().getTime());
            }
        });

        binding.doneFab.setOnClickListener(v -> {
            String inputText = binding.todoEditText.getText().toString();
            if (inputText.isEmpty()) {
                Toast.makeText(getActivity(), getString(R.string.message), Toast.LENGTH_SHORT).show();
                return;
            }
            if (item != null) {
                viewModel.updateTodo(inputText, calendar.getTime().getTime());
            } else {
                viewModel.addTodo(inputText, calendar.getTime().getTime());
            }
            NavHostFragment.findNavController(EditTodoFragment.this).popBackStack();
        });

        binding.deleteFab.setOnClickListener(v -> {
            if (item != null) {
                viewModel.deleteTodo(item.getId());
            }
            NavHostFragment.findNavController(EditTodoFragment.this).popBackStack();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}