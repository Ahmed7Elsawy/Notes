package com.elsawy.notes;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.elsawy.notes.models.Note;

import java.util.List;

@Dao
public interface NoteDao {

    @Query("SELECT * FROM note_table")
    LiveData<List<Note>> getAllNotes();

    @Query("SELECT * FROM note_table where group_id = :groupId")
    LiveData<List<Note>> getGroupNotes(int groupId);

    @Insert
    void insert(Note notes);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    @Query("DELETE FROM note_table")
    void deleteAllNotes();


}
