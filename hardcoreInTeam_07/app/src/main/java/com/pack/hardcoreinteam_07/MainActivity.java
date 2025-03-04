package com.pack.hardcoreinteam_07;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText et_search, et_name,et_age, et_course,et_gwa,et_profile;
    Button btn_user, btn_addPage, btn_add, btn_update,btn_back,btn_profile,btn_profileBack;
    Spinner spinner;
    ListView listView;
    TextView tv_profile;
    LinearLayout addPage, profilePage;
    DatabaseHelper db = new DatabaseHelper(MainActivity.this);
    ArrayAdapter showArrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        et_search = findViewById(R.id.et_search);
        btn_addPage = findViewById(R.id.btn_addPage);
        et_name = findViewById(R.id.et_name);
        et_age = findViewById(R.id.et_age);
        et_course = findViewById(R.id.et_course);
        et_gwa = findViewById(R.id.et_gwa);
        et_profile = findViewById(R.id.et_profile);

        btn_user = findViewById(R.id.btn_user);
        btn_add = findViewById(R.id.btn_add);
        btn_update = findViewById(R.id.btn_update);
        btn_back = findViewById(R.id.btn_back);
        btn_profile = findViewById(R.id.btn_profile);
        btn_profileBack = findViewById(R.id.btn_profileBack);

        spinner = findViewById(R.id.spinner);
        listView = findViewById(R.id.listView);
        tv_profile = findViewById(R.id.tv_profile);
        addPage = findViewById(R.id.addPage);
        profilePage = findViewById(R.id.profilePage);

        showAll();


//        ----------------- NAVIGATION -----------------
        btn_addPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPage.setVisibility(View.VISIBLE);


            }
        });
        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profilePage.setVisibility(View.VISIBLE);
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPage.setVisibility(View.GONE);
            }
        });
        btn_profileBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profilePage.setVisibility(View.GONE);
            }
        });

//        ----------------- NAVIGATION -----------------

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Student student;
                try{
                    student = new Student();

                    student.setName(et_name.getText().toString());
                    student.setAge(Integer.parseInt(et_age.getText().toString()));
                    student.setCourse(et_course.getText().toString());
                    student.setGwa(Float.parseFloat(et_gwa.getText().toString()));

                    db.addOne(student);
                    Toast.makeText(MainActivity.this, "DUMAGDAG", Toast.LENGTH_SHORT).show();
                } catch (Exception e){
                    Toast.makeText(MainActivity.this, "Hindi hahhahaha", Toast.LENGTH_SHORT).show();
                }
                showAll();
                addPage.setVisibility(View.GONE);
            }
        });

        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                showArrayAdapter.getFilter().filter(charSequence);
                showArrayAdapter.notifyDataSetChanged();
         }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
               Student delete = (Student) adapterView.getItemAtPosition(i);
               db.deleteOne(delete.getId());
               Toast.makeText(MainActivity.this, "NNIGERR", Toast.LENGTH_SHORT).show();
               showAll();
                return true;
            }
        });


        final int[] ID = new int[1];
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                addPage.setVisibility(View.VISIBLE);
                Student student =  (Student) adapterView.getItemAtPosition(i);

                ID[0] = student.getId();
                et_name.setText(student.getName());
                et_age.setText(String.valueOf(student.getAge()));
                et_course.setText(student.getCourse());
                et_gwa.setText(String.valueOf(student.getGwa()));

            }
        });


        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Student student;
                try{
                    student = new Student(ID[0], et_name.getText().toString(), Integer.parseInt(et_age.getText().toString()),
                            et_course.getText().toString(), Float.parseFloat(et_gwa.getText().toString()));
                    db.updateOne(student);
                    Toast.makeText(MainActivity.this, "UPDATED", Toast.LENGTH_SHORT).show();
                } catch (Exception e){
                    Toast.makeText(MainActivity.this, "Hindi hahhahaha", Toast.LENGTH_SHORT).show();
                }
                showAll();
                addPage.setVisibility(View.GONE);
            }
        });

    }

    private void showAll(){
        showArrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, db.getAll());
        listView.setAdapter(showArrayAdapter);
    }
}