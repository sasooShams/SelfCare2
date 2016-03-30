package com.example.selfcare.selfcare;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by h on 10/03/2016.
 */
public class GetDataOfPatient {
    DataBase reg ;
    SQLiteDatabase sqlDB ;
    // column of patient Regestration
    String column[] = new String[]{TableData.Patient_Regestration.Fname, TableData.Patient_Regestration.Lname,
            TableData.Patient_Regestration.Email, TableData.Patient_Regestration.Passward,
            TableData.Patient_Regestration.Moblile, TableData.Patient_Regestration.BirthDate ,
            TableData.Patient_Regestration.Weight, TableData.Patient_Regestration.Height ,
            TableData.Patient_Regestration.Gender};
    // column of patient Doctor
    String columns[] = new String[]{TableData.Patient_Doctor.DFname, TableData.Patient_Doctor.DLname,
            TableData.Patient_Doctor.DEmail, TableData.Patient_Doctor.Patient_ID,
            TableData.Patient_Doctor.DMoblile, TableData.Patient_Doctor.DAddress };
    // column of patient relative

    String columnOfPrel[] = new String[]{TableData.Patient_Relative.Patient_ID, TableData.Patient_Relative.RMoblile_1,
            TableData.Patient_Relative.RMoblile_2, TableData.Patient_Relative.RMoblile_3,
            TableData.Patient_Relative.RMoblile_4};

    // column of patient records
    String columnOfrecords[] = new String[]{TableData.CheckUP.Patient_ID, TableData.CheckUP.Temperture,
            TableData.CheckUP.Pressure, TableData.CheckUP.Heart,
            TableData.CheckUP.Date};


    //constructor
    public GetDataOfPatient(Context context){

        reg =new DataBase(context);
        sqlDB = reg.getWritableDatabase();
    }

/*
    public  String getDataofPatient (){
        String result = "";


        try {


            Cursor c = sqlDB.query(TableData.Patient_Regestration.TableName1, column, null, null, null, null, null);
            //  Cursor cc= sqlDB.rawQuery("SELECT * FROM"+TableData.Patient_Regestration.TableName1+" WHERE "+TableData.Patient_Regestration.Email+" = saraah@yahoo.com",null);
            Cursor cc = sqlDB.query(TableData.Patient_Regestration.TableName1, column, TableData.Patient_Regestration.Email + "=?", new String[] { "saraah@yahoo.com" }, null, null, null);


            int iFname = c.getColumnIndex(TableData.Patient_Regestration.Fname);
            int iLname = c.getColumnIndex(TableData.Patient_Regestration.Lname);
            int iEmail = c.getColumnIndex(TableData.Patient_Regestration.Email);
            int iPassword = c.getColumnIndex(TableData.Patient_Regestration.Passward);
            int iPhone = c.getColumnIndex(TableData.Patient_Regestration.Moblile);
            int iBirth =c.getColumnIndex(TableData.Patient_Regestration.BirthDate);
            int iWeight = c.getColumnIndex(TableData.Patient_Regestration.Weight);
            int iHeight = c.getColumnIndex(TableData.Patient_Regestration.Height);
            int iGender = c.getColumnIndex(TableData.Patient_Regestration.Gender);

            int fname = c.getColumnIndex(TableData.Patient_Regestration.Fname);
            int lname = c.getColumnIndex(TableData.Patient_Regestration.Lname);
            int email = c.getColumnIndex(TableData.Patient_Regestration.Email);


            for (cc.moveToFirst(); !cc.isAfterLast(); cc.moveToNext())
                //result = result +  "\n"+cv.getString(email)+cv.getString(fname)+"\n"+"\n";
                result = result + "\n"+cc.getString(0)+"\n"+cc.getString(1)+"\n";

            return result;
        }
        catch (Exception e){
            return e.toString();
        } finally {

        }


    }*/

    //functions to retrieve data
    //caal it in your activity

// paient info regest

    public String Fname (String Email){
      String Fname= "" ;
        Cursor cc = sqlDB.query(TableData.Patient_Regestration.TableName1, column, TableData.Patient_Regestration.Email + "=?", new String[] { Email }, null, null, null);
        if (cc != null ){
            cc.moveToFirst();

        Fname=Fname+"\n"+cc.getString(0);
            //sqlDB.close();
        return Fname;}

        return null;
    }


    public String Lname (String Email){
        String Fname= "" ;
        Cursor cc = sqlDB.query(TableData.Patient_Regestration.TableName1, column, TableData.Patient_Regestration.Email + "=?", new String[] { Email }, null, null, null);
        if (cc != null ){
            cc.moveToFirst();

            Fname=Fname+"\n"+cc.getString(1);
            //sqlDB.close();
            return Fname;}

        return null;
    }
    public String Pass (String Email){
        String Fname= "" ;
        Cursor cc = sqlDB.query(TableData.Patient_Regestration.TableName1, column, TableData.Patient_Regestration.Email + "=?", new String[] { Email }, null, null, null);
        if (cc != null ){
            cc.moveToFirst();

            Fname=Fname+"\n"+cc.getString(3);
            //sqlDB.close();
            return Fname;}

        return null;
    }
    public String Email (String Email){
        String Fname= "" ;
        Cursor cc = sqlDB.query(TableData.Patient_Regestration.TableName1, column, TableData.Patient_Regestration.Email + "=?", new String[] { Email }, null, null, null);
        if (cc != null ){
            cc.moveToFirst();

            Fname=Fname+"\n"+cc.getString(2);
            //sqlDB.close();
            return Fname;}

        return null;
    }
    public String mobile (String Email){
        String Fname= "" ;
        Cursor cc = sqlDB.query(TableData.Patient_Regestration.TableName1, column, TableData.Patient_Regestration.Email + "=?", new String[] { Email }, null, null, null);
        if (cc != null ){
            cc.moveToFirst();

            Fname=Fname+"\n"+cc.getString(4);
            //sqlDB.close();
            return Fname;}

        return null;

}
    public String birthdate (String Email){
        String Fname= "" ;
        Cursor cc = sqlDB.query(TableData.Patient_Regestration.TableName1, column, TableData.Patient_Regestration.Email + "=?", new String[] { Email }, null, null, null);
        if (cc != null ){
            cc.moveToFirst();

            Fname=Fname+"\n"+cc.getString(5);
            //sqlDB.close();
            return Fname;}

        return null;
    }
    public String weight (String Email){
        String Fname= "" ;
        Cursor cc = sqlDB.query(TableData.Patient_Regestration.TableName1, column, TableData.Patient_Regestration.Email + "=?", new String[] { Email }, null, null, null);
        if (cc != null ){
            cc.moveToFirst();

            Fname=Fname+"\n"+cc.getString(6);
            //sqlDB.close();
            return Fname;}

        return null;
    }

    public String height (String Email){
        String Fname= "" ;
        Cursor cc = sqlDB.query(TableData.Patient_Regestration.TableName1, column, TableData.Patient_Regestration.Email + "=?", new String[] { Email }, null, null, null);
        if (cc != null ){
            cc.moveToFirst();

            Fname=Fname+"\n"+cc.getString(7);
            //sqlDB.close();
            return Fname;}

        return null;
    }

    public String gendre (String Email){
        String Fname= "" ;
        Cursor cc = sqlDB.query(TableData.Patient_Regestration.TableName1, column, TableData.Patient_Regestration.Email + "=?", new String[] { Email }, null, null, null);
        if (cc != null ){
            cc.moveToFirst();

            Fname=Fname+"\n"+cc.getString(8);
            //sqlDB.close();
            return Fname;}

        return null;
    }
/////

    // paient doctor info
    public String Dfname (String Email){
        String Fname= "" ;
        Cursor cc = sqlDB.query(TableData.Patient_Doctor.TableName3, columns, TableData.Patient_Doctor.Patient_ID + "=?", new String[] { Email }, null, null, null);
        if (cc != null ){
            cc.moveToFirst();
            Fname=Fname+"\n"+cc.getString(0);
            //sqlDB.close();
            return Fname;}

        return null;
    }

    public String Dlname (String Email){
        String Fname= "" ;
        Cursor cc = sqlDB.query(TableData.Patient_Doctor.TableName3, columns, TableData.Patient_Doctor.Patient_ID + "=?", new String[] { Email }, null, null, null);
        if (cc != null ){
            cc.moveToFirst();
            Fname=Fname+"\n"+cc.getString(1);
            //sqlDB.close();
            return Fname;}

        return null;
    }
    public String Demail (String Email){
        String Fname= "" ;
        Cursor cc = sqlDB.query(TableData.Patient_Doctor.TableName3, columns, TableData.Patient_Doctor.Patient_ID + "=?", new String[] { Email }, null, null, null);
        if (cc != null ){
            cc.moveToFirst();
            Fname=Fname+"\n"+cc.getString(2);
            //sqlDB.close();
            return Fname;}

        return null;
    }
    public String Dmobile (String Email){
        String Fname= "" ;
        Cursor cc = sqlDB.query(TableData.Patient_Doctor.TableName3, columns, TableData.Patient_Doctor.Patient_ID + "=?", new String[] { Email }, null, null, null);
        if (cc != null ){
            cc.moveToFirst();
            Fname=Fname+"\n"+cc.getString(4);
            //sqlDB.close();
            return Fname;}

        return null;
    }
    public String Daddress (String Email){
        String Fname= "" ;
        Cursor cc = sqlDB.query(TableData.Patient_Doctor.TableName3, columns, TableData.Patient_Doctor.Patient_ID + "=?", new String[] { Email }, null, null, null);
        if (cc != null ){
            cc.moveToFirst();
            Fname=Fname+"\n"+cc.getString(5);
            //sqlDB.close();
            return Fname;}

        return null;
    }

    // column of patient relative
    public String p_relative_mob1(String Email){
        String result= "" ;
        Cursor cc = sqlDB.query(TableData.Patient_Relative.TableName2, columnOfPrel, TableData.Patient_Relative.Patient_ID + "=?", new String[] { Email }, null, null, null);
        if (cc != null ){
            cc.moveToFirst();
            result=result+"\n"+cc.getString(1);
            //sqlDB.close();
            return result;}

        return null;
    }

    public String p_relative_mob2(String Email){
        String result= "" ;
        Cursor cc = sqlDB.query(TableData.Patient_Relative.TableName2, columnOfPrel, TableData.Patient_Relative.Patient_ID + "=?", new String[] { Email }, null, null, null);
        if (cc != null ){
            cc.moveToFirst();
            result=result+"\n"+cc.getString(2);
          //  sqlDB.close();
            return result;}

        return null;
    }
    public String p_relative_mob3(String Email){
        String result= "" ;
        Cursor cc = sqlDB.query(TableData.Patient_Relative.TableName2, columnOfPrel, TableData.Patient_Relative.Patient_ID + "=?", new String[] { Email }, null, null, null);
        if (cc != null ){
            cc.moveToFirst();
            result=result+"\n"+cc.getString(3);
           // sqlDB.close();
            return result;}

        return null;
    }
    public String p_relative_mob4(String Email){
        String result= "" ;
        Cursor cc = sqlDB.query(TableData.Patient_Relative.TableName2, columnOfPrel, TableData.Patient_Relative.Patient_ID + "=?", new String[] { Email }, null, null, null);
        if (cc != null ){
            cc.moveToFirst();
            result=result+"\n"+cc.getString(4);
            //sqlDB.close();
            return result;}

        return null;
    }

    // column of patient records

    public String heart(String Email){
        String result= "" ;
        Cursor cc = sqlDB.query(TableData.CheckUP.TableNameCH, columnOfrecords, TableData.CheckUP.Patient_ID + "=?", new String[] { Email }, null, null, null);
        if (cc != null ){
            cc.moveToFirst();
            result=result+"\n"+cc.getString(3);
            //sqlDB.close();
            return result;}

        return null;
    }

    public String temp(String Email){
        String result= "" ;
        Cursor cc = sqlDB.query(TableData.CheckUP.TableNameCH, columnOfrecords, TableData.CheckUP.Patient_ID + "=?", new String[] { Email }, null, null, null);
        if (cc != null ){
            cc.moveToFirst();
            result=result+"\n"+cc.getString(1);
            //sqlDB.close();
            return result;}

        return null;
    }
    public String pressure(String Email){
        String result= "" ;
        Cursor cc = sqlDB.query(TableData.CheckUP.TableNameCH, columnOfrecords, TableData.CheckUP.Patient_ID + "=?", new String[] { Email }, null, null, null);
        if (cc != null ){
            cc.moveToFirst();
            result=result+"\n"+cc.getString(2);
            //sqlDB.close();
            return result;}

        return null;
    }
    public String date(String Email){
        String result= "" ;
        Cursor cc = sqlDB.query(TableData.CheckUP.TableNameCH, columnOfrecords, TableData.CheckUP.Patient_ID + "=?", new String[] { Email }, null, null, null);
        if (cc != null ){
            cc.moveToFirst();
            result=result+"\n"+cc.getString(4);
            //sqlDB.close();
            return result;}

        return null;
    }


    //patient records



}
