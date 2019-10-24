package com.elsawy.notes.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.elsawy.notes.Constants;
import com.elsawy.notes.R;

public class AddNoteActivity extends AppCompatActivity {


    private EditText noteTitleEditText;
    private EditText noteDescriptionEditText;
    private Button saveButton;

    private int noteID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        noteTitleEditText = findViewById(R.id.add_note_title_edit_text);
        noteDescriptionEditText = findViewById(R.id.add_note_description_edit_text);
        saveButton = findViewById(R.id.add_note_save_Button);

        saveButton.setOnClickListener(v -> saveNote());

        Intent intent = getIntent();

        if (intent.hasExtra(Constants.EXTRA_NOTE_ID)) {
            setTitle("edit note");
            noteID = intent.getIntExtra(Constants.EXTRA_NOTE_ID, -1);
            noteTitleEditText.setText(intent.getStringExtra(Constants.EXTRA_NOTE_TITLE));
            noteDescriptionEditText.setText(intent.getStringExtra(Constants.EXTRA_NOTE_DESCRIPTION));
        } else {
            setTitle("add note");
        }

    }

    private void saveNote() {
        String title = noteTitleEditText.getText().toString();
        String description = noteDescriptionEditText.getText().toString();

        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "insert the title and description ", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(Constants.EXTRA_NOTE_TITLE, title);
        intent.putExtra(Constants.EXTRA_NOTE_DESCRIPTION, description);

        if (noteID != -1)
            intent.putExtra(Constants.EXTRA_NOTE_ID, noteID);

        setResult(RESULT_OK, intent);
        finish();
    }

}