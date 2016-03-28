package com.example.selfcare.selfcare;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by sahar fathy on 2/1/2016.
 */

public class Doctor_Taphost extends AppCompatActivity {

    private MenuItem mSearchAction;
    private MenuItem logOut;
    private MenuItem sett;
    private MenuItem userProf;
    private boolean isSearchOpened = false;
    private boolean isLog = false;
    private boolean isSettOpened = false;
    private boolean isProfOpened = false;
    private EditText edtSeach;
    SharedPreferences sharedpreferences;
    String email = "";
    GetDataOfDoctor doctor;

    EditText Heart;
    EditText Temp;
    EditText pressure;

     TabHost tabHost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_taphost);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setTitle("SelfCare");
        sharedpreferences = getSharedPreferences("MyPREFERENCES", 0);
        email = sharedpreferences.getString("doctor_email", "null");
        doctor = new GetDataOfDoctor(this);

        //tabs
        tabHost =(TabHost)findViewById(R.id.doctabHost);
        tabHost.setup();
        TabHost.TabSpec tabSpec = tabHost.newTabSpec("Measurements");
        tabSpec.setContent(R.id.dr_measurements);
        tabSpec.setIndicator("Measurements");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("Requestes");
        tabSpec.setContent(R.id.dr_Requestes);
        tabSpec.setIndicator("Requestes");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("Notification");
        tabSpec.setContent(R.id.dr_Notification);
        tabSpec.setIndicator("Notification");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("Messages");
        tabSpec.setContent(R.id.dr_Messages);
        tabSpec.setIndicator("Messages");
        tabHost.addTab(tabSpec);

        //tab1
        Heart = (EditText) findViewById(R.id.drhartrateres);
        Temp = (EditText) findViewById(R.id.drtemperatureres);
        pressure = (EditText) findViewById(R.id.drpresserres);

        /////heartbeats test
        Heart.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                /////Empty
            }

            @Override
            public void beforeTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
                /////empty
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                //fun to get data by bluetooth
                try {
                    String gen = doctor.gendre(email);
                    String RNum1 = doctor.p_relative_mob1(email);
                    String RNum2 = doctor.p_relative_mob2(email);
                    String RNum3 = doctor.p_relative_mob3(email);
                    String RNum4 = doctor.p_relative_mob4(email);
                    double heart = Double.parseDouble(Heart.getText().toString());
                    //if(16<=age && age<=45){
                    if (60 <= heart && heart <= 100) {
                        new AlertDialog.Builder(Doctor_Taphost.this)
                                .setTitle("Heart Beats Alert")
                                .setIcon(R.drawable.ic_heart)
                                .setMessage("Your heart beats rate is normal..check your all records")
                                .setCancelable(false)
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(Doctor_Taphost.this, Doctor_Profile.class);
                                        startActivity(intent);
                                    }
                                }).create().show();
                    }
                    if (80 <= heart && heart <= 100) {
                        if (gen == "female") {
                            new AlertDialog.Builder(Doctor_Taphost.this)
                                    .setTitle("Heart Beats Alert")
                                    .setIcon(R.drawable.ic_heart)
                                    .setMessage("Your heart beats rate is normal if you are pregnant ..check your all records")
                                    .setCancelable(false)
                                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(Doctor_Taphost.this, Doctor_Profile.class);
                                            startActivity(intent);
                                        }
                                    }).create().show();
                        }
                    } else if (70 <= heart && heart <= 100) {
                        new AlertDialog.Builder(Doctor_Taphost.this)
                                .setTitle("Heart Beats Alert")
                                .setIcon(R.drawable.ic_heart)
                                .setMessage("Your Heartbeats rate is normal..check your all records")
                                .setCancelable(false)
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(Doctor_Taphost.this, Doctor_Profile.class);
                                        startActivity(intent);
                                    }
                                }).create().show();
                    }
                    if (100 < heart && heart <= 150) {
                        new AlertDialog.Builder(Doctor_Taphost.this)
                                .setTitle("Heart Beats Alert")
                                .setIcon(R.drawable.ic_heart)
                                .setMessage("Your Heartbeats rate result of your Physical exertion,repeat heartbeats checking again in your relaxation state ..check your all records")
                                .setCancelable(false)
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(Doctor_Taphost.this, Doctor_Profile.class);
                                        startActivity(intent);
                                    }
                                }).create().show();
                    }
                    if (150 < heart || heart < 60) {
                        try { //Relatives number usually entered here
                            String phoneNumberReciver1 = RNum1;
                            String phoneNumberReciver2 = RNum2;
                            String phoneNumberReciver3 = RNum3;
                            String phoneNumberReciver4 = RNum4;
                            String message = "Here it's SelfCare Alert , Danger case.. help me!!! ";
                            SmsManager sms = SmsManager.getDefault();
                            sms.sendTextMessage(phoneNumberReciver1, null, message, null, null);
                            sms.sendTextMessage(phoneNumberReciver2, null, message, null, null);
                            sms.sendTextMessage(phoneNumberReciver3, null, message, null, null);
                            sms.sendTextMessage(phoneNumberReciver4, null, message, null, null);
                            Toast.makeText(getApplicationContext(),
                                    "SMS Alert sent to your Relatives",
                                    Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(),
                                    "SMS failed, please try again later!",
                                    Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                        new AlertDialog.Builder(Doctor_Taphost.this)
                                .setTitle("Heart Beats Alert")
                                .setIcon(R.drawable.ic_heart)
                                .setMessage("Danger case ,connect to your doctor quickly ..check your all records")
                                .setCancelable(false)
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    /* Intent intent=new Intent(this,//massage.class);
                                    startActivity(intent); */
                                    }
                                }).create().show();
                    } else {
                        Toast.makeText(Doctor_Taphost.this, "Error, invalid Heartbeats", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                }
            }
        });
        //////// pressure test
        ///// pressure rate test //test systolic pressure only
        pressure.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                /////Empty
            }

            @Override
            public void beforeTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
                /////empty
            }
            ///this for test diastolic pressure
            /*double Dia = Double.parseDouble(diaPres.getText().toString());
            if(Dia <= 60 )
            (60 < Dia && Dia < 80) &&
            (80 <= Dia && Dia < 85) &&
            (85 <= Dia && Dia < 90) &&
            (90 <= Dia && Dia < 100) &&
            (100 <= Dia && Dia < 110) &&
            Dia > 110 &&
            * */
            @Override
            public void afterTextChanged(Editable arg0) {
                //fun get data by bluetooth
                try {
                    String RNum1 =doctor.p_relative_mob1(email);
                    String RNum2 =doctor.p_relative_mob2(email);
                    String RNum3 =doctor.p_relative_mob3(email);
                    String RNum4 =doctor.p_relative_mob4(email);
                    double Sys = Double.parseDouble(pressure.getText().toString());
                    if (80 < Sys && Sys <= 100) {
                        new AlertDialog.Builder(Doctor_Taphost.this)
                                .setTitle("Pressure Alert")
                                .setIcon(R.drawable.ic_pre)
                                .setMessage("Your pressure is very low..connect to your doctor")
                                .setCancelable(false)
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Intent intent=new Intent(this,//profile.class); startActivity(intent);
                                    }
                                }).create().show();
                    }
                    if (100 < Sys && Sys < 120) {
                        new AlertDialog.Builder(Doctor_Taphost.this)
                                .setTitle("Pressure Alert")
                                .setIcon(R.drawable.ic_pre)
                                .setMessage("Your pressure is low..check all your records")
                                .setCancelable(false)
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent=new Intent(Doctor_Taphost.this,Doctor_Profile.class);
                                        startActivity(intent);
                                    }
                                }).create().show();
                    }
                    if (120 <= Sys && Sys < 130) {
                        new AlertDialog.Builder(Doctor_Taphost.this)
                                .setTitle("Pressure Alert")
                                .setIcon(R.drawable.ic_pre)
                                .setMessage("Your pressure is normal..check all your records")
                                .setCancelable(false)
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent=new Intent(Doctor_Taphost.this,Doctor_Profile.class);
                                        startActivity(intent);
                                    }
                                }).create().show();
                    }
                    if (130 <= Sys && Sys < 140) {
                        new AlertDialog.Builder(Doctor_Taphost.this)
                                .setTitle("Pressure Alert")
                                .setIcon(R.drawable.ic_pre)
                                .setMessage("Your pressure is high..check all your records")
                                .setCancelable(false)
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent=new Intent(Doctor_Taphost.this,Doctor_Profile.class);
                                        startActivity(intent);
                                    }
                                }).create().show();
                    }
                    if (140 <= Sys && Sys < 160) {
                        new AlertDialog.Builder(Doctor_Taphost.this)
                                .setTitle("Pressure Alert")
                                .setIcon(R.drawable.ic_pre)
                                .setMessage("Your pressure is very high..connect to your doctor")
                                .setCancelable(false)
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent=new Intent(Doctor_Taphost.this,Doctor_Profile.class);
                                        startActivity(intent);
                                    }
                                }).create().show();
                    }
                    if (160 <= Sys && Sys < 180) {
                        try {  //Relatives number usually entered here
                            String phoneNumberReciver1 = RNum1;
                            String phoneNumberReciver2 = RNum2;
                            String phoneNumberReciver3 = RNum3;
                            String phoneNumberReciver4 = RNum4;
                            String message = "Here it's SelfCare Alert , Danger case.. help me!!! ";
                            SmsManager sms = SmsManager.getDefault();
                            sms.sendTextMessage(phoneNumberReciver1, null, message, null, null);
                            sms.sendTextMessage(phoneNumberReciver2, null, message, null, null);
                            sms.sendTextMessage(phoneNumberReciver3, null, message, null, null);
                            sms.sendTextMessage(phoneNumberReciver4, null, message, null, null);
                            Toast.makeText(getApplicationContext(),
                                    "SMS Alert sent to your Relatives",
                                    Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(),
                                    "SMS failed, please try again later!",
                                    Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                        new AlertDialog.Builder(Doctor_Taphost.this)
                                .setTitle("Pressure Alert")
                                .setIcon(R.drawable.ic_pre)
                                .setMessage("Your pressure is extremely high..connect to your doctor doctor quickly")
                                .setCancelable(false)
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Intent intent=new Intent(this,//massage.class); startActivity(intent);
                                    }
                                }).create().show();
                    }
                    if (Sys > 180) {
                        try { //Relatives number usually entered here
                            String phoneNumberReciver1 = RNum1;
                            String phoneNumberReciver2 = RNum2;
                            String phoneNumberReciver3 = RNum3;
                            String phoneNumberReciver4 = RNum4;
                            String message = "Here it's SelfCare Alert , Danger case.. help me!!! ";
                            SmsManager sms = SmsManager.getDefault();
                            sms.sendTextMessage(phoneNumberReciver1, null, message, null, null);
                            sms.sendTextMessage(phoneNumberReciver2, null, message, null, null);
                            sms.sendTextMessage(phoneNumberReciver3, null, message, null, null);
                            sms.sendTextMessage(phoneNumberReciver4, null, message, null, null);
                            Toast.makeText(getApplicationContext(),
                                    "SMS Alert sent to your Relatives",
                                    Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(),
                                    "SMS failed, please try again later!",
                                    Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                        new AlertDialog.Builder(Doctor_Taphost.this)
                                .setTitle("Pressure Alert")
                                .setIcon(R.drawable.ic_pre)
                                .setMessage("Danger case ,Your pressure is extremely high..connect to your doctor quickly")
                                .setCancelable(false)
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Intent intent=new Intent(this,//massage.class); startActivity(intent);
                                    }
                                }).create().show();
                    } else {
                        Toast.makeText(Doctor_Taphost.this, "Error, invalid pressure", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                }
            }
        });
        ///////// temperature test
        Temp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
              //fun get data by bluetooth
                try {
                    String gen =doctor.gendre(email);
                    String RNum1 =doctor.p_relative_mob1(email);
                    String RNum2 =doctor.p_relative_mob2(email);
                    String RNum3 =doctor.p_relative_mob3(email);
                    String RNum4 =doctor.p_relative_mob4(email);

                    double Tem = Double.parseDouble(Temp.getText().toString());
                    if ((36.8 <= Tem && Tem <= 37.2)) {
                        new AlertDialog.Builder(Doctor_Taphost.this)
                                .setTitle("Temperature Alert")
                                .setIcon(R.drawable.ic_temper)
                                .setMessage("Your temperature is normal..check your all records")
                                .setCancelable(false)
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent=new Intent(Doctor_Taphost.this,Doctor_Profile.class);
                                        startActivity(intent);
                                    }
                                }).create().show();
                    }
                    if (37.2 < Tem && Tem < 37.5) {
                        new AlertDialog.Builder(Doctor_Taphost.this)
                                .setTitle("Temperature Alert")
                                .setIcon(R.drawable.ic_temper)
                                .setMessage("A slight rise in temperature,maybe because of the High temperature air or that you made a lot of exercises ..check your all records ")
                                .setCancelable(false)
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent=new Intent(Doctor_Taphost.this,Doctor_Profile.class);
                                        startActivity(intent);
                                    }
                                }).create().show();
                    }
                    if (37.5 < Tem && Tem <= 38.3) {
                        new AlertDialog.Builder(Doctor_Taphost.this)
                                .setTitle("Temperature Alert")
                                .setIcon(R.drawable.ic_temper)
                                .setMessage("You catch a cold ..please connect to you doctor ..check your all records ")
                                .setCancelable(false)
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent=new Intent(Doctor_Taphost.this,Doctor_Profile.class);
                                        startActivity(intent);
                                    }
                                }).create().show();
                    }
                    if (38.3 < Tem && Tem <= 41.8) {
                        try { //Relatives number usually entered here
                            String phoneNumberReciver1 = RNum1;
                            String phoneNumberReciver2 = RNum2;
                            String phoneNumberReciver3 = RNum3;
                            String phoneNumberReciver4 = RNum4;
                            String message = "Here it's SelfCare Alert , Danger case.. help me!!! ";
                            SmsManager sms = SmsManager.getDefault();
                            sms.sendTextMessage(phoneNumberReciver1, null, message, null, null);
                            sms.sendTextMessage(phoneNumberReciver2, null, message, null, null);
                            sms.sendTextMessage(phoneNumberReciver3, null, message, null, null);
                            sms.sendTextMessage(phoneNumberReciver4, null, message, null, null);
                            Toast.makeText(getApplicationContext(),
                                    "SMS Alert sent to your Relatives",
                                    Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(),"SMS failed, please try again later!",Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                        new AlertDialog.Builder(Doctor_Taphost.this)
                                .setTitle("Temperature Alert")
                                .setIcon(R.drawable.ic_temper)
                                .setMessage("danger case.. A slight rise in temperature,you have fever .. connect to your doctor quickly")
                                .setCancelable(false)
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    /*Intent intent=new Intent(this,//massage.class);
                                        startActivity(intent);
                                */
                                    }
                                }).create().show();
                    }
                    if (Tem == 37.5) {
                        if (gen=="female"){
                            new AlertDialog.Builder(Doctor_Taphost.this)
                                    .setTitle("Temperature Alert")
                                    .setIcon(R.drawable.ic_temper)
                                    .setMessage("If you on day 14 from your PMS..detecting ovulation is possible for the best chance of getting pregnant, please check your temperature after 30 minutes ..check your all records ")
                                    .setCancelable(false)
                                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        /*
                                Intent intent=new Intent(this,//massage.class);
                                        startActivity(intent);
                                */
                                        }
                                    }).create().show();
                        }
                        else{
                            new AlertDialog.Builder(Doctor_Taphost.this)
                                    .setTitle("Temperature Alert")
                                    .setIcon(R.drawable.ic_temper)
                                    .setMessage("MayBe You catch a cold ..please connect to you doctor ..check your all records ")
                                    .setCancelable(false)
                                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent=new Intent(Doctor_Taphost.this,Doctor_Profile.class);
                                            startActivity(intent);
                                        }
                                    }).create().show();
                        }
                    } else {
                        Toast.makeText(Doctor_Taphost.this, "Error, invalid temperature", Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){

                }

            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        mSearchAction = menu.findItem(R.id.search_id);
        logOut = menu.findItem(R.id.sign_id);
        sett =menu.findItem(R.id.setting_id);
        userProf =menu.findItem(R.id.profile_id);
        return true;
    }

    /// icons clicked
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile_id:
                Toast.makeText(getApplicationContext(), "Your profile is selected", Toast.LENGTH_LONG).show();
                gotoProf();
                return true;

            case R.id.setting_id:
                Toast.makeText(getApplicationContext(), "Setting icon is selected", Toast.LENGTH_LONG).show();
                gotoSett();
                return true;

            case R.id.sign_id:
                open();
                // Toast.makeText(getApplicationContext(), "Sign out", Toast.LENGTH_LONG).show();
                return true;

            case R.id.search_id:
                handleMenuSearch();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /// search function
    protected void handleMenuSearch() {
        ActionBar action = getSupportActionBar(); //get the actionbar

        if (isSearchOpened)  //test if the search is open
        {
            action.setDisplayShowCustomEnabled(false); //disable a custom view inside the actionbar
            action.setDisplayShowTitleEnabled(true); //show the title in the action bar
            //hides the keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(edtSeach.getWindowToken(), 0);
            mSearchAction.setIcon(getResources().getDrawable(R.drawable.ic_search));//add the search icon in the action bar
            isSearchOpened = false;
        } else { //open the search entry
            action.setDisplayShowCustomEnabled(true); //enable it to display a custom view in the action bar
            action.setCustomView(R.layout.icons);//add the custom view
            action.setDisplayShowTitleEnabled(false); //hide the title
            edtSeach = (EditText) action.getCustomView().findViewById(R.id.edtSearch); //the text editor
            //this is a listener to do a search when the user clicks on search button
            edtSeach.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        doSearch();
                        return true;
                    }
                    return false;
                }
            });
            edtSeach.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE); //open the keyboard focused in the edtSearch
            imm.showSoftInput(edtSeach, InputMethodManager.SHOW_IMPLICIT);
            mSearchAction.setIcon(getResources().getDrawable(R.drawable.ic_close)); //add the close icon
            isSearchOpened = true;
        }
    }

    @Override
    public void onBackPressed() {
        if (isSearchOpened) {
            handleMenuSearch();
            return;
        }
        super.onBackPressed();
    }

    private void doSearch() {
/////////////////////////////// deal with server
    }

    // log out function
    protected void open() {
        ActionBar action = getSupportActionBar();
        if (isLog) {
            action.setDisplayShowCustomEnabled(false); //disable a custom view inside the actionbar
            action.setDisplayShowTitleEnabled(true);
            mSearchAction.setIcon(getResources().getDrawable(R.drawable.ic_shutdown));
            isLog = false;
        } else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Are you sure that You want to Log out");

            alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    Toast.makeText(Doctor_Taphost.this, "Sign out", Toast.LENGTH_LONG).show();

                    //// switch to register or user screen
                      Intent intent=new Intent(Doctor_Taphost.this,LoginActivity.class);
                     startActivity(intent);

                }
            });
            alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

    protected void gotoSett() { //settings screen
        ActionBar action = getSupportActionBar();
        if (isSettOpened)  //test if setting icon selected
        {
            action.setDisplayShowCustomEnabled(false);
            action.setDisplayShowTitleEnabled(true);
            isSettOpened = false;
        }
        else { //open the setting activity
            /*
            Intent intent = new Intent(this,//.class);
            startActivity(intent);
                   */
        }
    }
    private void gotoProf(){ //profile screen
        ActionBar action = getSupportActionBar();
        if (isProfOpened)  //test if the profile icon selected
        {
            action.setDisplayShowCustomEnabled(false);
            action.setDisplayShowTitleEnabled(true);
            isProfOpened = false;
        }
        else { //open the profile activity
            Intent intent = new Intent(this,Doctor_Profile.class);
            startActivity(intent);

        }

    }


}
