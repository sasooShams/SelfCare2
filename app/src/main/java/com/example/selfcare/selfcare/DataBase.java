package com.example.selfcare.selfcare;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by h on 21/02/2016.
 */
public class DataBase extends SQLiteOpenHelper {

    public static final int Version=1;
    private static final String DataBaseName ="SelfCare_S";


    Context contexts;
String base1,base2,base3;
    public DataBase(Context context) {
        super(context, DataBaseName, null, Version);
        contexts=context;

        Log.d("DataBase operation","Database Created");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    try {
        //SQL statement of patient regestration
        db.execSQL(TableData.Patient_Regestration.query);
        Log.d("DataBase operation", "Table Created");
    }
    catch (Exception e){

        e.printStackTrace();}

        try {
            //SQL statement of Patient_Relative

            db.execSQL(TableData.Patient_Relative.query);

            String query3 = "DROP TABLE IF EXIST patient_relative";
            String query4 = "DROP TABLE IF EXIST relative";
            String query5 = "DROP TABLE IF EXIST relatives";
            String query6 = "DROP TABLE IF EXIST reg_doctor_inf";

            db.execSQL(query3);
            db.execSQL(query4);
            db.execSQL(query5);
            db.execSQL(query6);
                    Log.d("DataBase operation", "Table Created");
        }
        catch (Exception e){

            e.printStackTrace();}

        try {
            //SQL statement of Patient_Doctor
            db.execSQL(TableData.Patient_Doctor.query);
            Log.d("DataBase operation", "Table Created");
        } catch (Exception e){

            e.printStackTrace();
        }

        try {
            //SQL statement of patient CheckUP
            db.execSQL(TableData.CheckUP.query);
            Log.d("DataBase operation", "Table Created");
        }
        catch (Exception e){

            e.printStackTrace();}
        try {
            //SQL statement of doctor_Regestration
            db.execSQL(TableData.doctor_Regestration.query);
            Log.d("DataBase operation", "Table Created");
        }
        catch (Exception e){

            e.printStackTrace();}

        try {
            //SQL statement of doctor relative
            db.execSQL(TableData.doctor_Relative.query);
            Log.d("DataBase operation", "Table Created");
        }
        catch (Exception e){

            e.printStackTrace();}

        try {
            //SQL statement of doctor CheckUP
            db.execSQL(TableData.CheckUPDR.query);
            Log.d("DataBase operation", "Table Created");
        }
        catch (Exception e){

            e.printStackTrace();}



        try {
            contexts.deleteDatabase("SelfCare");
            contexts.deleteDatabase("SelfCare2");
            contexts.deleteDatabase("SelfCare3");
            contexts.deleteDatabase("SelfCare_D");
            contexts.deleteDatabase("SelfCare_R");
            db.execSQL("DROP TABLE IF EXISTS CheckUPDoc");
            db.execSQL( "DROP TABLE IF EXISTS CheckUPDR");

        }
    catch (Exception e){

        e.printStackTrace();
    }

}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


        String query1 = "DROP TABLE IF EXISTS"+ TableData.Patient_Regestration.TableName1;
        String query2 = "DROP TABLE IF EXISTS"+ TableData.Patient_Relative.TableName2;
        String query3 = "DROP TABLE IF EXISTS"+ TableData.Patient_Doctor.TableName3;
        String query4 = "DROP TABLE IF EXISTS"+ TableData.CheckUP.TableNameCH;
        String query5 = "DROP TABLE IF EXISTS"+ TableData.doctor_Regestration.TableNamedoc;
        String query6 = "DROP TABLE IF EXISTS"+ TableData.doctor_Relative.TableNamedocrel;
        String query7 = "DROP TABLE IF EXISTS"+ TableData.CheckUPDR.TableNameCHDoc;

        try {
            db.execSQL(query1);
            db.execSQL(query2);
            db.execSQL(query3);
            db.execSQL(query4);
            db.execSQL(query5);
            db.execSQL(query6);
            db.execSQL(query7);
            onCreate(db);
        }

        catch (Exception e){

            e.printStackTrace();
        }
    }
}
