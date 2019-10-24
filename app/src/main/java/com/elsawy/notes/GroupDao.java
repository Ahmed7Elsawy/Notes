package com.elsawy.notes;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.elsawy.notes.models.Group;

import java.util.List;

@Dao
public interface GroupDao {

    @Query("SELECT * FROM group_table")
    LiveData<List<Group>> getAllGroups();

    @Insert
    void insert(Group groups);

    @Update
    void update(Group group);

    @Delete
    void delete(Group group);

    @Query("DELETE FROM group_table")
    void deleteAllGroups();


}
