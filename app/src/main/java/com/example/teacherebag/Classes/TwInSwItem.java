package com.example.teacherebag.Classes;

public class TwInSwItem {
    private long teacherWorkId;

    private String title;

    private String courseName;

    private String encContent;

    public TwInSwItem(long teacherWorkId, String title, String courseName, String encContent) {
        this.teacherWorkId = teacherWorkId;
        this.title = title;
        this.courseName = courseName;
        this.encContent = encContent;
    }

    public long getTeacherWorkId() {
        return teacherWorkId;
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
