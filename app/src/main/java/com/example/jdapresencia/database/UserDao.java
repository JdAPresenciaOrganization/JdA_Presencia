package com.example.jdapresencia.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.jdapresencia.model.User;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT COUNT(*) FROM " + User.TABLE_NAME)
    int getUsersCount();

    @Query("SELECT * FROM " + User.TABLE_NAME)
    List<User> getAllUsersList();

    @Insert
    void insertUser(User obj);
}