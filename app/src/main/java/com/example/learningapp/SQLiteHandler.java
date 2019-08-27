package com.example.learningapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "course_content_developer";
    private static final String TABLE_COURSE = "Course";
    private static final String KEY_COURSE_ID="CO_id",
            KEY_COURSE_NAME="CO_Name",
            KEY_COURSE_DESC="CO_Desc",
            KEY_COURSE_DURATION="CO_Duration",
            KEY_COURSE_INSERTDATE="CO_Insertdate";

    public SQLiteHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_COURSE_TABLE = "CREATE TABLE "+ TABLE_COURSE +"("+
                KEY_COURSE_ID+" bigint(20) NOT NULL,"+
                KEY_COURSE_NAME+" text," +
                KEY_COURSE_DESC+" text," +
                KEY_COURSE_DURATION+" bigint(20) DEFAULT NULL," +
                //"  `CO_Image` text,\n" +
                KEY_COURSE_INSERTDATE+" bigint(20) DEFAULT NULL"+
                //"  `CU_id` bigint(20) NOT NULL\n" +
                ")";


        db.execSQL(CREATE_COURSE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_COURSE);
        onCreate(db);

    }
    public void addCourse(String courseID, String courseName,String courseDesc,String courseDuration){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_COURSE_ID, courseID);
        values.put(KEY_COURSE_NAME,courseName);
        values.put(KEY_COURSE_DESC, courseDesc);
        values.put(KEY_COURSE_DURATION, courseDuration);
        db.insert(TABLE_COURSE, null, values);
        db.close();

    }
    public List getAllCourse() {
        List courseDetailsList = new ArrayList();
        String selectQuery = "SELECT * FROM " + TABLE_COURSE
                + " ORDER BY " + KEY_COURSE_ID + " DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //if TABLE has rows
        if (cursor.moveToFirst()) {
            //Loop through the table rows
            do {
                CourseModel courseDetails = new CourseModel();
                courseDetails.setCourseName(cursor.getString(1));
                courseDetails.setCourseDesc(cursor.getString(2));
                courseDetails.setCourseDuration(cursor.getString(2));

                //Add movie details to list
                courseDetailsList.add(courseDetails);
            } while (cursor.moveToNext());
        }
        db.close();
        return courseDetailsList;
    }
    public void updateMovie(String courseID, String courseName,String courseDesc,String courseDuration) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_COURSE_ID, courseID);
        values.put(KEY_COURSE_NAME,courseName);
        values.put(KEY_COURSE_DESC, courseDesc);
        values.put(KEY_COURSE_DURATION, courseDuration);
        db.update(TABLE_COURSE, values, KEY_COURSE_NAME + " = ?", new String[]{courseName});
        db.close();
    }
}
