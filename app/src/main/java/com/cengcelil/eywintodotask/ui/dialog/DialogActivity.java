package com.cengcelil.eywintodotask.ui.dialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.widget.Toast;

import com.cengcelil.eywintodotask.databinding.ActivityDialogBinding;
import com.cengcelil.eywintodotask.data.local.entity.Note;
import com.cengcelil.eywintodotask.data.NoteViewModel;

public class DialogActivity extends AppCompatActivity {
    NoteViewModel noteViewModel;
    ActivityDialogBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDialogBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        binding.btAdd.setOnClickListener((view) -> {
            Note note = new Note(binding.etTitle.getText().toString(), binding.etDescription.getText().toString());
            noteViewModel.insert(note);
            Toast.makeText(this, "Task added successfully", Toast.LENGTH_SHORT).show();
            finish();
        });
        binding.btCancel.setOnClickListener((view)->finish());
        setFinishOnTouchOutside(false);
    }
}