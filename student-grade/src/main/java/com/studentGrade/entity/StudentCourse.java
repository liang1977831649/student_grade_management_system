package com.studentGrade.entity;

import lombok.Data;

@Data
public class StudentCourse {
    private Integer id;
    private String courseNo;
    private String courseName;
    private Integer studentId;
    private String studentName;
    private Float score;
    private String comment;
    private String feedback;
}
