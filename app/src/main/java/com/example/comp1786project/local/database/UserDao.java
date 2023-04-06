package com.example.comp1786project.local.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.comp1786project.model.User;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user WHERE user_name like :username")
    User getUser(String username);

    @Insert
    void insertUser(User user);
}

