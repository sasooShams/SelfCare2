package com.example.selfcare.selfcare;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


/**
 * Created by sahar fathy on 2/1/2016.
 */
public class Patient_Taphost extends AppCompatActivity implements View.OnClickListener {

    private MenuItem mSearchAction;
    private MenuItem logOut;
    private MenuItem sett;
    private MenuItem userProf;
    private boolean isSearchOpened = false;
    private boolean isLog = false;
    private boolean isSettOpened = false;
    private boolean isProfOpened = false;
    private EditText edtSeach;

    private int mInterval = 95000; // 5 seconds by default, can be changed later
    private Handler mHandler;
    TabHost PatabHost;
    EditText Heart, Temp, pressure, comment;
    Button save;
    InsertData reg;
    SharedPreferences sharedpreferences;
    String email = "";
    Build args;

    DialogFragment newFragment;
    GetDataOfPatient patient;
//belutooth side
    private static final String TAG = "bluetooth2";



    final int RECIEVE_MESSAGE = 1;        // Status  for Handler
    private BluetoothAdapter BA = null;
    private BluetoothSocket btSocket = null;
    private StringBuilder sb = new StringBuilder();
    ImageButton start;

    private ConnectedThread mConnectedThread;

    // SPP UUID service
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    //ده الماك ادريس الخاص بالموديول بتاعنا
    private static String address = "20:15:10:19:69:37";

    BroadcastReceiver receiver;
    String tepr="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_taphost);
       /* Toolbar toolbar = (Toolbar) findViewById(R.id.Logtoolbar);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setTitle("SelfCare");*/

        reg = new InsertData(this);//database
        sharedpreferences = getSharedPreferences("MyPREFERENCES", 0);
        email = sharedpreferences.getString("Paient_email", "null");
        patient = new GetDataOfPatient(this);
        args = new Build();



// tabs
        PatabHost = (TabHost) findViewById(R.id.patabHost);
        PatabHost.setup();
        TabHost.TabSpec tabSpec = PatabHost.newTabSpec("Measurements");
        tabSpec.setContent(R.id.measurements);
        tabSpec.setIndicator("Measurements");
        PatabHost.addTab(tabSpec);

        tabSpec = PatabHost.newTabSpec("Notification");
        tabSpec.setContent(R.id.Notification);
        tabSpec.setIndicator("Notification");
        PatabHost.addTab(tabSpec);

        tabSpec = PatabHost.newTabSpec("Messages");
        tabSpec.setContent(R.id.Messages);
        tabSpec.setIndicator("Messages");
        PatabHost.addTab(tabSpec);

        tabSpec = PatabHost.newTabSpec("Profile");
        tabSpec.setContent(R.id.Profile);
        tabSpec.setIndicator("Profile");
        PatabHost.addTab(tabSpec);

        /// tab 1
        Heart = (EditText) findViewById(R.id.Heart);
        Temp = (EditText) findViewById(R.id.Temp);
        pressure = (EditText) findViewById(R.id.pressure);
        comment = (EditText) findViewById(R.id.Comments);
        save = (Button) findViewById(R.id.Accepttbtn);
        Toast.makeText(getBaseContext(), email, Toast.LENGTH_LONG).show();

        save.setOnClickListener(this);
        /*
        BA = BluetoothAdapter.getDefaultAdapter();
        turnOn();
        try {
            mHandler = new Handler();
            startRepeatingTask();
        }
        catch (Exception e){
            Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG).show();
        }*/
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
                try{
                    String gen =patient.gendre(email);
                    String RNum1 =patient.p_relative_mob1(email);
                    String RNum2 =patient.p_relative_mob2(email);
                    String RNum3 =patient.p_relative_mob3(email);
                    String RNum4 =patient.p_relative_mob4(email);
                    double heart = Double.parseDouble(Heart.getText().toString());
                    //if(16<=age && age<=45){
                    if (60 <= heart && heart <= 100) {
                        new AlertDialog.Builder(Patient_Taphost.this)
                                .setTitle("Heart Beats Alert")
                                .setIcon(R.drawable.ic_heart)
                                .setMessage("Your heart beats rate is normal..check your all records")
                                .setCancelable(false)
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    Intent intent=new Intent(Patient_Taphost.this,Patient_Profile.class);
                                    startActivity(intent);
                                    }
                                }).create().show();
                    }
                    if (80 <= heart && heart <= 100){
                     if(gen=="female"){
            new AlertDialog.Builder(Patient_Taphost.this)
                    .setTitle("Heart Beats Alert")
                    .setIcon(R.drawable.ic_heart)
                    .setMessage("Your heart beats rate is normal if you are pregnant ..check your all records")
                    .setCancelable(false)
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent=new Intent(Patient_Taphost.this,Patient_Profile.class);
                                    startActivity(intent);
                        }
                    }).create().show();
                    }
                    }
                    else if (70 <= heart && heart <= 100) {
                new AlertDialog.Builder(Patient_Taphost.this)
                        .setTitle("Heart Beats Alert")
                        .setIcon(R.drawable.ic_heart)
                        .setMessage("Your Heartbeats rate is normal..check your all records")
                        .setCancelable(false)
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                    Intent intent=new Intent(Patient_Taphost.this,Patient_Profile.class);
                                    startActivity(intent);
                            }
                        }).create().show();
            }
                    if (100 < heart && heart <= 150) {
                        new AlertDialog.Builder(Patient_Taphost.this)
                                .setTitle("Heart Beats Alert")
                                .setIcon(R.drawable.ic_heart)
                                .setMessage("Your Heartbeats rate result of your Physical exertion,repeat heartbeats checking again in your relaxation state ..check your all records")
                                .setCancelable(false)
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent=new Intent(Patient_Taphost.this,Patient_Profile.class);
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
                        }
                        catch (Exception e){
                            Toast.makeText(getApplicationContext(),
                                    "SMS failed, please try again later!",
                                    Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                        new AlertDialog.Builder(Patient_Taphost.this)
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
                        Toast.makeText(Patient_Taphost.this, "Error, invalid Heartbeats", Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){}
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
                //fun to get data by bluetooth
                try {
                    String RNum1 =patient.p_relative_mob1(email);
                    String RNum2 =patient.p_relative_mob2(email);
                    String RNum3 =patient.p_relative_mob3(email);
                    String RNum4 =patient.p_relative_mob4(email);
                    double Sys = Double.parseDouble(pressure.getText().toString());
                    if (80 < Sys && Sys <= 100) {
                        Toast.makeText(getApplication(),"Your pressure is very low..connect to your doctor",Toast.LENGTH_LONG).show();
                        new AlertDialog.Builder(Patient_Taphost.this)
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
                        new AlertDialog.Builder(Patient_Taphost.this)
                                .setTitle("Pressure Alert")
                                .setIcon(R.drawable.ic_pre)
                                .setMessage("Your pressure is low..check all your records")
                                .setCancelable(false)
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent=new Intent(Patient_Taphost.this,Patient_Profile.class);
                                        startActivity(intent);
                                    }
                                }).create().show();
                    }
                    if (120 <= Sys && Sys < 130) {
                        new AlertDialog.Builder(Patient_Taphost.this)
                                .setTitle("Pressure Alert")
                                .setIcon(R.drawable.ic_pre)
                                .setMessage("Your pressure is normal..check all your records")
                                .setCancelable(false)
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent=new Intent(Patient_Taphost.this,Patient_Profile.class);
                                        startActivity(intent);
                                    }
                                }).create().show();
                    }
                    if (130 <= Sys && Sys < 140) {
                        new AlertDialog.Builder(Patient_Taphost.this)
                                .setTitle("Pressure Alert")
                                .setIcon(R.drawable.ic_pre)
                                .setMessage("Your pressure is high..check all your records")
                                .setCancelable(false)
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent=new Intent(Patient_Taphost.this,Patient_Profile.class);
                                        startActivity(intent);
                                    }
                                }).create().show();
                    }
                    if (140 <= Sys && Sys < 160) {
                        new AlertDialog.Builder(Patient_Taphost.this)
                                .setTitle("Pressure Alert")
                                .setIcon(R.drawable.ic_pre)
                                .setMessage("Your pressure is very high..connect to your doctor")
                                .setCancelable(false)
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent=new Intent(Patient_Taphost.this,Patient_Profile.class);
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
                        new AlertDialog.Builder(Patient_Taphost.this)
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
                        new AlertDialog.Builder(Patient_Taphost.this)
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
                        Toast.makeText(Patient_Taphost.this, "Error, invalid pressure", Toast.LENGTH_LONG).show();
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
                record();
                try {
                   String gen =patient.gendre(email);
                    String RNum1 =patient.p_relative_mob1(email);
                    String RNum2 =patient.p_relative_mob2(email);
                    String RNum3 =patient.p_relative_mob3(email);
                    String RNum4 =patient.p_relative_mob4(email);
                    double Tem = Double.parseDouble(Temp.getText().toString());
                    if ((36.8 <= Tem && Tem <= 37.2)) {
                        new AlertDialog.Builder(Patient_Taphost.this)
                                .setTitle("Temperature Alert")
                                .setIcon(R.drawable.ic_temper)
                                .setMessage("Your temperature is normal..check your all records")
                                .setCancelable(false)
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    Intent intent=new Intent(Patient_Taphost.this,Patient_Profile.class);
                                    startActivity(intent);
                                    }
                                }).create().show();
                    }
                    if (37.2 < Tem && Tem < 37.5) {
                        new AlertDialog.Builder(Patient_Taphost.this)
                                .setTitle("Temperature Alert")
                                .setIcon(R.drawable.ic_temper)
                                .setMessage("A slight rise in temperature,maybe because of the High temperature air or that you made a lot of exercises ..check your all records ")
                                .setCancelable(false)
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                   Intent intent=new Intent(Patient_Taphost.this,Patient_Profile.class);
                                    startActivity(intent);
                                    }
                                }).create().show();
                    }
                    if (37.5 < Tem && Tem <= 38.3) {
                        new AlertDialog.Builder(Patient_Taphost.this)
                                .setTitle("Temperature Alert")
                                .setIcon(R.drawable.ic_temper)
                                .setMessage("You catch a cold ..please connect to you doctor ..check your all records ")
                                .setCancelable(false)
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    Intent intent=new Intent(Patient_Taphost.this,Patient_Profile.class);
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
                        new AlertDialog.Builder(Patient_Taphost.this)
                                .setTitle("Temperature Alert")
                                .setIcon(R.drawable.ic_temper)
                                .setMessage("danger case.. A slight rise in temperature,you have fever .. connect to your doctor quickly")
                                .setCancelable(false)
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    /*Intent intent=new Intent(this,//massage.class);
                                        startActivity(intent);*/

                                    }
                                }).create().show();
                    }
                    if (Tem == 37.5) {
                       if (gen=="female"){
                        new AlertDialog.Builder(Patient_Taphost.this)
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
                            new AlertDialog.Builder(Patient_Taphost.this)
                                    .setTitle("Temperature Alert")
                                    .setIcon(R.drawable.ic_temper)
                                    .setMessage("MayBe You catch a cold ..please connect to you doctor ..check your all records ")
                                    .setCancelable(false)
                                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent=new Intent(Patient_Taphost.this,Patient_Profile.class);
                                            startActivity(intent);
                                        }
                                    }).create().show();
                        }
                    } else {
                        Toast.makeText(Patient_Taphost.this, "Error, invalid temperature", Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    Toast.makeText(Patient_Taphost.this, e.toString(), Toast.LENGTH_LONG).show();

                }

            }
        });
    }


    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            try {
                updateStatus(); //this function can change value of mInterval.
            } finally {
                // 100% guarantee that this always happens, even if
                // your update method throws an exception
                mHandler.postDelayed(mStatusChecker, mInterval);
            }
        }
    };

    private void updateStatus() {
        try {
            turnOnBT();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e+"", Toast.LENGTH_LONG).show();
        }


        finally {
            mConnectedThread.write("0");

        }

    }

    void startRepeatingTask() {
        mStatusChecker.run();
    }

    void stopRepeatingTask() {
        mHandler.removeCallbacks(mStatusChecker);
    }

    private void turnOn() {
        try{
            if (!BA.isEnabled()) {
        Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(turnOn, 0);
        Toast.makeText(getApplicationContext(), "Turned on", Toast.LENGTH_LONG).show();
    } else {
        // Toast.makeText(getApplicationContext(), "Already on", Toast.LENGTH_LONG).show();
    }
    }
catch (Exception r){
        Toast.makeText(getApplicationContext(),r+"", Toast.LENGTH_LONG).show();

        }
        }



    private void turnOnBT() {
        BluetoothDevice device = BA.getRemoteDevice(address);

        BluetoothSocket mmSocket;
        BluetoothDevice mmDevice;
        final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

        BluetoothSocket tmp = null;
        mmDevice = device;

        try {
            tmp = mmDevice.createRfcommSocketToServiceRecord(MY_UUID);
        } catch (IOException e) {
            e.printStackTrace();
        }


        mmSocket = tmp;

        BA.cancelDiscovery();
        try {
            mmSocket.connect();




        } catch (IOException connectException) {

            try {

                mmSocket.close();
            } catch (IOException closeException) {
            }  return;
        }


         mConnectedThread = new ConnectedThread(mmSocket);
       // mConnectedThread.write("0");
        mConnectedThread.start();




    }








String heart = "";
    String temp = "";
    String press = "";
    String comm = "";

    public void record(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String Time = sdf.format(new Date());

        inite();
//String Heart,String Temperture,String Pressure,String Comments,String Time,String Email
        long id = reg.checkUP(heart, temp, press, Time, email);

        if (id > 0)

            Toast.makeText(getBaseContext(), "successful", Toast.LENGTH_LONG).show();


        else
            Toast.makeText(getBaseContext(), "Faild", Toast.LENGTH_LONG).show();
        reg.getData();

        // reg.delet();
        //  Intent i = new Intent(Patient_Profile.this, Test.class);
        //  startActivity(i);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Accepttbtn:
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String Time = sdf.format(new Date());

                inite();
//String Heart,String Temperture,String Pressure,String Comments,String Time,String Email
                long id = reg.checkUP(heart, temp, press, Time, email);

                if (id > 0)

                    Toast.makeText(getBaseContext(), "successful", Toast.LENGTH_LONG).show();


                else
                    Toast.makeText(getBaseContext(), "Faild", Toast.LENGTH_LONG).show();
                reg.getData();

                // reg.delet();
                //  Intent i = new Intent(Patient_Taphost.this, Test.class);
                //  startActivity(i);


        }
    }

    private void inite() {
        heart = Heart.getText().toString();
        temp = Temp.getText().toString();
        comm = comment.getText().toString();
        press = pressure.getText().toString();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.patient_taphost, menu);
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
                    Toast.makeText(Patient_Taphost.this, "Sign out", Toast.LENGTH_LONG).show();

                    //// switch to register or user screen
                      Intent intent=new Intent(Patient_Taphost.this,Login_Register.class);
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

            Intent intent = new Intent(this,Patient_Profile.class);
            startActivity(intent);

        }

    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //   return super.onCreateOptionsMenu(menu);



        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

            MenuInflater blow = getMenuInflater();
            blow.inflate(R.menu.patient_taphost, menu);
            return super.onCreateOptionsMenu(menu);

        }
        else
        {

            MenuInflater blow = getMenuInflater();
            blow.inflate(R.menu.patient_taphost, menu);
            return super.onCreateOptionsMenu(menu);
        }




       // return super.onCreateOptionsMenu(menu);
    }


    Intent i;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {


            case R.id.Update_name:
                Update_name nm=new Update_name(Patient_Taphost.this,email);
                nm.show();
                return true;

            case R.id.Update_pass:
                Update_pass passw=new Update_pass(Patient_Taphost.this,email);
                passw.show();
               return true;

            case R.id.Mobile:

                Update_mobile mob=new Update_mobile(Patient_Taphost.this,email);
                mob.show();
                return true;

            case R.id.Weight:

                Update_weight weght=new Update_weight(Patient_Taphost.this,email);
                weght.show();

                return true;
            case R.id.Other:
                // Other option clicked.

            case R.id.delet:
                // delet option clicked.
                i = new Intent(Patient_Taphost.this, Delete_profile.class);
                startActivity(i);

                return true;

            case R.id.view:
                // view option clicked.
                i = new Intent(Patient_Taphost.this, View_patient_profile.class);
                startActivity(i);

                return true;

            case R.id.cancle: // close my account
                Intent intent = new Intent(getApplicationContext(), Login_Register.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

                return true;

            case R.id.close:  /// close App
                Intent i = new Intent(Intent.ACTION_MAIN);
                i.addCategory(Intent.CATEGORY_HOME);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);

                return true;


        }

return true;
    }
*/

    public class ConnectedThread extends Thread {
        OutputStream  mmOutStream;
        InputStream mmInStream;
        private ConnectedThread(BluetoothSocket socket) {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the input and output streams, using temp objects because
            // member streams are final
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) { }

             mmInStream = tmpIn;
              mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[256];  // buffer store for the stream
            int bytes; // bytes returned from read()

            // Keep listening to the InputStream until an exception occurs
            while (true) {
                try {
                    // Read from the InputStream
                    bytes = mmInStream.read(buffer);        // Get number of bytes and message in "buffer"
                    h.obtainMessage(1, bytes, -1, buffer).sendToTarget();     // Send to message queue Handler
                } catch (IOException e) {
                    break;
                }
            }
        }

        Handler h = new Handler() {
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case 1:                                                   // if receive massage
                        byte[] readBuf = (byte[]) msg.obj;
                        String strIncom = new String(readBuf, 0, msg.arg1);                 // create string from bytes array
                        sb.append(strIncom);                                                // append string
                        int endOfLineIndex = sb.indexOf("\r\n");                            // determine the end-of-line
                        if (endOfLineIndex > 0) {                                            // if end-of-line,
                            String sbprint = sb.substring(0, endOfLineIndex);               // extract string
                            sb.delete(0, sb.length());// and clear
                            Temp.setText(sbprint);
                        }
                        break;
                }
            };
        };

        /* Call this from the main activity to send data to the remote device */
        public void write(String message) {

            byte[] msgBuffer = message.getBytes();
            try {
                mmOutStream.write(msgBuffer);
            } catch (IOException e) {
            }}

    }






}
