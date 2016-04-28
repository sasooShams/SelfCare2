package com.example.selfcare.selfcare;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by h on 10/03/2016.
 */
public class GetDataOfDoctor {
    DataBase reg;
    SQLiteDatabase sqlDB;
    // column of Doctor regestration
    String column[] = new String[]{TableData.doctor_Regestration.Fname, TableData.doctor_Regestration.Lname,
            TableData.doctor_Regestration.Email, TableData.doctor_Regestration.Passward,
            TableData.doctor_Regestration.Moblile, TableData.doctor_Regestration.BirthDate ,
            TableData.doctor_Regestration.Weight, TableData.doctor_Regestration.Height ,
            TableData.doctor_Regestration.Gender};

    // column of doctor relative

    String columnOfDRrel[] = new String[]{TableData.doctor_Relative.doctor_ID, TableData.doctor_Relative.RMoblile_1,
            TableData.doctor_Relative.RMoblile_2, TableData.doctor_Relative.RMoblile_3,
            TableData.doctor_Relative.RMoblile_4};

    // column of doctor records

    String columnOfrecords[] = new String[]{TableData.CheckUPDR.doctor_ID, TableData.CheckUPDR.Temperture,
            TableData.CheckUPDR.Pressure, TableData.CheckUPDR.Heart,
            TableData.CheckUPDR.Date};


    //constructor
    public GetDataOfDoctor(Context context){

         reg = new DataBase(context);
         sqlDB = reg.getWritableDatabase();
    }

    // Doctor info regest

    public String Fname (String Email){
        String Fname= "" ;
        Cursor cc = sqlDB.query(TableData.doctor_Regestration.TableNamedoc, column, TableData.doctor_Regestration.Email + "=?", new String[] { Email }, null, null, null);
        if (cc != null ){
            cc.moveToFirst();

            Fname=Fname+cc.getString(0);

            return Fname;}

        return null;
    }


    public String Lname (String Email){
        String Fname= "" ;
        Cursor cc = sqlDB.query(TableData.doctor_Regestration.TableNamedoc, column, TableData.doctor_Regestration.Email + "=?", new String[] { Email }, null, null, null);
        if (cc != null ){
            cc.moveToFirst();

            Fname=Fname+cc.getString(1);

            return Fname;}

        return null;
    }
    public String Pass (String Email){
        String Fname= "" ;
        Cursor cc = sqlDB.query(TableData.doctor_Regestration.TableNamedoc, column, TableData.doctor_Regestration.Email + "=?", new String[] { Email }, null, null, null);
        if (cc != null ){
            cc.moveToFirst();

            Fname=Fname+cc.getString(3);

            return Fname;}

        return null;
    }
    public String Email (String Email){
        String Fname= "" ;
        Cursor cc = sqlDB.query(TableData.doctor_Regestration.TableNamedoc, column, TableData.doctor_Regestration.Email + "=?", new String[] { Email }, null, null, null);
        if (cc != null ){
            cc.moveToFirst();

            Fname=Fname+cc.getString(2);

            return Fname;}

        return null;
    }
    public String mobile (String Email){
        String Fname= "" ;
        Cursor cc = sqlDB.query(TableData.doctor_Regestration.TableNamedoc, column, TableData.doctor_Regestration.Email + "=?", new String[] { Email }, null, null, null);
        if (cc != null ){
            cc.moveToFirst();

            Fname=Fname+cc.getString(4);

            return Fname;}

        return null;

    }
    public String birthdate (String Email){
        String Fname= "" ;
        Cursor cc = sqlDB.query(TableData.doctor_Regestration.TableNamedoc, column, TableData.doctor_Regestration.Email + "=?", new String[] { Email }, null, null, null);
        if (cc != null ){
            cc.moveToFirst();

            Fname=Fname+cc.getString(5);

            return Fname;}

        return null;
    }
    public String weight (String Email){
        String Fname= "" ;
        Cursor cc = sqlDB.query(TableData.doctor_Regestration.TableNamedoc, column, TableData.doctor_Regestration.Email + "=?", new String[] { Email }, null, null, null);
        if (cc != null ){
            cc.moveToFirst();

            Fname=Fname+cc.getString(6);

            return Fname;}

        return null;
    }

    public String height (String Email){
        String Fname= "" ;
        Cursor cc = sqlDB.query(TableData.doctor_Regestration.TableNamedoc, column, TableData.doctor_Regestration.Email + "=?", new String[] { Email }, null, null, null);
        if (cc != null ){
            cc.moveToFirst();

            Fname=Fname+cc.getString(7);

            return Fname;}

        return null;
    }

    public String gendre (String Email){
        String Fname= "" ;
        Cursor cc = sqlDB.query(TableData.doctor_Regestration.TableNamedoc, column, TableData.doctor_Regestration.Email + "=?", new String[] { Email }, null, null, null);
        if (cc != null ){
            cc.moveToFirst();

            Fname=Fname+cc.getString(8);

            return Fname;}

        return null;
    }
/////



    // column of doctor relative
    public String p_relative_mob1(String Email){
        String result= "" ;
        Cursor cc = sqlDB.query(TableData.doctor_Relative.TableNamedocrel, columnOfDRrel, TableData.doctor_Relative.doctor_ID + "=?", new String[] { Email }, null, null, null);
        if (cc != null ){
            cc.moveToFirst();
            result=result+cc.getString(1);

            return result;}

        return null;
    }

    public String p_relative_mob2(String Email){
        String result= "" ;
        Cursor cc = sqlDB.query(TableData.doctor_Relative.TableNamedocrel, columnOfDRrel, TableData.doctor_Relative.doctor_ID + "=?", new String[] { Email }, null, null, null);
        if (cc != null ){
            cc.moveToFirst();
            result=result+cc.getString(2);

            return result;}

        return null;
    }
    public String p_relative_mob3(String Email){
        String result= "" ;
        Cursor cc = sqlDB.query(TableData.doctor_Relative.TableNamedocrel, columnOfDRrel, TableData.doctor_Relative.doctor_ID + "=?", new String[] { Email }, null, null, null);
        if (cc != null ){
            cc.moveToFirst();
            result=result+cc.getString(3);

            return result;}

        return null;
    }
    public String p_relative_mob4(String Email){
        String result= "" ;
        Cursor cc = sqlDB.query(TableData.doctor_Relative.TableNamedocrel, columnOfDRrel, TableData.doctor_Relative.doctor_ID + "=?", new String[] { Email }, null, null, null);
        if (cc != null ){
            cc.moveToFirst();
            result=result+cc.getString(4);

            return result;}

        return null;
    }

    // column of doctor records

    public String heart(String Email){
        String result= "" ;
        Cursor cc = sqlDB.query(TableData.CheckUPDR.TableNameCHDoc, columnOfrecords, TableData.CheckUPDR.doctor_ID + "=?", new String[] { Email }, null, null, null);
        int res = cc.getColumnIndex(TableData.CheckUPDR.Heart);
        for (cc.moveToFirst(); !cc.isAfterLast(); cc.moveToNext())
            result = result +cc.getString(res);
        return result;
    }

    public String temp(String Email){
        String result= "" ;
        Cursor cc = sqlDB.query(TableData.CheckUPDR.TableNameCHDoc, columnOfrecords, TableData.CheckUPDR.doctor_ID + "=?", new String[] { Email }, null, null, null);
        int res = cc.getColumnIndex(TableData.CheckUPDR.Temperture);
        for (cc.moveToFirst(); !cc.isAfterLast(); cc.moveToNext())
            result = result +cc.getString(res);
        return result;
    }
    public String pressure(String Email){
        String result= "" ;
        Cursor cc = sqlDB.query(TableData.CheckUPDR.TableNameCHDoc, columnOfrecords, TableData.CheckUPDR.doctor_ID + "=?", new String[] { Email }, null, null, null);
        int res = cc.getColumnIndex(TableData.CheckUPDR.Pressure);
        for (cc.moveToFirst(); !cc.isAfterLast(); cc.moveToNext())
            result = result+cc.getString(res);
        return result;
    }
    public String date(String Email){
        String result= "" ;
        Cursor cc = sqlDB.query(TableData.CheckUPDR.TableNameCHDoc, columnOfrecords, TableData.CheckUPDR.doctor_ID + "=?", new String[] { Email }, null, null, null);
        int res = cc.getColumnIndex(TableData.CheckUPDR.Date);
        for (cc.moveToFirst(); !cc.isAfterLast(); cc.moveToNext())
            result = result +cc.getString(res);
        return result;
    }


}
