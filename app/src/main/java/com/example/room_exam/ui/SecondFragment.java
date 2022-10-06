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
import com.example.room_exam.data.Todo;
import com.example.room_exam.databinding.FragmentSecondBinding;

import java.util.Calendar;

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

        Calendar cal = Calendar.getInstance();
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
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, month);
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        });

        // 등록
        binding.doneFab.setOnClickListener(v -> {
            String inputText = binding.todoEditText.getText().toString();
            if (inputText.isEmpty()) {
                Toast.makeText(getActivity(), "할 일을 입력해주세요.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (item != null) {
                viewModel.updateTodo(inputText, cal.getTime().getTime());
            } else {
                viewModel.addTodo(inputText, cal.getTime().getTime());
            }
            NavHostFragment.findNavController(SecondFragment.this).popBackStack();
        });

        // 삭제
        binding.deleteFab.setOnClickListener(v -> {
            if (item != null) {
                viewModel.deleteTodo(item.getId());
            }
            NavHostFragment.findNavController(SecondFragment.this).popBackStack();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}