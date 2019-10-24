package com.elsawy.notes.models;


import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;


@Entity(tableName = "note_table" , foreignKeys = @ForeignKey(entity = Group.class,
        parentColumns = "gid",
        childColumns = "group_id",
        onDelete = CASCADE))

public class Note {

    @PrimaryKey(autoGenerate = true)
    private int nid;

    @ColumnInfo(name = "note_title")
    private String noteTitle;

    @ColumnInfo(name = "note_description")
    private String noteDescription;

//    @Nullable
    @ColumnInfo(name = "group_id")
    private int groupId;

//    @Ignore
    public Note(String noteTitle, String noteDescription) {
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
    }

//    public Note(String noteTitle, String noteDescription, int groupId) {
//        this.noteTitle = noteTitle;
//        this.noteDescription = noteDescription;
//        this.groupId = groupId;
//    }

    public int getNid() {
        return nid;
    }

    public void setNid(int nid) {
        this.nid = nid;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        Note otherNote = (Note) obj;

        return this.nid == otherNote.getNid() && this.noteTitle.equals(otherNote.getNoteTitle()) && this.noteDescription.equals(otherNote.getNoteDescription());
    }
}
