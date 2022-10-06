package com.example.room_exam.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.room_exam.data.AppDatabase;
import com.example.room_exam.data.Todo;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private final AppDatabase db;
    public Todo seletedTodo = null;

    public MutableLiveData<List<Todo>> todoLiveData = new MutableLiveData<>();

    private void setTodoLiveData() {
        todoLiveData.setValue(db.todoDao().getAll());
        seletedTodo = null;
    }

    public MainViewModel(@NonNull Application application) {
        super(application);
        db = Room.databaseBuilder(application, AppDatabase.class, "todo-db")
                .allowMainThreadQueries()
                .build();// 전역으로

        setTodoLiveData();
    }

    void addTodo(String text, Long date) {
        db.todoDao().insert(new Todo(text, date));
        setTodoLiveData();
    }

    void deleteTodo(long id) {
        for (Todo item : todoLiveData.getValue()) {
            if (item.getId() == id) {
                db.todoDao().delete(item);
                break;
            }
        }
        setTodoLiveData();
    }

    void updateTodo(String text, Long date) {
        if (seletedTodo != null) {
            seletedTodo.setTitle(text);
            seletedTodo.setDate(date);
        }

        db.todoDao().update(seletedTodo);
        setTodoLiveData();
    }

}
