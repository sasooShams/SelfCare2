package com.example.selfcare.selfcare;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by h on 09/03/2016.
 */
public class CheckValid {
    DataBase reg ;
    SQLiteDatabase sqlDB ;


    public CheckValid(Context context){

        reg =new DataBase(context);
        sqlDB = reg.getWritableDatabase();
    }

    public  String vaild_patient(String Email) {

        try {
            String column[] = new String[]{TableData.Patient_Regestration.Fname, TableData.Patient_Regestration.Lname,
                    TableData.Patient_Regestration.Email, TableData.Patient_Regestration.Passward,
                    TableData.Patient_Regestration.Moblile, TableData.Patient_Regestration.BirthDate,
                    TableData.Patient_Regestration.Weight, TableData.Patient_Regestration.Height, TableData.Patient_Regestration.Gender};


            Cursor c = sqlDB.query(TableData.Patient_Regestration.TableName1, column, TableData.Patient_Regestration.Email + "=?", new String[] { Email }, null, null, null);
            if (c != null) {

                c.moveToFirst();
                String pass = c.getString(3);
                return pass;
            }
        } catch (Exception e) {
            return e.toString();
        }

        return null;


    }

    public  String vaild_Doctor(String Email) {

        try {
            String column[] = new String[]{TableData.doctor_Regestration.Fname, TableData.doctor_Regestration.Lname,
                    TableData.doctor_Regestration.Email, TableData.doctor_Regestration.Passward,
                    TableData.doctor_Regestration.Moblile, TableData.doctor_Regestration.BirthDate,
                    TableData.doctor_Regestration.Weight, TableData.doctor_Regestration.Height, TableData.doctor_Regestration.Gender};


            Cursor c = sqlDB.query(TableData.doctor_Regestration.TableNamedoc, column, TableData.doctor_Regestration.Email + "=?", new String[] { Email }, null, null, null);
            if (c != null ){
                c.moveToFirst();

                String pass = c.getString(3);
              //  c.close();
              //  sqlDB.close();
                return pass;
            }
        } catch (Exception e) {
            return e.toString();
        }

        return null;


    }

}
