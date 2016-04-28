package com.example.selfcare.selfcare;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public  class Doctor_regster extends Activity {


    Button sinUpBut; //create account button
    EditText editTextFirstName,editTextLastName,editTextPassword,editTextEmail
            ,editTextPhoneNumber, editTextWeight, editTextHeight ;
    TextView textViewAlreadyMember;

    private DatePicker datePicker;

    Button but_ds;
Context c;
    private int year, month, day;
    static final int DIALOG_ID=0;
    InsertData reg ;
    GetEmail getmail;
    SharedPreferences sharedpreferences;
    // private RadioGroup radioSexGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctorregistrationform);
        // Get References of Views
        editTextFirstName=(EditText)findViewById(R.id.drinput_FrName);
        editTextLastName=(EditText)findViewById(R.id.drinput_LaName);
        editTextPassword=(EditText)findViewById(R.id.drinput_password);
        editTextEmail=(EditText)findViewById(R.id.drinput_email);
        editTextPhoneNumber=(EditText)findViewById(R.id.drinput_phoneNum);
        editTextWeight=(EditText)findViewById(R.id.drinput_weight);
        editTextHeight=(EditText)findViewById(R.id.drinput_height);
        sinUpBut =(Button)findViewById(R.id.drbtnnext);
        c=this;
        reg =new InsertData(this);
       // getmail=new GetEmail();
        sharedpreferences = getSharedPreferences("MyPREFERENCES",0);





        final Calendar cal= Calendar.getInstance();
        year =cal.get(Calendar.YEAR);
        month =cal.get(Calendar.MONTH);
        day =cal.get(Calendar.DAY_OF_MONTH);
        showDialogOnButtonClick();

        signup();

    }
    public void showDialogOnButtonClick(){
        but_ds=(Button)findViewById(R.id.drbtnBirthDate);
        but_ds.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        showDialog(DIALOG_ID);

                    }
                }
        );
    }
    @Override
    protected Dialog onCreateDialog(int id){
        if (id==DIALOG_ID)
            return new DatePickerDialog(this,dpickerListener,year ,month,day);
        return null;
    }

    private DatePickerDialog.OnDateSetListener dpickerListener=new DatePickerDialog.OnDateSetListener(){

        public void onDateSet(DatePicker view,int year,int monthOfYear,int dayOfMonth){
            year =year;
            month =monthOfYear + 1;
            day =dayOfMonth;
            Toast.makeText(Doctor_regster.this,year +"/"+ month +"/"+ day,Toast.LENGTH_SHORT).show();
        }
    };

    String Gender ="";
    public void onRadioButtonClicked(View view){
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.drradio_male:
                if (checked)
                    Gender="male";

                break;
            case R.id.drradio_female:
                if (checked)
                    Gender="female";

                break;
        }
    }


    public void signup() {

      //  sinUpBut = (Button) findViewById(R.id.btnSignup);
        sinUpBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validate()) {
                    onSignupFailed();
                    return;
                }
                else {
                    //DataBase
                    //< do some thing >

                    try {

                        long id = reg.insert_doctor_data(FName, LName, EEmail, Passward, Mobile, BirthDate, Weights, Heights, Gender);

                        if (id>0){
                            Toast.makeText(getBaseContext(), "sucjdjd", Toast.LENGTH_LONG).show();

                            if (isNetworkAvailable()==true) {
                                Toast.makeText(getBaseContext(), isNetworkAvailable()+"", Toast.LENGTH_LONG).show();
                                BackgroundTask task = new BackgroundTask(c);
                                String method = "regester";
                                String mob = Mobile + "";
                                String weight = Weights + "";
                                String height = Heights + "";
                                task.execute(method, FName, LName, Passward, mob, BirthDate, weight, height, Gender, EEmail, "doctor");
                            }

                            Intent i = new Intent(Doctor_regster.this, DR_RelativeNumbers.class);
                            startActivity(i);
                        }



                        else
                            Toast.makeText(getBaseContext(), "Faild", Toast.LENGTH_LONG).show();
                    }
                    catch (Exception e){ Toast.makeText(getBaseContext(),e.toString(), Toast.LENGTH_LONG).show();}



                }
            }

        });}

    // check connection of internet
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        sinUpBut.setEnabled(true);
    }

    String FName;
    String LName;
    String Passward;
    String EEmail;
    int Mobile;
    int Weights;
    int Heights;
    String BirthDate;
    public boolean validate() {
        boolean valid = true;
        String FirstName = editTextFirstName.getText().toString();

        String LastName = editTextLastName.getText().toString();

        String Password = editTextPassword.getText().toString();

        String Email = editTextEmail.getText().toString();

        String PhoneNumber = editTextPhoneNumber.getText().toString();

        String Weight = editTextWeight.getText().toString();

        String Height = editTextHeight.getText().toString();


        if (FirstName.isEmpty() || FirstName.length() < 3) {
            editTextFirstName.setError("at least 3 characters");
            valid = false;
        } else {
            editTextFirstName.setError(null);
        }
        if (LastName.isEmpty()) {
            editTextLastName.setError("invalid Last name");
            valid = false;
        } else {
            editTextLastName.setError(null);
        }
        if (Email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            editTextEmail.setError("enter a valid email address");
            valid = false;
        } else {
            editTextEmail.setError(null);
        }
        if (Password.isEmpty() || Password.length() < 4 || Password.length() > 10) {
            editTextPassword.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            editTextPassword.setError(null);
        }
        if (PhoneNumber.isEmpty()) {
            editTextPhoneNumber.setError("invalid mobile number");
            valid = false;
        } else {
            editTextPhoneNumber.setError(null);
        }
        if (Weight.isEmpty()) {
            editTextWeight.setError("invalid weight");
            valid = false;
        } else {
            editTextWeight.setError(null);
        }
        if (Height.isEmpty()) {
            editTextHeight.setError("invalid height");
            valid = false;
        } else {
            editTextHeight.setError(null);
        }

        if (valid){
            FName =FirstName;
            LName=LastName;
            Passward =Password;
            EEmail =Email;
            Mobile=Integer.parseInt(PhoneNumber);
            Weights =Integer.parseInt(Weight);
            Heights =Integer.parseInt(Height);
            BirthDate ="" + year + "-" + month + "-" + day + "";

            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString( "doctor_email",EEmail);
            editor.commit();

        }

        return valid;
    }

}


