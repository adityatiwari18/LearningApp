package com.example.learningapp;

        import android.app.ProgressDialog;
        import android.content.Intent;

        import androidx.appcompat.app.AlertDialog;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;

        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ListView;
        import android.widget.Spinner;
        import android.widget.Toast;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;

public class courseBranch extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";
    private static final String KEY_COURSE_ID="CO_id",
            KEY_COURSE_NAME="CO_Name",
            KEY_COURSE_DESC="CO_Desc",
            KEY_COURSE_DURATION="CO_Duration",
            KEY_COURSE_INSERTDATE="CO_Insertdate";
    private static final String BASE_URL = "http://10.12.18.235/courses/db/";
    private ArrayList<HashMap<String, String>> courseList;
    private ListView courseListView;
    private ProgressDialog pDialog;


    String[] Engineering = {"Course","Computer Science and Engineering", "Electronics and Communication Engineering","Aerospace Engineering"
                        ,"Mechanical Engineering","AP - Upstream","AP - Gas","Civil Engineering","Chemical Engineering"};
    String[] Medical =  {"Course","M. B. B. S. (Bachelor of Medicine and Bachelor of Surgery)","B. D. S. (Bachelor of Dental Surgery)"
                        ,"B. H. M. S. (Bachelor of Homeopathic Medicine & Surgery)","B. A. M. S. (Bachelor of Ayurvedic Medicine and Surgery)"
                        ,"M.D. (Doctor of Medicine)","M.S. (Master of Surgery) ","D.M (Doctorate in Medicine)","B.Pharm (Bachelor of Pharmacy)"
                        ,"B.Sc Nursing","B.P.T (Physiotherapy)","B.O.T (Occupational Therapy)","D.Pharm (Ayurvedic, Siddha Medicine)"
                        ,"BMLT (Bachelor of Medical Lab Technicians)","DMLT (Diploma of Medical Lab Technicians)"};
    String[] Law = {"Course","Bachelor of Laws (LL.B.)","Integrated undergraduate degrees – B.A. LL.B., B.Sc. LL.B., BBA LLB, B.Com LL.B"
                        ,"Master of Laws (LL.M.) ","Master of Business Law","Integrated MBL-LLM/ MBA-LLM"};
    String[] Business = { "Course","BBA. BBA stands for Bachelor of Business Administration","BMS","BBA+MBA Integrated course"
                        ,"Degree in Hotel & Hospitality Management","B Sc in Hotel Management","Diploma Management courses after 12th"
                        ,"BA Management courses","BBS (Bachelor of Business Studies)"};
    String[] Semister = {"Semister","I","II","III","IV","V","VI","VII","VIII"};
    private static final String CSE = "Computer Science and Engineering";
    Spinner sp,sp2;
    RecyclerView mRecyclerView;
    SubjectsAdapter mSubjectsAdapter;
    List<String> Subjects = new ArrayList<>();
    Button AddSub;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_branch);
        sp = (Spinner) findViewById(R.id.spinner);
        sp.setOnItemSelectedListener(this);
        sp2 = (Spinner) findViewById(R.id.spinner2);
        sp2.setOnItemSelectedListener(this);

        AddSub = (Button) findViewById(R.id.AddSubject);
        AddSub.setOnClickListener(this);
        AddSub.setVisibility(View.INVISIBLE);

        mSubjectsAdapter = new SubjectsAdapter(Subjects, this);
        Intent intent = getIntent();
        String Course = intent.getStringExtra("KEY");


        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mSubjectsAdapter);


        switch (Course){

            case "Engineering":
                Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_LONG).show();
                ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Engineering);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp.setAdapter(aa);
                break;

            case "Medical":
                Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_LONG).show();
                ArrayAdapter aa1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Medical);
                aa1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp.setAdapter(aa1);
                break;

            case "Law":
                Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_LONG).show();
                ArrayAdapter aa2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Law);
                aa2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp.setAdapter(aa2);
                break;

            case "Business":
                Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_LONG).show();
                ArrayAdapter aa3 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Business);
                aa3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp.setAdapter(aa3);
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,Semister);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp2.setAdapter(arrayAdapter);

    }

    void getSubjects(String Course, String Semister){
//        FetchMoviesAsyncTask fetchMoviesAsyncTask = new FetchMoviesAsyncTask();
//        fetchMoviesAsyncTask.execute();


        if(Course == CSE&&Semister=="I"){
            AddSub.setVisibility(View.VISIBLE);
            FetchCourseAsyncTask fetchCourseAsyncTask = new FetchCourseAsyncTask();
            fetchCourseAsyncTask.execute();

        }else{
            Subjects.clear();
            AddSub.setVisibility(View.INVISIBLE);
            mSubjectsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, AddCourse.class);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (!sp.getSelectedItem().toString().equalsIgnoreCase("Course")&& !sp2.getSelectedItem().toString().equalsIgnoreCase("Semister")) {
            Toast.makeText(getApplicationContext(), sp.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            getSubjects(sp.getSelectedItem().toString(),sp2.getSelectedItem().toString());
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * Fetches the list of movies from the server
     */
    private class FetchCourseAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(courseBranch.this);
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
                        Subjects.add(data.get(KEY_COURSE_NAME));
                    }
                    mSubjectsAdapter.setSubjects(Subjects);
                    mSubjectsAdapter.notifyDataSetChanged();
                }
            });
        }

    }

    /**
     * Updating parsed JSON data into ListView
     * */
  /*
    private void populateCourseList() {
        ListAdapter adapter = new SimpleAdapter(
                MovieListingActivity.this, movieList,
                R.layout.list_item, new String[]{KEY_MOVIE_ID,
                KEY_MOVIE_NAME},
                new int[]{R.id.movieId, R.id.movieName});
        // updating listview
        *//*
        movieListView.setAdapter(adapter);
        //Call MovieUpdateDeleteActivity when a movie is clicked
        movieListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Check for network connectivity
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    String movieId = ((TextView) view.findViewById(R.id.movieId))
                            .getText().toString();
                    Intent intent = new Intent(getApplicationContext(),
                            MovieUpdateDeleteActivity.class);
                    intent.putExtra(KEY_MOVIE_ID, movieId);
                    startActivityForResult(intent, 20);

                } else {
                    Toast.makeText(MovieListingActivity.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }


            }
        });*//*

    }*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 20) {
            // If the result code is 20 that means that
            // the user has deleted/updated the movie.
            // So refresh the movie listing
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
    }


}
