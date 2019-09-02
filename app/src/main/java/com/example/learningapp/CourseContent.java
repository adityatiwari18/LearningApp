package com.example.learningapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CourseContent extends AppCompatActivity {

    private Button AddConcept;
    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";
    private static final String KEY_CONCEPT_ID = "CN_id",
            KEY_CONCEPT_NAME = "CN_Name",
            KEY_CONCEPT_DESC = "CN_Desc",
            KEY_CONCEPT_DURATION = "CN_Duration",
            KEY_CONCEPT_INSERTDATE = "CN_Insertdate";
    List<String> Concepts = new ArrayList<>();

    private static final String BASE_URL = "http://localhost/courses/db/";
    private ArrayList<HashMap<String, String>> conceptList;
    private ListView conceptListView;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_content);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AddConcept = (Button) findViewById(R.id.add_button);
        AddConcept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CourseContent.this, com.example.learningapp.AddConcept.class);
                startActivity(i);
            }
        });
    }
    private class FetchConceptAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(CourseContent.this);
            pDialog.setMessage("Loading course. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "fetch_all_concept.php", "GET", null);
            try {
                int success = jsonObject.getInt(KEY_SUCCESS);
                JSONArray courses;
                if (success == 1) {
                    conceptList = new ArrayList<>();
                    courses = jsonObject.getJSONArray(KEY_DATA);
                    //Iterate through the response and populate movies list
                    for (int i = 0; i < courses.length(); i++) {
                        JSONObject course = courses.getJSONObject(i);
                        String conceptDesc = course.getString(KEY_CONCEPT_DESC);
                        String conceptName = course.getString(KEY_CONCEPT_NAME);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put(KEY_CONCEPT_DESC, conceptDesc);
                        map.put(KEY_CONCEPT_NAME, conceptName);
                        conceptList.add(map);
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
                    for(HashMap<String,String> data : conceptList){
                        Concepts.add(data.get(KEY_CONCEPT_NAME));
                    }
                    /*mConceptsAdapter.setSubjects(Concepts);
                    mConceptsAdapter.notifyDataSetChanged();*/
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
