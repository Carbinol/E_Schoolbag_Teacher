package com.example.teacherebag.Classes;

public class TWIndex {
    private String title;

    private String courseName;

    private String encContent;

    public TWIndex(String title, String courseName, String encContent) {
        this.title = title;
        this.courseName = courseName;
        this.encContent = encContent;
    }

    public String getTitle() {
        return title;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getEncContent() {
        return encContent;
    }
}
