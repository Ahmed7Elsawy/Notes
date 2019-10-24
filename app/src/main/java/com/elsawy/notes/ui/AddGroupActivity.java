package com.elsawy.notes.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.elsawy.notes.Constants;
import com.elsawy.notes.R;

public class AddGroupActivity extends AppCompatActivity {


    private EditText groupTitleEdittext;
    private Button saveButton;

    private int noteID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);


        groupTitleEdittext = findViewById(R.id.add_group_title_edit_text);
        saveButton = findViewById(R.id.add_group_save_Button);

        saveButton.setOnClickListener(v -> saveNote());

    }

    private void saveNote() {
        String title = groupTitleEdittext.getText().toString();

        if (title.trim().isEmpty()) {
            Toast.makeText(this, "insert the title ", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(Constants.EXTRA_GROUP_TITLE, title);

        setResult(RESULT_OK, intent);
        finish();
    }

}
