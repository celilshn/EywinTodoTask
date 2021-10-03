package com.cengcelil.eywintodotask.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cengcelil.eywintodotask.databinding.SingleNoteDoneItemBinding;
import com.cengcelil.eywintodotask.databinding.SingleNoteHeaderItemBinding;
import com.cengcelil.eywintodotask.databinding.SingleNoteItemBinding;
import com.cengcelil.eywintodotask.data.local.entity.Note;
import com.cengcelil.eywintodotask.ui.model.BodyItem;
import com.cengcelil.eywintodotask.ui.model.HeaderItem;
import com.cengcelil.eywintodotask.ui.manager.INoteItemEventManager;
import com.cengcelil.eywintodotask.ui.model.ListItem;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class NoteRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "NoteRecyclerAdapter";

    @Override
    public int getItemViewType(int position) {
        return notes.get(position).getType();
    }

    public void setNotes(List<Note> notes) {
        this.notes = sortAndGroupItems(notes);
        notifyDataSetChanged();
    }

    private List<ListItem> sortAndGroupItems(List<Note> notes) {
        TreeMap<Boolean, List<Note>> map = new TreeMap<>((o1, o2) -> o1 == o2 ? 0 : 1);
        List<ListItem> items = new ArrayList<>();
        for (Note note : notes) {
            if (!map.containsKey(note.isActive())) {
                List<Note> list = new ArrayList<>();
                list.add(note);
                map.put(note.isActive(), list);
            } else {
                map.get(note.isActive()).add(note);
            }
        }
        for (boolean active : map.keySet()) {
            HeaderItem header = new HeaderItem();
            header.setHeaderText(active);
            items.add(header);
            for (Note note : map.get(active)) {
                BodyItem item = new BodyItem();
                item.setNote(note);
                items.add(item);
            }
        }
        return items;
    }

    List<ListItem> notes = new ArrayList<>();
    INoteItemEventManager iNoteItemEventManager;

    public NoteRecyclerAdapter(INoteItemEventManager iNoteItemEventManager) {
        this.iNoteItemEventManager = iNoteItemEventManager;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ListItem.TYPE_HEADER)
            return new MyHeaderHolder(SingleNoteHeaderItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        else if (viewType == ListItem.TYPE_BODY_ACTIVE)
            return new MyBodyActiveHolder(SingleNoteItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        else
            return new MyBodyDoneHolder(SingleNoteDoneItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        if (type == ListItem.TYPE_HEADER) {
            HeaderItem header = (HeaderItem) notes.get(position);
            MyHeaderHolder myHeaderHolder = (MyHeaderHolder) holder;
            myHeaderHolder.binding.tvHeader.setText(header.getHeaderText());
        } else{
            BodyItem bodyItem = (BodyItem) notes.get(position);
            if (type==ListItem.TYPE_BODY_ACTIVE){
                MyBodyActiveHolder myBodyActiveHolder = (MyBodyActiveHolder) holder;
                myBodyActiveHolder.binding.tvTitle.setText(bodyItem.getNote().getTitle());
                myBodyActiveHolder.binding.tvDescription.setText(bodyItem.getNote().getDescription());
                myBodyActiveHolder.binding.btTrash.setOnClickListener((view) -> {
                    iNoteItemEventManager.onTrashClicked(bodyItem.getNote());
                });
                myBodyActiveHolder.binding.cbActive.setChecked(false);
                myBodyActiveHolder.binding.cbActive.setOnCheckedChangeListener((view, checked) -> {
                    iNoteItemEventManager.onNoteChecked(bodyItem.getNote());
                });
            }
            else
            {
                MyBodyDoneHolder myBodyDoneHolder = (MyBodyDoneHolder) holder;
                myBodyDoneHolder.binding.tvTitle.setText(bodyItem.getNote().getTitle());
                myBodyDoneHolder.binding.tvDescription.setText(bodyItem.getNote().getDescription());
                myBodyDoneHolder.binding.cbActive.setChecked(true);

                myBodyDoneHolder.binding.cbActive.setOnCheckedChangeListener((view, checked) -> {
                    iNoteItemEventManager.onNoteUnChecked(bodyItem.getNote());
                });
                myBodyDoneHolder.binding.btTrash.setOnClickListener((view) -> {
                    iNoteItemEventManager.onTrashClicked(bodyItem.getNote());
                });
            }
        }

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    static class MyHeaderHolder extends RecyclerView.ViewHolder {
        private SingleNoteHeaderItemBinding binding;

        public MyHeaderHolder(SingleNoteHeaderItemBinding binding) {
            super(binding.getRoot());
            this.binding = SingleNoteHeaderItemBinding.bind(itemView);

        }
    }

    static class MyBodyActiveHolder extends RecyclerView.ViewHolder {
        private SingleNoteItemBinding binding;

        public MyBodyActiveHolder(SingleNoteItemBinding binding) {
            super(binding.getRoot());
            this.binding = SingleNoteItemBinding.bind(itemView);

        }
    }

    static class MyBodyDoneHolder extends RecyclerView.ViewHolder {
        private SingleNoteDoneItemBinding binding;

        public MyBodyDoneHolder(SingleNoteDoneItemBinding binding) {
            super(binding.getRoot());
            this.binding = SingleNoteDoneItemBinding.bind(itemView);

        }
    }


}
