package com.cengcelil.eywintodotask.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.cengcelil.eywintodotask.data.local.entity.Note;

import java.util.List;

@Dao
public interface NoteDao {
    @Insert
    void insert(Note note);

    @Delete
    void delete(Note note);

    @Update
    void update(Note note);

    @Query("DELETE FROM note_table")
    void deleteAll();

    @Query("Select * From note_table Order By active Desc,id DESC")
    LiveData<List<Note>> getAll();

}
