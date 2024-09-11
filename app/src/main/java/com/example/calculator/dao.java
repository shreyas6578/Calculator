package com.example.calculator;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface dao {

    @Insert
    void insert(Memo history);

     @Delete
    void  delete(Memo history);

    @Query("SELECT * FROM (SELECT * FROM history ORDER BY id DESC LIMIT 10) subquery ORDER BY id ASC")
    List<Memo> getAllNotes();

    @Query("DELETE FROM sqlite_sequence WHERE name = 'history'")
    void resetAutoIncrement();

    @Query("DELETE FROM history")
    void deleteAll();
}
