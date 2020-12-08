package com.example.helloworld2;
public class ListItem {

    private String noteTitle;
    private String noteDetail;

    public ListItem(String noteTitle, String noteDetail) {
        this.noteTitle = noteTitle;
        this.noteDetail = noteDetail;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteDetail() {
        return noteDetail;
    }

    public void setNoteDetail(String noteDetail) {
        this.noteDetail = noteDetail;
    }
}
