package com.cengcelil.eywintodotask.ui.main;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import com.cengcelil.eywintodotask.data.NoteViewModel;
import com.cengcelil.eywintodotask.data.local.entity.Note;
import com.cengcelil.eywintodotask.databinding.ActivityMainBinding;
import com.cengcelil.eywintodotask.service.AddNoteService;
import com.cengcelil.eywintodotask.service.ServiceTools;
import com.cengcelil.eywintodotask.ui.adapter.NoteRecyclerAdapter;
import com.cengcelil.eywintodotask.ui.manager.INoteItemEventManager;

import dagger.hilt.android.AndroidEntryPoint;

import static com.cengcelil.eywintodotask.other.Constants.ACTION_START_LISTEN;
import static com.cengcelil.eywintodotask.other.Constants.PERMISSION_FOREGORUND;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity implements INoteItemEventManager {
    private static final String TAG = "MainActivity";
    private NoteViewModel noteViewModel;
    NoteRecyclerAdapter adapter;
    ActivityMainBinding binding;
    boolean isAddNoteServiceRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupRecycler();
        provideData();
        setListeners();
    }

    private void setListeners() {
        binding.btAddTask.setOnClickListener((view) -> {
            String title = binding.etTitle.getText().toString().trim();
            String description = binding.etDescription.getText().toString();
            noteViewModel.insert(new Note(title, description));
            Toast.makeText(this, "Task added successfully", Toast.LENGTH_SHORT).show();

        });
        binding.swService.setOnCheckedChangeListener((view, checked) -> {
            if (checked)
                startAddNoteService();
            else
                stopAddNoteService();
        });
    }

    private void provideData() {
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAll().observe(this, adapter::setNotes);
    }

    private void setupRecycler() {
        binding.noteRecycler.setHasFixedSize(true);
        adapter = new NoteRecyclerAdapter(this);
        binding.noteRecycler.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isAddNoteServiceRunning = ServiceTools.isServiceRunning(AddNoteService.class.getName(), this);
        if (isAddNoteServiceRunning)
            binding.swService.setChecked(true);
        else
            binding.swService.setChecked(false);

    }

    public void startAddNoteService() {
        Intent intent = new Intent(getApplicationContext(), AddNoteService.class);
        intent.setAction(ACTION_START_LISTEN);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.FOREGROUND_SERVICE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.FOREGROUND_SERVICE)) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.FOREGROUND_SERVICE}, PERMISSION_FOREGORUND);
                    }
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.FOREGROUND_SERVICE}, PERMISSION_FOREGORUND);
                    }
                }
            } else {
                startService(intent);
            }
        else
            startService(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_FOREGORUND) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startAddNoteService();
            }
            else
                binding.swService.setChecked(false);
        }
    }

    public void stopAddNoteService() {
        isAddNoteServiceRunning = ServiceTools.isServiceRunning(AddNoteService.class.getName(), this);
        if (isAddNoteServiceRunning)
            stopService(new Intent(getApplicationContext(), AddNoteService.class));
    }

    @Override
    public void onTrashClicked(Note note) {
        noteViewModel.delete(note);
        Toast.makeText(this, "Task deleted successfully", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNoteChecked(Note note) {
        note.setActive(false);
        noteViewModel.update(note);
    }

    @Override
    public void onNoteUnChecked(Note note) {
        note.setActive(true);
        noteViewModel.update(note);
    }

}