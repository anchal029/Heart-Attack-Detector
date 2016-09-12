package com.arqamahmad.heartattack;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.arqamahmad.heartattack.database.Contract.Entry;
import com.arqamahmad.heartattack.database.DbHelper;

public class ListActivity extends AppCompatActivity {

    private TextView textView;
    private DbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        textView = (TextView) findViewById(R.id.textView);
        mDbHelper = new DbHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabase();
    }

    private void displayDatabase() {

        //get read permission for the database
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        //Projection to specify which column to use from the database
        String[] projection = {
                Entry.COLUMN_ID,
                Entry.COLUMN_NAME,
                Entry.DETECTION_PERCENTAGE
        };

        //Query
        Cursor cursor = db.query(
                Entry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        try {
            //Creating header for the Database
            textView.setText("Database of the Patient list\n\n");
            textView.append(Entry.COLUMN_ID + " - " + Entry.COLUMN_NAME + " - " + Entry.DETECTION_PERCENTAGE + "\n");

            //Index for each column
            int idColumnIndex = cursor.getColumnIndex(Entry.COLUMN_ID);
            int nameColumnIndex = cursor.getColumnIndex(Entry.COLUMN_NAME);
            int detectionColumnIndex = cursor.getColumnIndex(Entry.DETECTION_PERCENTAGE);

            //Iterating through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                //Using the index to extract the values
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                int currentDetection = cursor.getInt(detectionColumnIndex);
                //Display Values of each in the TextView.
                textView.append("\n" + currentID + " - " + currentName + " - " + currentDetection);
            }
        }
        finally {
            cursor.close();
        }
    }
}
