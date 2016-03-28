package com.example.selfcare.selfcare;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by h on 27/02/2016.
 */
public class P_Update_dB {

    DataBase reg ;
    TableData table;
    SQLiteDatabase sqlDB ;
    public P_Update_dB(Context context){

        reg =new DataBase(context);
        table =new TableData();
        sqlDB = reg.getWritableDatabase();



    }

    public long updateFName (String key ,String fname){

        long id =-5;
        ContentValues co =new ContentValues();
        co.put(TableData.Patient_Regestration.Fname,fname);
        String[]st={key};
    id= sqlDB.update(TableData.Patient_Regestration.TableName1, co, TableData.Patient_Regestration.Email + "=?", st);


        return id;

    }

    public long updateLName (String key ,String lname){

        long id =-5;
        ContentValues co =new ContentValues();
        co.put(TableData.Patient_Regestration.Lname,lname);
        String[]st={key};
        id= sqlDB.update(TableData.Patient_Regestration.TableName1,co,TableData.Patient_Regestration.Email+"=?",st);


        return id;
    }

    public long Passward (String key ,String Passward){

        long id =-5;
        ContentValues co =new ContentValues();
        co.put(TableData.Patient_Regestration.Passward,Passward);
        String[]st={key};
        id= sqlDB.update(TableData.Patient_Regestration.TableName1,co,TableData.Patient_Regestration.Email+"=?",st);


        return id;
    }

    public long mobile (String key ,String mobile){

        long id =-5;
        ContentValues co =new ContentValues();
        co.put(TableData.Patient_Regestration.Moblile,mobile);
        String[]st={key};
        id= sqlDB.update(TableData.Patient_Regestration.TableName1,co,TableData.Patient_Regestration.Email+"=?",st);


        return id;
    }

    public long weight (String key ,String weight){

        long id =-5;
        int wg =Integer.parseInt(weight);
        ContentValues co =new ContentValues();
        co.put(TableData.Patient_Regestration.Weight,wg);
        String[]st={key};
        id= sqlDB.update(TableData.Patient_Regestration.TableName1,co,TableData.Patient_Regestration.Email+"=?",st);


        return id;
    }
}
