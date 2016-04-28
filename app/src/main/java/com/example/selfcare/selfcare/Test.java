package com.example.selfcare.selfcare;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class Test extends AppCompatActivity {
    GetDataOfPatient reg ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        TextView tv =(TextView)findViewById(R.id.TV5);
        reg = new GetDataOfPatient(this);
       // Toast.makeText(this,reg.date("sara@yahoo.com"),Toast.LENGTH_LONG).show();

        // reg.date("sara@yahoo.com");
        String ss ="";

           // ss+=reg.date("sara@yahoo.com");

        tv.setText(ss);
        }



}
