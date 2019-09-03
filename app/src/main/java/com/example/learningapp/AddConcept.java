package com.example.learningapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddConcept extends AppCompatActivity {

    private EditText conceptNameEditText, conceptDescEditText, conceptDurationEditText;
    private Button addButton, addImage;

    public static int RESULT_LOAD_IMAGE = 1;

    private static final String KEY_CONCEPT_ID = "CN_id",
            KEY_CONCEPT_NAME = "CN_Name",
            KEY_CONCEPT_DESC = "CN_Desc",
            KEY_CONCEPT_DURATION = "CN_Duration",
            KEY_CONCEPT_INSERTDATE = "CN_Insertdate";

    private static final String BASE_URL = "http://10.12.18.235/courses/db/";
    private static String STRING_EMPTY = "";
    private ProgressDialog cDialog;
    private int success;
    String conceptID, conceptName, conceptDesc, conceptDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_concept);

        conceptNameEditText = findViewById(R.id.concept_name);
        conceptDescEditText = findViewById(R.id.concept_desc);
        conceptDurationEditText = findViewById(R.id.concept_duration);

        addButton = findViewById(R.id.add_concept_button);

        addImage = (Button) findViewById(R.id.image_concept);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    addConcept();
                } else {
                    Toast.makeText(AddConcept.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }
            }
        });

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);                    //SAVED PICTURE PATH
            cursor.close();

        }
    }

    /**
     * Checks whether all files are filled. If so then calls AddMovieAsyncTask.
     * Otherwise displays Toast message informing one or more fields left empty
     */
    private void addConcept() {
        if (!STRING_EMPTY.equals(conceptNameEditText.getText().toString()) &&
                !STRING_EMPTY.equals(conceptDescEditText.getText().toString()) &&
                !STRING_EMPTY.equals(conceptDurationEditText.getText().toString())) {

            conceptName = conceptNameEditText.getText().toString();
            conceptDesc = conceptDescEditText.getText().toString();
            conceptDuration = conceptDurationEditText.getText().toString();
            new AddConcept.AddConceptAsyncTask().execute();
        } else {
            Toast.makeText(AddConcept.this,
                    "One or more fields left empty!",
                    Toast.LENGTH_LONG).show();

        }

    }

    /**
     * AsyncTask for adding a movie
     */
    private class AddConceptAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display proggress bar
            cDialog = new ProgressDialog(AddConcept.this);
            cDialog.setMessage("Adding Concept. Please wait...");
            cDialog.setIndeterminate(false);
            cDialog.setCancelable(false);
            cDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Populating request parameters
            httpParams.put(KEY_CONCEPT_NAME, conceptName);
            httpParams.put(KEY_CONCEPT_DESC, conceptDesc);
            httpParams.put(KEY_CONCEPT_DURATION, conceptDuration);

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "add_concept.php", "POST", httpParams);
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
                        Toast.makeText(AddConcept.this,
                                "Concept Added", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        //Finish ths activity and go back to listing activity
                        finish();

                    } else {
                        Toast.makeText(AddConcept.this,
                                "Some error occurred while adding course",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }

}
