package com.example.selfcare.selfcare;

import android.provider.BaseColumns;

/**
 * Created by h on 21/02/2016.
 */
public class TableData {
    TableData(){}

    public static abstract class Patient_Regestration implements BaseColumns
    {

        public static final String TableName1="reg_patient_inf";
        public static final String Fname="FirstName";
        public static final String Lname="LastName";
        public static final String Email ="Email";
        public static final String Passward = "passward";
        public static final String Moblile ="Mobile";
        public static final String BirthDate ="BirthDate";
        public static final String Height="Height";
        public static final String Weight = "Weight";
        public static final String Gender ="Gender";
       ;


        //SQL statement of patient regestration
        public static final String query ="CREATE TABLE "+TableName1+" ( "
                + Fname+"  varchar(255) NOT NULL, "
                + Lname+"  varchar(255) NOT NULL,"
                + Email+"  varchar(255) PRIMARY KEY NOT NULL ,"
                + Passward+"  varchar(255) NOT NULL,"
                + Moblile+"  INTEGER NOT NULL,"
                + Weight+"  INTEGER NOT NULL,"
                + Height+"  INTEGER NOT NULL,"
                + Gender+"  varchar(255) NOT NULL,"
                + BirthDate+"  DATE );";


    }

    public static abstract class Patient_Relative implements BaseColumns
    {//patient_relative
        public static final String TableName2="patients_relatives";
        public static final String RMoblile_1 ="Mobile1";
        public static final String RMoblile_2 ="Mobile2";
        public static final String RMoblile_3 ="Mobile3";
        public static final String RMoblile_4 ="Mobile4";
        public static final String relative_id ="relative_id";
        public static final String Patient_ID ="Patient_ID";


        //SQL statement of relative of patient mobile numbers
        public static final String query ="CREATE TABLE "+TableName2+" ("+
                relative_id +"  INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT ,"
                + Patient_ID +" varchar(255) NOT NULL,"
                + RMoblile_1+"  varchar(255) NOT NULL,"
                + RMoblile_2+"  varchar(255) NOT NULL,"
                + RMoblile_3+"  varchar(255) NOT NULL,"
                + RMoblile_4+"  varchar(255) NOT NULL ," +
                "FOREIGN KEY (" +Patient_ID+") REFERENCES "+ Patient_Regestration.TableName1+"("+ Patient_Regestration.Email+"));";


    }

    public static abstract class Patient_Doctor  implements BaseColumns
        
    {
        public static final String TableName3="patient_doctor";
        public static final String DFname="FirstName";
        public static final String DLname="LastName";
        public static final String DEmail ="Email";
        public static final String DMoblile ="Mobile";
        public static final String DAddress="Address";
        public static final String D_ID="ID_Doctor";
        public static final String Patient_ID ="Patient_ID";

        //SQL statement of Doctor of patient information

        public static final String query ="CREATE TABLE "+TableName3+" ("+D_ID+" INTEGER  PRIMARY KEY, " +
                  Patient_ID +" varchar(255) NOT NULL,"
                + DFname+"  varchar(255) NOT NULL, "
                + DLname+"  varchar(255) NOT NULL,"
                + DEmail+"  varchar(255) NOT NULL ,"
                + DAddress+"  varchar(255),"
                + DMoblile+"  INTEGER NOT NULL," +
                "FOREIGN KEY (" +Patient_ID+") REFERENCES "+ Patient_Regestration.TableName1+"("+ Patient_Regestration.Email+"));";





    }

    public static abstract class CheckUP implements BaseColumns{

        public static final String TableNameCH="CheckUPs";
        public static final String Date="Date";
        public static final String Heart="Heart";
        public static final String Pressure ="Pressure";
        public static final String Temperture ="Temperture";
        public static final String Comments ="Comments";
        public static final String Patient_ID ="Patient_ID";//forgenKey
        public static final String ID ="ID";

        public static final String query ="CREATE TABLE "+TableNameCH+" ("+
                 ID +" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT ,"
                +Patient_ID +" varchar(255) ,"
                + Date+"  DATETIME  , "
                + Heart+"  varchar(255) ,"
                + Pressure+"  varchar(255) ,"
                + Temperture+"  varchar(255) ,"
                +
                "FOREIGN KEY (" +Patient_ID+") REFERENCES "+ Patient_Regestration.TableName1+"("+ Patient_Regestration.Email+"));";

    }

    public static abstract class doctor_Regestration implements BaseColumns
    {

        public static final String TableNamedoc="reg_doctor_info";
        public static final String Fname="FirstName";
        public static final String Lname="LastName";
        public static final String Email ="Email";
        public static final String Passward = "passward";
        public static final String Moblile ="Mobile";
        public static final String BirthDate ="BirthDate";
        public static final String Height="Height";
        public static final String Weight = "Weight";
        public static final String Gender ="Gender";
        ;


        //SQL statement of patient regestration
        public static final String query ="CREATE TABLE "+TableNamedoc+" ( "
                + Fname+"  varchar(255) NOT NULL, "
                + Lname+"  varchar(255) NOT NULL,"
                + Email+"  varchar(255) PRIMARY KEY NOT NULL ,"
                + Passward+"  varchar(255) NOT NULL,"
                + Moblile+"  INTEGER NOT NULL,"
                + Weight+"  INTEGER NOT NULL,"
                + Height+"  INTEGER NOT NULL,"
                + Gender+"  varchar(255) NOT NULL,"
                + BirthDate+"  DATE );";


    }

    public static abstract class doctor_Relative implements BaseColumns
    {
        //patient_relative
        public static final String TableNamedocrel="doctor_relatives";
        public static final String RMoblile_1 ="Mobile1";
        public static final String RMoblile_2 ="Mobile2";
        public static final String RMoblile_3 ="Mobile3";
        public static final String RMoblile_4 ="Mobile4";
        public static final String relative_id ="relative_id";
        public static final String doctor_ID ="doctor_ID";


        //SQL statement of relative of patient mobile numbers
        public static final String query ="CREATE TABLE "+TableNamedocrel+" ("+
                relative_id +"  INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT ,"
                + doctor_ID +" varchar(255) NOT NULL,"
                + RMoblile_1+"  varchar(255) NOT NULL,"
                + RMoblile_2+"  varchar(255) NOT NULL,"
                + RMoblile_3+"  varchar(255) NOT NULL,"
                + RMoblile_4+"  varchar(255) NOT NULL ," +
                "FOREIGN KEY (" +doctor_ID+") REFERENCES "+ doctor_Regestration.TableNamedoc+"("+ doctor_Regestration.Email+"));";


    }

    public static abstract class CheckUPDR implements BaseColumns{

        public static final String TableNameCHDoc="CheckUPDoctor";
        public static final String Date="Date";
        public static final String Heart="Heart";
        public static final String Pressure ="Pressure";
        public static final String Temperture ="Temperture";
       // public static final String Comments ="Comments";
        public static final String doctor_ID ="Patient_ID";//forgenKey
        public static final String ID ="ID";

        public static final String query ="CREATE TABLE "+TableNameCHDoc+" ("
                +doctor_ID +" varchar(255) ,"
                + Date+"  DATETIME  , "
                + Heart+"  varchar(255) ,"
                + Pressure+"  varchar(255) ,"
                + Temperture+"  varchar(255) ,"
                +
                "FOREIGN KEY (" +doctor_ID+") REFERENCES "+ doctor_Regestration.TableNamedoc+"("+ doctor_Regestration.Email+"));";

    }

}
