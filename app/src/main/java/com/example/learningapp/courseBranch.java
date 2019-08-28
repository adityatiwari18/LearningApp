package com.example.learningapp;

        import android.content.Intent;

        import androidx.appcompat.app.AlertDialog;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;

        import android.os.Bundle;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Spinner;
        import android.widget.Toast;

        import java.util.ArrayList;
        import java.util.List;

public class courseBranch extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

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
        mSubjectsAdapter = new SubjectsAdapter(Subjects, this);
        Intent intent = getIntent();
        String Course = intent.getStringExtra("KEY");
        AddSub = (Button) findViewById(R.id.AddSubject);
        AddSub.setOnClickListener(this);
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
        if(Course == CSE&&Semister=="I"){
            for(int i=0;i<=10;i++){
                Subjects.add("Subject "+i);
            }
            AddSub.setVisibility(View.VISIBLE);
            mSubjectsAdapter.setSubjects(Subjects);
            mSubjectsAdapter.notifyDataSetChanged();
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
}
