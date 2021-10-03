package com.cengcelil.eywintodotask.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.cengcelil.eywintodotask.data.local.entity.Note;
import com.cengcelil.eywintodotask.data.local.dao.NoteDao;

@Database(entities = Note.class,version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract NoteDao noteDao();
}
