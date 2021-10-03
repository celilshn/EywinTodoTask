package com.cengcelil.eywintodotask.ui.model;


import com.cengcelil.eywintodotask.data.local.entity.Note;

public class BodyItem extends ListItem {

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    private Note note;

    @Override
    public int getType() {
        return note.isActive() ? TYPE_BODY_ACTIVE : TYPE_BODY_DONE;
    }

}