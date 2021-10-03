package com.cengcelil.eywintodotask.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.cengcelil.eywintodotask.data.local.repo.NoteRepository;
import com.cengcelil.eywintodotask.data.local.entity.Note;
import com.cengcelil.eywintodotask.di.DatabaseModule;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class NoteViewModel extends AndroidViewModel {
    NoteRepository repository;
    private LiveData<List<Note>> allData;
    @Inject
    public NoteViewModel(@NonNull Application application) {
        super(application);
        repository = DatabaseModule.getNoteRepo(application);
        allData = repository.getAll();
    }

    public void insert(Note note) {
        repository.insert(note);
    }

    public void update(Note note) {
        repository.update(note);
    }

    public void delete(Note note) {
        repository.delete(note);
    }

    public LiveData<List<Note>> getAll() {
        return repository.getAll();
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}
