package com.example.comp1786project.local.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.comp1786project.model.Expense;
import com.example.comp1786project.model.Trip;
import com.example.comp1786project.model.User;

@Database(entities = {User.class, Trip.class, Expense.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract TripDao tripDao();
    public abstract ExpenseDao expenseDao();
}