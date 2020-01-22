package com.example.jdapresencia.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.jdapresencia.model.User;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT COUNT(*) FROM " + User.TABLE_NAME)
    int getUsersCount();

    @Query("SELECT * FROM " + User.TABLE_NAME)
    List<User> getAllUsersList();

    @Query("SELECT * FROM " + User.TABLE_NAME + " WHERE " + User.TU_C3_USERNAME + " = :username")
    User getUserByUsername(String username);

    @Insert
    void insertUser(User obj);

    @Update
    void updateUser(User... obj);

    @Query("DELETE FROM " + User.TABLE_NAME + " WHERE " + User.TU_C3_USERNAME + " = :username")
    void deleteByUsername(String username);

    @Query("UPDATE " + User.TABLE_NAME + " SET " + User.TU_C2_ROL + " = :rol " +
            " WHERE " + User.TU_C3_USERNAME + " = :username")
    void updateUserRol(String rol, String username);

    @Query("UPDATE " + User.TABLE_NAME + " SET " + User.TU_C2_ROL + " = :rol , " +
            User.TU_C4_PASSWORD + " = :pwd , " + User.TU_C5_PWD_SALT + " = :salt " +
            " WHERE " + User.TU_C3_USERNAME + " = :username")
    void updateUserPassword(String rol, String pwd, String salt, String username);

    @Query("UPDATE " + User.TABLE_NAME + " SET " + User.TU_C2_ROL + " = :rol , " +
            User.TU_C3_USERNAME + " = :newUsername WHERE " + User.TU_C3_USERNAME + " = :username")
    void updateUsername(String rol, String newUsername, String username);
}