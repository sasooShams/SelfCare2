package com.example.selfcare.selfcare;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by sahar fathy on 2/15/2016.
 */
public class DR_RelativeNumbers extends Activity implements View.OnClickListener {

    EditText mobile1, mobile2, mobile3, mobile4;
    String mob1, mob2, mob3, mob4;
    SharedPreferences shared;

    Button btn;
    InsertData reg;
    String Email = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.relative_number);

        mobile1 = (EditText) findViewById(R.id.etphNum1);
        mobile2 = (EditText) findViewById(R.id.etphNum2);
        mobile3 = (EditText) findViewById(R.id.etPhNum3);
        mobile4 = (EditText) findViewById(R.id.etPhNum4);
        reg = new InsertData(this);

        shared = getSharedPreferences("MyPREFERENCES", 0);
        Email  =shared.getString("doctor_email", "null");
        Toast.makeText(this, Email, Toast.LENGTH_LONG).show();

        btn = (Button) findViewById(R.id.btnDone);
        try {
            btn.setOnClickListener(this);
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }



    @Override
    public void onClick(View v) {

        if (!validate()) {
            onSignupFailed();
            return;
        }
        else {


            Long id = reg.relative_doctor_insert(mob1, mob2, mob3, mob4, Email);
            Toast.makeText(getBaseContext(), id+"", Toast.LENGTH_LONG).show();

            if (id > 0)
            {
                Toast.makeText(getBaseContext(), "sucjdjd", Toast.LENGTH_LONG).show();

                Intent i = new Intent(DR_RelativeNumbers.this,Doctor_Taphost.class);
                startActivity(i);}

            else{
                Toast.makeText(getBaseContext(), "Faild", Toast.LENGTH_LONG).show();
                //  Toast.makeText(getBaseContext(), ( ff), Toast.LENGTH_LONG).show();
                onSignupFailed();
                return;}
       }
    }


    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        btn.setEnabled(true);
    }

    private boolean validate() {
        boolean valid = true;
        String phon = mobile1.getText().toString();
        String phon2 = mobile2.getText().toString();
        String phon3 = mobile3.getText().toString();
        String phon4 = mobile4.getText().toString();
        if (phon.isEmpty()) {
            mobile1.setError("invalid mobile number");
            valid = false;
        } else
            mobile2.setError(null);

        if (phon2.isEmpty()) {
            mobile2.setError("invalid mobile number");
            valid = false;
        } else
            mobile3.setError(null);

        if (phon3.isEmpty()) {
            mobile3.setError("invalid mobile number");
            valid = false;
        } else
            mobile4.setError(null);
        if (phon4.isEmpty()) {
            mobile4.setError("invalid mobile number");
            valid = false;
        } else
            mobile4.setError(null);

        if (valid) {

            mob1 =phon; //Integer.parseInt(phon);
            mob2 =phon2;//Integer.parseInt(phon2);
            mob3 =phon3;//Integer.parseInt(phon3);
            mob4 = phon4;//Integer.parseInt(phon4);
        }
        return valid;
    }
}



