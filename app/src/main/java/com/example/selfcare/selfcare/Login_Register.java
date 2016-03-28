package com.example.selfcare.selfcare;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Created by sahar fathy on 2/1/2016.
 */
public class Login_Register extends Activity {


    Button BtnLogin , BtnSignUp ;
    Context c ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginorregister);

        //Buttons
        BtnLogin  = (Button)findViewById(R.id.frag4log);
        BtnSignUp = (Button)findViewById(R.id.frag4reg);
        c =this;

        BtnLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                //if the user hit login

                    Toast.makeText(c ," Welcome ! " , Toast.LENGTH_SHORT).show();
                    Intent launchHome = new Intent(Login_Register.this, LoginActivity.class);
                    startActivity(launchHome);
                }

        });

        BtnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //if the user hit login

                Toast.makeText(c, " Welcome ! ", Toast.LENGTH_SHORT).show();
                Intent launchHome = new Intent(Login_Register.this, Doctor_PatientReg.class);
                startActivity(launchHome);
            }

        });
    }
}
