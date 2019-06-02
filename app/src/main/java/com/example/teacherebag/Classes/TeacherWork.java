package com.example.teacherebag.Classes;

public class TeacherWork {
    private String teacherId;

    private String policy;

    private Course course;

    private String title;

    private String encContent;

    private String deadline;

    public TeacherWork(String teacherId, String policy, Course course, String title, String encContent, String deadline) {
        this.teacherId = teacherId;
        this.policy = policy;
        this.course = course;
        this.title = title;
        this.encContent = encContent;
        this.deadline = deadline;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public String getPolicy() {
        return policy;
    }

    public Course getCourse() {
        return course;
    }

    public String getTitle() {
        return title;
    }

    public String getEncContent() {
        return encContent;
    }

    public String getDeadline() {
        return deadline;
    }
}
