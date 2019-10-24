package com.elsawy.notes.models;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "group_table")
public class Group {

    @PrimaryKey(autoGenerate = true)
    private int gid;

    @ColumnInfo(name = "group_name")
    private String groupName;

    public Group(String groupName) {
        this.groupName = groupName;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public int getGid() {
        return gid;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }


    @Override
    public boolean equals(@Nullable Object obj) {
        Group otherGroup = (Group) obj;

        return this.gid == otherGroup.getGid() && this.groupName.equals(otherGroup.getGroupName());
    }

}
