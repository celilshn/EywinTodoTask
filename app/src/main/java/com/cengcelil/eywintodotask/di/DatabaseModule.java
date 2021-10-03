package com.cengcelil.eywintodotask.di;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import com.cengcelil.eywintodotask.data.local.AppDatabase;
import com.cengcelil.eywintodotask.data.local.dao.NoteDao;
import com.cengcelil.eywintodotask.data.local.repo.NoteRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

import static com.cengcelil.eywintodotask.other.Constants.DATABASE_NAME;

@InstallIn(SingletonComponent.class)
@Module
public class DatabaseModule {
    private static final String TAG = "DatabaseModule";
    @Provides
    public static NoteDao getNoteDao(AppDatabase appDatabase) {
        return appDatabase.noteDao();
    }
    @Singleton
    @Provides
    public static AppDatabase getDb(@ApplicationContext Context context){
        return Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,DATABASE_NAME)
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build();
    }

    @Provides
    public static NoteRepository getNoteRepo(Application application) {
        return NoteRepository.getInstance(application);
    }
}
