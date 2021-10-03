package com.cengcelil.eywintodotask.ui.manager;

import com.cengcelil.eywintodotask.data.local.entity.Note;

public interface INoteItemEventManager
{
    void onTrashClicked(Note note);
    void onNoteChecked(Note note);
    void onNoteUnChecked(Note note);
}
