package com.example.selfcare.selfcare;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

    /* <<!-- Intialiazation --!>> */
    ImageButton BackArrow ,Ggmail ;
    EditText  Email , Password ;
    CheckBox RememberMe ;
    TextView Forget ;
    Button BtnLogin , BtnSignUp ;
    String Username , EPassword ;
    RadioButton doctor ,patient;
    Context c ;
    CheckValid reg;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        reg =new CheckValid(this);
        sharedpreferences = getSharedPreferences("MyPREFERENCES",0);

        c =this;

      //Edit Text:-
        Email    = (EditText)findViewById(R.id.etEmail);
        Password = (EditText)findViewById(R.id.etPassword);

      //Buttons
        BackArrow = (ImageButton)findViewById(R.id.ibBack);
      //  Ggmail    = (ImageButton)findViewById(R.id.ibtngmail);
        BtnLogin  = (Button)findViewById(R.id.btnLogin);
        BtnSignUp = (Button)findViewById(R.id.btnSignUp);
        doctor = (RadioButton)findViewById(R.id.radio_Doctor);
        patient = (RadioButton)findViewById(R.id.radio_Patient);

      // Check Box
        RememberMe = (CheckBox)findViewById(R.id.cbRememberme);

      //Text View
        Forget     = (TextView)findViewById(R.id.tvForget);

        //Login Button Hit
       BtnLogin.setOnClickListener(new View.OnClickListener()
       {
           @Override
           public void onClick(View v) {
               _("Login Button hit!");

               Username = Email.getText() + "" ;
               EPassword = Password.getText() + "" ;
               _("UserName:" + Username);
               _("Password:" + EPassword);

               //if the user hit login without Writing anr thing ;
               if (Username.length() == 0 || EPassword.length() == 0)
               {
                   Toast.makeText(c ,"Please fill Email & Password ! " , Toast.LENGTH_SHORT).show();
                   return;
               }
               else
               {
                   if(doctor.isChecked())
                   {

                       String pass=  reg.vaild_Doctor(Username);

                       Toast.makeText(c ,pass , Toast.LENGTH_LONG).show();
                    //   Email.setText(pass);

                       if (pass.equals(EPassword)){

                           SharedPreferences.Editor editor = sharedpreferences.edit();
                           editor.putString( "doctor_email",Username);
                           editor.commit();

                           Toast.makeText(c, " Welcome DR ! ", Toast.LENGTH_SHORT).show();

                           Intent launchHome = new Intent(LoginActivity.this, Doctor_Taphost.class);
                           startActivity(launchHome);
                       }
                       else
                           Toast.makeText(c ,"not equal" , Toast.LENGTH_SHORT).show();

                   }





                   else if (patient.isChecked()) {

                       String pass=    reg.vaild_patient(Username);

                       Toast.makeText(c ,pass , Toast.LENGTH_SHORT).show();

                       if (pass.equals(EPassword)){

                           SharedPreferences.Editor editor = sharedpreferences.edit();
                           editor.putString( "Paient_email",Username);
                           editor.commit();
                           Toast.makeText(c, " Welcome Sir ! ", Toast.LENGTH_SHORT).show();

                           Intent launchHome = new Intent(LoginActivity.this, Patient_Taphost.class);
                           startActivity(launchHome);
                       }
                       else
                           Toast.makeText(c ,"not equal" , Toast.LENGTH_SHORT).show();


                   }
                   else
                   {
                       Toast.makeText(c ,"Something thing went wrong try again later ! " , Toast.LENGTH_SHORT).show();
                       return;

                   }
               }
           }
       });

        // Sign Up Button Hit
        BtnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(c ," Hello ! SignUp with Us  " , Toast.LENGTH_SHORT).show();
                Intent i = new Intent(LoginActivity.this, Doctor_PatientReg.class);
                startActivity(i);

            }
        });

        // Forget My Password Hit
        Forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(c ," Check Your Email Account " , Toast.LENGTH_SHORT).show();

            }
        });

        // Back Arrow Hit
        BackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //    Intent launchSignUp= new Intent(LoginActivity.this,home.class);
             //   startActivity(launchSignUp);

            }
        });

    }

    public void onRadioButtonClicked(View view){
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_Doctor:
                if (checked)

                    break;
            case R.id.radio_Patient:
                if (checked)

                    break;
        }
    }

    //wrapper message
    private void _(String s)
    {
        Log.d("MyApp","LoginActivity"+"#######"+s);
    }
}
