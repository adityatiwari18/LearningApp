package com.example.learningapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddCourse extends AppCompatActivity {
    private EditText courseNameEditText,courseDescEditText, courseDurationEditText;
    private Button addButton;

    private static final String KEY_COURSE_ID="CO_id",
            KEY_COURSE_NAME="CO_Name",
            KEY_COURSE_DESC="CO_Desc",
            KEY_COURSE_DURATION="CO_Duration",
            KEY_COURSE_INSERTDATE="CO_Insertdate";
    private static final String BASE_URL = "http://10.12.18.235/courses/db/";
    private static String STRING_EMPTY = "";
    private SQLiteHandler db;
    private ProgressDialog cDialog;
    private int success;
    String courseID, courseName, courseDesc,courseDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        courseNameEditText = findViewById(R.id.course_name);
        courseDescEditText = findViewById(R.id.course_desc);
        courseDurationEditText = findViewById(R.id.course_duration);
        addButton = findViewById(R.id.add_button);


        db = new SQLiteHandler(getApplicationContext());

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    addMovie();
                } else {
                    Toast.makeText(AddCourse.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    /**
     * Checks whether all files are filled. If so then calls AddMovieAsyncTask.
     * Otherwise displays Toast message informing one or more fields left empty
     */
    private void addMovie() {
        if (!STRING_EMPTY.equals(courseNameEditText.getText().toString()) &&
                !STRING_EMPTY.equals(courseDescEditText.getText().toString()) &&
                !STRING_EMPTY.equals(courseDurationEditText.getText().toString())) {

            courseName = courseNameEditText.getText().toString();
            courseDesc = courseDescEditText.getText().toString();
            courseDuration = courseDurationEditText.getText().toString();
            new AddCourseAsyncTask().execute();
        } else {
            Toast.makeText(AddCourse.this,
                    "One or more fields left empty!",
                    Toast.LENGTH_LONG).show();

        }


    }

    /**
     * AsyncTask for adding a movie
     */
    private class AddCourseAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display proggress bar
            cDialog = new ProgressDialog(AddCourse.this);
            cDialog.setMessage("Adding Course. Please wait...");
            cDialog.setIndeterminate(false);
            cDialog.setCancelable(false);
            cDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Populating request parameters
            httpParams.put(KEY_COURSE_NAME, courseName);
            httpParams.put(KEY_COURSE_DESC, courseDesc);
            httpParams.put(KEY_COURSE_DURATION, courseDuration);

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "add_course.php", "POST", httpParams);
            try {
                success = jsonObject.getInt("success");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            cDialog.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                    if (success == 1) {
                        //Display success message
                        Toast.makeText(AddCourse.this,
                                "Course Added", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        //Finish ths activity and go back to listing activity
                        finish();

                    } else {
                        Toast.makeText(AddCourse.this,
                                "Some error occurred while adding course",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }
}
