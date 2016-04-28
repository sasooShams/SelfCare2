package com.example.selfcare.selfcare;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Doctor_patient extends Activity implements View.OnClickListener {

    Button btn;
    Intent i;
Context c;
    EditText Fname,Lname,address,Email,Phone,ID;
    String fname,lname,email,addr;
    int D_Id,phone;
    InsertData reg ;
    SharedPreferences shared;
    String Email_p="" ;
    GetEmail getmails;
    public static final String DEf="N/A";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctors);
c=this;
        Fname =(EditText)findViewById(R.id.etphNum1);
        Lname =(EditText)findViewById(R.id.etphNum2);
        address =(EditText)findViewById(R.id.etAdderss);
        Phone =(EditText)findViewById(R.id.etPhNum3);
        Email =(EditText)findViewById(R.id.etdocEmail);
        ID =(EditText)findViewById(R.id.doctorId);
        reg=new InsertData(this);
        shared=getSharedPreferences("MyPREFERENCES",0);
        getmails=new GetEmail();
        Email_p  =shared.getString("Paient_email", "null");


       // Email_p = getmails.getEmail();
Toast.makeText(getBaseContext(), Email_p, Toast.LENGTH_LONG).show();


        btn=(Button)findViewById(R.id.btnNext);
        btn.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        fname=Fname.getText().toString();
        lname=Lname.getText().toString();
        addr=address.getText().toString();
        email=Email.getText().toString();
        phone= Integer.parseInt(Phone.getText().toString());
        D_Id=Integer.parseInt(ID.getText().toString());

//(String fname ,String lname,String email,int phone,String add,int ID)
        long id=   reg.pDoctor_insert(fname,lname,email,phone,addr,D_Id,Email_p);
        if (id>0)

        {

            Login_server lg =new Login_server(c);
            Toast.makeText(getBaseContext(), "sucjdjd", Toast.LENGTH_LONG).show();
            if (isNetworkAvailable()==true) {
                Toast.makeText(getBaseContext(), isNetworkAvailable() + "", Toast.LENGTH_LONG).show();

                lg.execute("doc_email", email, Email_p);
            }

            i= new Intent(this,Relative_number.class);
            startActivity(i);
        }
        else
            Toast.makeText(getBaseContext(), "Faild", Toast.LENGTH_LONG).show();



    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
