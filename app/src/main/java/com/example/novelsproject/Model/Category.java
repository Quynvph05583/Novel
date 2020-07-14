package com.example.novelsproject.Model;

public class Category {
    private String categoryID, name, note;


    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public Category(String CategoryID, String Name, String Note) {
        this.categoryID = CategoryID;
        this.name = Name;
        this.note = Note;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String CategoryID) {
        this.categoryID = CategoryID;
    }

    public String getName() {
        return name;
    }

    public void setName(String Name) {
        this.name = Name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String Note) {
        this.note = Note;
    }
}
