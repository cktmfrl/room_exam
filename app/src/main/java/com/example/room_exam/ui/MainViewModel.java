package com.example.room_exam.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.room_exam.data.AppDatabase;
import com.example.room_exam.data.Todo;

import java.util.Calendar;
import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private final AppDatabase db;
    public Todo seletedTodo = null;

    public MutableLiveData<List<Todo>> _items = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
        db = Room.databaseBuilder(application, AppDatabase.class, "todo-db")
                .allowMainThreadQueries()
                .build();// 전역으로

        _items.setValue(db.todoDao().getAll());
    }

    void addTodo(String text) {
        Long date = Calendar.getInstance().getTimeInMillis();
        db.todoDao().insert(new Todo(text, date));
    }

    void addTodo(String text, Long date) {
        db.todoDao().insert(new Todo(text, date));
    }

    void deleteTodo(int id) {
        for (Todo item : _items.getValue()) {
            if (item.getDate() == id) {
                db.todoDao().delete(item);
                break;
            }
        }

        seletedTodo = null;
    }

    void updateTodo(String text) {
        if (seletedTodo != null) {
            seletedTodo.setTitle(text);
            seletedTodo.setDate(Calendar.getInstance().getTimeInMillis());
        }

        db.todoDao().update(seletedTodo);
        seletedTodo = null;
    }

}
