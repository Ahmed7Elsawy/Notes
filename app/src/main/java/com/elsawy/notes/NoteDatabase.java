package com.elsawy.notes;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.elsawy.notes.models.Group;
import com.elsawy.notes.models.Note;

@Database(entities = {Note.class, Group.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance;

    public abstract NoteDao noteDao();

    public abstract GroupDao groupDao();

    public static synchronized NoteDatabase getInstance(Context context) {

        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDatabase.class, "note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }

        return instance;
    }

    private static Callback roomCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            new populateDbAsyncTask(instance).execute();
        }
    };

    private static class populateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        private NoteDao noteDao;
        private GroupDao groupDao;

        private populateDbAsyncTask(NoteDatabase db) {
            noteDao = db.noteDao();
            groupDao = db.groupDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            Group group1 = new Group("group1");
            Group group2 = new Group("group2");
            Group group3 = new Group("group3");

            groupDao.insert(group1);
            groupDao.insert(group2);
            groupDao.insert(group3);


            Note note1 = new Note("Title 1", "description 1");
            note1.setGroupId(1);
            Note note2 = new Note("Title 2", "description 2");
            note2.setGroupId(2);
            Note note3 = new Note("Title 3", "description 3");
            note3.setGroupId(3);

            noteDao.insert(note1);
            noteDao.insert(note2);
            noteDao.insert(note3);



            return null;
        }
    }


}