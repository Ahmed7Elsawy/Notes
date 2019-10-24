package com.elsawy.notes.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.elsawy.notes.Constants;
import com.elsawy.notes.R;
import com.elsawy.notes.adapter.NoteAdapter;
import com.elsawy.notes.models.Note;
import com.elsawy.notes.viewmodel.NoteViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class NotesActivity extends AppCompatActivity {

    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;

    private int groupID = -1;

    private NoteViewModel noteViewModel;
    private FloatingActionButton addNoteFab;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        addNoteFab = findViewById(R.id.add_note_fab);
        recyclerView = findViewById(R.id.note_recycler_view);

        Intent groupIntent = getIntent();
        if (groupIntent.hasExtra(Constants.EXTRA_GROUP_ID)) {
            groupID = groupIntent.getIntExtra(Constants.EXTRA_GROUP_ID, -1);
            Log.i("GroupID",groupID+"");
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final NoteAdapter noteAdapter = new NoteAdapter();
        recyclerView.setAdapter(noteAdapter);
        noteAdapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {
                Intent intent = new Intent(NotesActivity.this,AddNoteActivity.class);
                intent.putExtra(Constants.EXTRA_NOTE_ID,note.getNid());
                intent.putExtra(Constants.EXTRA_NOTE_TITLE,note.getNoteTitle());
                intent.putExtra(Constants.EXTRA_NOTE_DESCRIPTION,note.getNoteDescription());
                startActivityForResult(intent,EDIT_NOTE_REQUEST);
            }
        });

        noteViewModel  = ViewModelProviders.of(this).get(NoteViewModel.class);

        if (groupID != -1){
            noteViewModel.getGroupNotes(groupID).observe(this, new Observer<List<Note>>() {
                @Override
                public void onChanged(List<Note> notes) {
                    // update recyclerview
                    noteAdapter.submitList(notes);
                    Toast.makeText(NotesActivity.this, "data changed", Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            addNoteFab.setVisibility(View.GONE);

            noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
                @Override
                public void onChanged(List<Note> notes) {
                    // update recyclerview
                    noteAdapter.submitList(notes);
                    Toast.makeText(NotesActivity.this, "data changed", Toast.LENGTH_SHORT).show();
                }
            });
        }

        addNoteFab.setOnClickListener(v ->{
            Intent intent = new Intent(NotesActivity.this,AddNoteActivity.class); noteViewModel.getGroupNotes(groupID).observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                // update recyclerview
                noteAdapter.submitList(notes);
                Toast.makeText(NotesActivity.this, "data changed", Toast.LENGTH_SHORT).show();
            }
        });
            startActivityForResult(intent,ADD_NOTE_REQUEST);
        });


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Note note = noteAdapter.getNoteAtPosition(viewHolder.getAdapterPosition());
                noteViewModel.delete(note);
            }
        }).attachToRecyclerView(recyclerView);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(Constants.EXTRA_NOTE_TITLE);
            String description = data.getStringExtra(Constants.EXTRA_NOTE_DESCRIPTION);
            Note note = new Note(title, description);
            note.setGroupId(groupID);
            noteViewModel.insert(note);
        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(Constants.EXTRA_NOTE_ID, -1);
            String title = data.getStringExtra(Constants.EXTRA_NOTE_TITLE);
            String description = data.getStringExtra(Constants.EXTRA_NOTE_DESCRIPTION);

            if (id != -1) {
                Note note = new Note(title, description);
                note.setNid(id);
                note.setGroupId(groupID);
                noteViewModel.update(note);
            }
        }
    }
}
