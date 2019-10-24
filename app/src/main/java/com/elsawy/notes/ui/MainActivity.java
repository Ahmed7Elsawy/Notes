 package com.elsawy.notes.ui;

 import android.content.Intent;
 import android.os.Bundle;
 import android.widget.Button;
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
 import com.elsawy.notes.adapter.GroupAdapter;
 import com.elsawy.notes.models.Group;
 import com.elsawy.notes.viewmodel.GroupViewModel;
 import com.google.android.material.floatingactionbutton.FloatingActionButton;

 import java.util.List;

 public class MainActivity extends AppCompatActivity {

     private static final int ADD_GROUP_REQUEST = 1;

     private GroupViewModel groupViewModel;

     private FloatingActionButton addGroupFab;
     private Button showAllNotesButton;
     private RecyclerView recyclerView;

     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main);

         addGroupFab = findViewById(R.id.add_group_fab);
         showAllNotesButton = findViewById(R.id.show_all_notes_button);
         recyclerView = findViewById(R.id.group_recycler_view);

         final GroupAdapter groupAdapter = new GroupAdapter();

         recyclerView.setLayoutManager(new LinearLayoutManager(this));
         recyclerView.setHasFixedSize(true);
         recyclerView.setAdapter(groupAdapter);

         groupViewModel = ViewModelProviders.of(this).get(GroupViewModel.class);

         groupViewModel.getAllGroups().observe(this, new Observer<List<Group>>() {
             @Override
             public void onChanged(List<Group> groups) {
                 // update recyclerview
                 groupAdapter.submitList(groups);
                 Toast.makeText(MainActivity.this, "data changed", Toast.LENGTH_SHORT).show();
             }
         });

         groupAdapter.setOnItemClickListener(new GroupAdapter.OnItemClickListener() {
             @Override
             public void onItemClick(Group group) {
                 Intent intent = new Intent(MainActivity.this,NotesActivity.class);
                 intent.putExtra(Constants.EXTRA_GROUP_ID,group.getGid());
                 intent.putExtra(Constants.EXTRA_GROUP_TITLE,group.getGroupName());
                 startActivity(intent);
             }
         });

         addGroupFab.setOnClickListener(v -> {
             Intent intent = new Intent(MainActivity.this, AddGroupActivity.class);
             startActivityForResult(intent,ADD_GROUP_REQUEST);
         });

         showAllNotesButton.setOnClickListener(v -> {
             Intent intent = new Intent(MainActivity.this, NotesActivity.class);
             startActivity(intent);
         });

         new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
             @Override
             public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                 return false;
             }

             @Override
             public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                 Group group = groupAdapter.getGroupAtPosition(viewHolder.getAdapterPosition());
                 groupViewModel.delete(group);
             }
         }).attachToRecyclerView(recyclerView);

     }

     protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
         super.onActivityResult(requestCode, resultCode, data);

         if (requestCode == ADD_GROUP_REQUEST && resultCode == RESULT_OK) {
             String title = data.getStringExtra(Constants.EXTRA_GROUP_TITLE);
             Group group = new Group(title);
             groupViewModel.insert(group);
         }
     }

 }
