package com.example.learningapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CirriculumContent extends AppCompatActivity {

    private Button AddConcept;
    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";
    private static final String KEY_LESSON_NAME = "LS_Name";
    List<String> Lessons = new ArrayList<>();
    LessonAdapter mLessonAdapter;
    RecyclerView mRecyclerView;

    private static final String BASE_URL = "http://10.12.18.235/courses/db/";
    private ArrayList<HashMap<String, String>> lessonList;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_content);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CirriculumContent.FetchConceptAsyncTask fetchConceptAsyncTask = new CirriculumContent.FetchConceptAsyncTask();
        fetchConceptAsyncTask.execute();
        mLessonAdapter =  new LessonAdapter(Lessons, this);
        Intent intent = getIntent();
        String Course = intent.getStringExtra("KEY");

        mRecyclerView = (RecyclerView) findViewById(R.id.concept_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mLessonAdapter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AddConcept = findViewById(R.id.add_concept_button);
        AddConcept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CirriculumContent.this, AddLesson.class);
                startActivity(i);
            }
        });

    }
    private class FetchConceptAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(CirriculumContent.this);
            pDialog.setMessage("Loading lessons. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "fetch_all_lesson.php", "GET", null);
            try {
                int success = jsonObject.getInt(KEY_SUCCESS);
                JSONArray lesson;
                if (success == 1) {
                    lessonList = new ArrayList<>();
                    lesson = jsonObject.getJSONArray(KEY_DATA);
                    //Iterate through the response and populate movies list
                    for (int i = 0; i < lesson.length(); i++) {
                        JSONObject lessonJSONObject = lesson.getJSONObject(i);
                        String lessonName = lessonJSONObject.getString(KEY_LESSON_NAME);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put(KEY_LESSON_NAME, lessonName);
                        lessonList.add(map);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            pDialog.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                    for(HashMap<String,String> data : lessonList){
                        Lessons.add(data.get(KEY_LESSON_NAME));
                    }
                    mLessonAdapter.setConcept(Lessons);
                    mLessonAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 20) {
            // If the result code is 20 that means that
            // the user has deleted/updated the concept.
            // So refresh the movie listing
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
    }


}
