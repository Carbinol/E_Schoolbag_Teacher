package com.example.teacherebag.Classes;

public class StudentWorkRemark {
    private float score;

    private String remark;

    public StudentWorkRemark(float score, String remark) {
        this.score = score;
        this.remark = remark;
    }

    public float getScore() {
        return score;
    }

    public String getRemark() {
        return remark;
    }
}
