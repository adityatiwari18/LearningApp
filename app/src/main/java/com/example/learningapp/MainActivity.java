package com.example.learningapp;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{

    CardView Engineering;
    CardView Medical;
    CardView Law;
    CardView Business;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Engineering = (CardView) findViewById(R.id.engineering);
        Medical = (CardView) findViewById(R.id.medical);
        Law = (CardView) findViewById(R.id.law);
        Business = (CardView) findViewById(R.id.business);

        Engineering.setOnClickListener(this);
        Medical.setOnClickListener(this);
        Law.setOnClickListener(this);
        Business.setOnClickListener(this);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.engineering:
                Intent intent = new Intent(this, CirriculumBranch.class);
                if(intent!=null){
                    intent.putExtra("KEY","Engineering");
                    startActivity(intent);
                }
                break;
            case R.id.medical:
                Intent intent2 = new Intent(this, CirriculumBranch.class);
                if(intent2!=null){
                    intent2.putExtra("KEY","Medical");
                    startActivity(intent2);
                }
                break;
            case R.id.law:
                Intent intent3 = new Intent(this, CirriculumBranch.class);
                if(intent3!=null){
                    intent3.putExtra("KEY","Law");
                    startActivity(intent3);
                }
                break;
            case R.id.business:
                Intent intent4 = new Intent(this, CirriculumBranch.class);
                if(intent4!=null){
                    intent4.putExtra("KEY","Business");
                    startActivity(intent4);
                }
                break;
        }
    }
}
