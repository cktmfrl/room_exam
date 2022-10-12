package com.example.room_exam.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.room_exam.models.Todo;

@Database(entities = {Todo.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TodoDao todoDao();
}
