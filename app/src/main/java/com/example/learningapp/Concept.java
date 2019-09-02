package com.example.learningapp;

import android.content.Intent;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class Concept extends AppCompatActivity {

    private Button AddConcept;
    private RecyclerView mRecyclerView;
    private ConceptAdapter mConceptAdapter;

    List<String> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_content);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mConceptAdapter = new ConceptAdapter(data, this);
        mRecyclerView = (RecyclerView) findViewById(R.id.concept_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mConceptAdapter);
        getConcept();
        AddConcept = (Button) findViewById(R.id.AddConcept);
        AddConcept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Concept.this, com.example.learningapp.AddConcept.class);
                startActivity(i);
            }
        });
    }

    void getConcept(){

        for(int i = 1; i < 10; i++){
            data.add("Concept "+1);
        }
        mConceptAdapter.notifyDataSetChanged();
    }

}
