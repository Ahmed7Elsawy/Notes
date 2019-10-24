package com.elsawy.notes;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.elsawy.notes.models.Group;

import java.util.List;

public class GroupRepository {

    private GroupDao groupDao;
    private LiveData<List<Group>> allGroup;

    public GroupRepository(Application application) {
        NoteDatabase noteDatabase = NoteDatabase.getInstance(application);
        groupDao = noteDatabase.groupDao();
        allGroup = groupDao.getAllGroups();
    }

    public LiveData<List<Group>> getAllGroups() {
        return allGroup;
    }

    public void insert(Group group) {
        new InsertGroupAsyncTask(groupDao).execute(group);
    }

    public void update(Group group) {
        new UpdateGroupAsyncTask(groupDao).execute(group);
    }

    public void delete(Group group) {
        new DeleteGroupAsyncTask(groupDao).execute(group);
    }


    private static class InsertGroupAsyncTask extends AsyncTask<Group, Void, Void> {
        private GroupDao groupDao;

        InsertGroupAsyncTask(GroupDao groupDao) {
            this.groupDao = groupDao;
        }

        @Override
        protected Void doInBackground(Group... groups) {
            groupDao.insert(groups[0]);
            return null;
        }
    }

    private static class UpdateGroupAsyncTask extends AsyncTask<Group, Void, Void> {
        private GroupDao groupDao;

        UpdateGroupAsyncTask(GroupDao groupDao) {
            this.groupDao = groupDao;
        }

        @Override
        protected Void doInBackground(Group... groups) {
            groupDao.update(groups[0]);
            return null;
        }
    }

    private static class DeleteGroupAsyncTask extends AsyncTask<Group, Void, Void> {
        private GroupDao groupDao;

        DeleteGroupAsyncTask(GroupDao groupDao) {
            this.groupDao = groupDao;
        }

        @Override
        protected Void doInBackground(Group... groups) {
            groupDao.delete(groups[0]);
            return null;
        }
    }


}