package com.example.selfcare.selfcare;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by sahar fathy on 2/1/2016.
 */
public class Doctor_PatientReg extends Activity {

    Button BtnDoctor , BtnPatient ;
    Context c ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dp_register);

        //Buttons
        BtnDoctor = (Button) findViewById(R.id.regdr);
        BtnPatient = (Button) findViewById(R.id.regpa);
        c = this;

        BtnDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //if the user hit login

                Toast.makeText(c, " Welcome ! ", Toast.LENGTH_SHORT).show();
                try{

                   // Intent launchHome = new Intent(Doctor_PatientReg.this, Doctor_IDphoto.class);
                    Intent launchHome = new Intent(Doctor_PatientReg.this, Doctor_regster.class);
                startActivity(launchHome);}
                catch (Exception e){
                    Toast.makeText(c, e.toString(), Toast.LENGTH_SHORT).show();//Doctor_regster

                }
            }

        });

        BtnPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //if the user hit login

                Toast.makeText(c, " Welcome ! ", Toast.LENGTH_SHORT).show();
                Intent launchHome = new Intent(Doctor_PatientReg.this,Registrion.class);
                startActivity(launchHome);
            }

        });


    }
}