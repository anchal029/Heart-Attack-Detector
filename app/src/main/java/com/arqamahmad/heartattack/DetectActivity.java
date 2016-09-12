package com.arqamahmad.heartattack;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.arqamahmad.heartattack.database.Contract.Entry;
import com.arqamahmad.heartattack.database.DbHelper;

public class DetectActivity extends AppCompatActivity {

    private Spinner mBreathing;
    private Spinner mAge;
    private Spinner mSex;
    private Spinner mDiabetic;
    private EditText mName;
    private ArrayAdapter spinnerAdapter;
    private Button result;

    private int detection = 0; //Variable calculating the Heart Attack percentage

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detect);

        mBreathing = (Spinner)findViewById(R.id.spinner_breathing);
        mAge = (Spinner)findViewById(R.id.spinner_age);
        mSex = (Spinner)findViewById(R.id.spinner_sex);
        mDiabetic = (Spinner)findViewById(R.id.spinner_diabetic);
        mName = (EditText)findViewById(R.id.name);
        result = (Button)findViewById(R.id.button_result);

        setupSpinner();//calculating heart attack percentage


    }


    //Setting up the Spinners and storing the value for heart attack detection percentage.
    private void setupSpinner(){

        spinnerAdapter = ArrayAdapter.createFromResource(this,R.array.array_spinner,android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        mBreathing.setAdapter(spinnerAdapter);
        mAge.setAdapter(spinnerAdapter);
        mSex.setAdapter(spinnerAdapter);
        mDiabetic.setAdapter(spinnerAdapter);

        //Adding for breathing problem
        mBreathing.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.yes))) {
                        detection += 25;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Adding for Above 30 years problem
        mAge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.yes))) {
                        detection += 25;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Adding for sex : men
        mSex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.yes))) {
                        detection += 25;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Adding for Diabetic
        mDiabetic.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.yes))) {
                        detection += 25;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertInDb(detection);
            }
        });

    }

    private void insertInDb(int percentage){
        String name = mName.getText().toString().trim();
        Toast.makeText(DetectActivity.this,name + " with " + percentage + "%",Toast.LENGTH_SHORT).show();

        //Create database helper
        DbHelper mDbHelper = new DbHelper(this);

        //Getting Database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        //Creating content values with Names and Percentage
        ContentValues values = new ContentValues();
        values.put(Entry.COLUMN_NAME,name);
        values.put(Entry.DETECTION_PERCENTAGE,percentage);

        //Insert a new row in database
        long newRowId = db.insert(Entry.TABLE_NAME,null,values);

        // Show a toast message depending on whether or not the insertion was successful
        if (newRowId == -1) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(this, "Error with saving patient", Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText(this, "Patient saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }
}
