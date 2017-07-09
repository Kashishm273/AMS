package com.example.kashish.ams;

/**
 * Created by Kashish on 7/22/2016.
 */
public class StaticFields {
    private static String student_email_id;
    private static String teacher_email_id;

    public static String getStudent_email_id() {
        return student_email_id;
    }

    public static void setStudent_email_id(String student_email_id) {
        StaticFields.student_email_id = student_email_id;
    }

    public static String getTeacher_email_id() {
        return teacher_email_id;
    }

    public static void setTeacher_email_id(String teacher_email_id) {
        StaticFields.teacher_email_id = teacher_email_id;
    }
}
