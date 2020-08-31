package com.yr.messageboard_yr;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MessageDao {

    @Query("SELECT * FROM messages")
    LiveData<List<Message>> getAll();

    @Query("SELECT * FROM messages WHERE id = :messageId")
    LiveData<Message> get(String messageId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Message message);

    @Update
    int update(Message message);

    @Delete
    int delete(Message message);

    @Query("DELETE FROM Messages")
    int deleteAll();
}
