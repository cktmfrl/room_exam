package com.example.room_exam.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.room_exam.models.Todo;

import java.util.List;

@Dao
public interface TodoDao {
    @Query("SELECT * FROM Todo ORDER BY date DESC")
    List<Todo> getAll();

    @Insert
    void insert(Todo todo);

    @Update
    void update(Todo todo);

    @Delete
    void delete(Todo todo);
}
