package com.elsawy.notes.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.elsawy.notes.GroupRepository;
import com.elsawy.notes.models.Group;

import java.util.List;

public class GroupViewModel extends AndroidViewModel {


    private GroupRepository groupRepository;
    private LiveData<List<Group>> allGroups;

    public GroupViewModel(@NonNull Application application) {
        super(application);

        groupRepository = new GroupRepository(application);
        allGroups = groupRepository.getAllGroups();
    }

    public void insert(Group group) {
        groupRepository.insert(group);
    }


    public void update(Group group) {
        groupRepository.update(group);
    }

    public void delete(Group group) {
        groupRepository.delete(group);
    }

    public LiveData<List<Group>> getAllGroups() {
        return allGroups;
    }

}
