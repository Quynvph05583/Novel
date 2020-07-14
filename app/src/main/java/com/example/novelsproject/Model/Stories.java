package com.example.novelsproject.Model;

import java.io.Serializable;

public class Stories implements Serializable {
    private String ComicId, Name, Thumbnail, content, AuthorID, CategoryID, MaximID, views;

    public Stories(String comicId, String name, String thumbnail, String content, String authorID, String categoryID, String maximID, String views) {
        this.ComicId = comicId;
        this.Name = name;
        this.Thumbnail = thumbnail;
        this.content = content;
        this.AuthorID = authorID;
        this.CategoryID = categoryID;
        this.MaximID = maximID;
        this.views = views;
    }

    public Stories() {
    }

    public Stories(String thumbnail, String name, String views) {
        this.Thumbnail = thumbnail;
        this.views = views;
        this.Name = name;
    }


    public String getComicId() {
        return ComicId;
    }

    public void setComicId(String comicId) {
        ComicId = comicId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getThumbnail() {
        return Thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        Thumbnail = thumbnail;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthorID() {
        return AuthorID;
    }

    public void setAuthorID(String authorID) {
        AuthorID = authorID;
    }

    public String getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(String categoryID) {
        CategoryID = categoryID;
    }

    public String getMaximID() {
        return MaximID;
    }

    public void setMaximID(String maximID) {
        MaximID = maximID;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }
}
