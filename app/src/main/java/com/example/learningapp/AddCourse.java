package com.example.learningapp;

import androidx.appcompat.app.AppCompatActivity;

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
    private SQLiteHandler db;
    static int id = 0;

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
                String courseID, courseName, courseDesc,courseDuration;

                courseName = courseNameEditText.getText().toString();
                courseDesc = courseDescEditText.getText().toString();
                courseDuration = courseDurationEditText.getText().toString();
                //courseID = String.valueOf(++id);
                if(!courseName.isEmpty() && !courseDesc.isEmpty() && !courseDuration.isEmpty()){
                    requestUser(courseName,courseDesc,courseDuration);
                }else{
                    Toast.makeText(getApplicationContext(), "Please enter valid details", Toast.LENGTH_LONG).show();

                }
            }
        });
    }
    private void requestUser(final String courseName, final String courseDesc, final String courseDuration){
        String url = "";
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    boolean error = jsonObject.getBoolean("error");
                    if(error){
                        String courseID = jsonObject.getString("uid");
                        JSONObject course = jsonObject.getJSONObject("course");
                        String courseName = course.getString("courseName");
                        String courseDesc = course.getString("courseDesc");
                        String courseDuration = course.getString("courseDuration");

                        db.addCourse(courseID, courseName, courseDesc, courseDuration);
                        finish();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                    }

                }catch (JSONException e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }){
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("tag","add");
                params.put("courseName", courseName);
                params.put("courseDesc", courseDesc);
                params.put("courseDuration", courseDuration);

                return params;
            }
        };

        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
    }
}
