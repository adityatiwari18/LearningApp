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

public class Concept extends AppCompatActivity {

    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";
    private static final String KEY_COURSE_ID="CO_id",
            KEY_COURSE_NAME="CO_Name",
            KEY_COURSE_DESC="CO_Desc",
            KEY_COURSE_DURATION="CO_Duration",
            KEY_COURSE_INSERTDATE="CO_Insertdate";
    private static final String BASE_URL = "http://10.12.18.235/courses/db/";
    private ArrayList<HashMap<String, String>> courseList;
    private ProgressDialog pDialog;

    private Button AddConcept;
    private RecyclerView mRecyclerView;
    private ConceptAdapter mConceptAdapter;

    List<String> concepts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_content);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mConceptAdapter = new ConceptAdapter(concepts, this);
        mRecyclerView = (RecyclerView) findViewById(R.id.concept_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mConceptAdapter);
        AddConcept = (Button) findViewById(R.id.AddConcept);
        AddConcept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Concept.this, com.example.learningapp.AddConcept.class);
                startActivity(i);
            }
        });

        AddConcept.setVisibility(View.VISIBLE);
        FetchConceptAsyncTask fetchConceptAsyncTask = new FetchConceptAsyncTask();
        fetchConceptAsyncTask.execute();
    }



    private class FetchConceptAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(Concept.this);
            pDialog.setMessage("Loading course. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "fetch_all_course.php", "GET", null);
            try {
                int success = jsonObject.getInt(KEY_SUCCESS);
                JSONArray courses;
                if (success == 1) {
                    courseList = new ArrayList<>();
                    courses = jsonObject.getJSONArray(KEY_DATA);
                    //Iterate through the response and populate movies list
                    for (int i = 0; i < courses.length(); i++) {
                        JSONObject course = courses.getJSONObject(i);
                        String courseDesc = course.getString(KEY_COURSE_DESC);
                        String courseName = course.getString(KEY_COURSE_NAME);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put(KEY_COURSE_DESC, courseDesc);
                        map.put(KEY_COURSE_NAME, courseName);
                        courseList.add(map);
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
                    for(HashMap<String,String> data : courseList){
                        concepts.add(data.get(KEY_COURSE_NAME));
                    }
                    mConceptAdapter.setConcept(concepts);
                    mConceptAdapter.notifyDataSetChanged();
                }
            });
        }

    }

}
