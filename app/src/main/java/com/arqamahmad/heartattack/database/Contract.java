package com.arqamahmad.heartattack.database;

import android.provider.BaseColumns;

/**
 * Created by B on 9/12/2016.
 */
public final class Contract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private Contract(){}

    public static final class Entry implements BaseColumns {

        //Name of database table
        public final static String TABLE_NAME = "patients";
        // Unique ID number
        public final static String COLUMN_ID = BaseColumns._ID;

        public final static String COLUMN_NAME ="name";

        public static final String DETECTION_PERCENTAGE = "detection%";

    }

}
