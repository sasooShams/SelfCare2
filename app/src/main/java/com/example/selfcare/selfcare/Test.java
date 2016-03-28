package com.example.selfcare.selfcare;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class Test extends AppCompatActivity {
    InsertData reg ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        TextView tv =(TextView)findViewById(R.id.TV5);
        reg = new InsertData(this);
        reg.getData();
        String ss ="";
        try {
            ss=reg.getData();


        }catch (Exception e){}
        finally {
            tv.setText(ss);
        }

    }

}
