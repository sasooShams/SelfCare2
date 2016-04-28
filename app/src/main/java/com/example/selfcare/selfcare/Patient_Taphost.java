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
import android.graphics.Color;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
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

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    private int mInterval = 550000 * 500000; // 5 seconds by default, can be changed later
    private Handler mHandler;

    private Handler serverHandler;
    TabHost PatabHost;
    EditText Heart, Temp, pressure, comment;
    Button save;
    InsertData reg;
    SharedPreferences sharedpreferences;
    String email = "";
    Build args;
    ImageButton hbeats; //heartBeats to display graph
    ImageButton tem; //temperature to display graph
    ImageButton pre; //pressure to display graph
    private String[] mMonth = new String[] {"Sun" , "Mon", "Tus", "Wed", "Thu","Fri","Sat"};
    DialogFragment newFragment;
    Context c;
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
    String tepr = "";
GetDataOfPatient get ;
    SendRecToServer send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_taphost);
        get  = new GetDataOfPatient(this);
        send =new SendRecToServer(this);
       /* Toolbar toolbar = (Toolbar) findViewById(R.id.Logtoolbar);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setTitle("SelfCare");*/

        reg = new InsertData(this);//database
        sharedpreferences = getSharedPreferences("MyPREFERENCES", 0);
        email = sharedpreferences.getString("Paient_email", "null");

        args = new Build();


       // send.getDataofPatient(email) ;
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
        c = this;
        String dd="";
        /*
        List<String> ss =get.date(email);
        for (int i=0;i<=ss.size();i++){
          //  Toast.makeText(this,ss.get(i),Toast.LENGTH_LONG).show();
             dd +=ss.get(i);

        }
        Heart.setText(dd);
*/
        //   Heart.setText(ss);
        save.setOnClickListener(this);

        //Bluetooth side
        // BA = BluetoothAdapter.getDefaultAdapter();
        //turnOn();
/*
        try {
            mHandler = new Handler();
            startRepeatingTask();
        }
        catch (Exception e){

            Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG).show();

        }*/

        // check net
        try {
            serverHandler = new Handler();
            startcheck();
        }catch (Exception e){

            Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG).show();

        }

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

            }
        });

        hbeats = (ImageButton) findViewById(R.id.heartratebtn); // open heart chart
        hbeats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHeartGraph(email);


               // ArrayList arr = new ArrayList();
                //arr=get.allpressure(email);
                //Heart.setText(arr.get(0)+"");
            }
        });
        tem = (ImageButton) findViewById(R.id.thermobtn); // open temperature chart
        tem.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openTempGraph();
            }
        });
        pre = (ImageButton) findViewById(R.id.pressurebtn); // open pressure chart
        pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPreGraph();
            }
        });


    }

    ////heartBeats chart
    private void openHeartGraph(String EE){
       // String heart;
      //  String date;
       // ArrayList<GetDataOfPatient> patient;
       // double[] xValues; //date
        //double[] yValues; //heart
        //xValues =patient.date(email);
     //   ArrayList<GetDataOfPatient> patient;
       // patient = new GetDataOfPatient(c) ;
      //  Heart =patient.heart(email);
        int[] x = {1,2,3,4};
      // int[] Heart = {60,40,50,90,70,80,100,150}; //get values from database
        ArrayList arr = new ArrayList();
        arr=get.allpressure(EE);
Heart.setText(arr.get(0)+"");
        // Creating an  XYSeries for heartbeats
        XYSeries heartSeries = new XYSeries("Heartbeats");
        // Adding data to heartbeats  Series
        /*
        if (records.size() > 0) {
            int seriesLength = xValues.length;
            for (int k = 0; k < seriesLength; k++) {
                series.add(k * 3 + 1, yValues[k]);
            }
        }
         */
        for(int i=0; i < x.length; i++){
            heartSeries.add(x[i],Integer.parseInt(arr.get(i).toString()));
        }
        // Creating a dataset to hold series
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        // Adding heartbeats Series to the dataset
        dataset.addSeries(heartSeries);
        // Creating XYSeriesRenderer to customize heartSeries
        XYSeriesRenderer heartRenderer = new XYSeriesRenderer();
        heartRenderer.setColor(Color.GREEN);
        heartRenderer.setChartValuesTextSize(18);
        heartRenderer.setPointStyle(PointStyle.CIRCLE);
        heartRenderer.setFillPoints(true);
        heartRenderer.setLineWidth(2);
        heartRenderer.setDisplayChartValues(true);
        // Creating a XYMultipleSeriesRenderer to customize the whole chart
        XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
        multiRenderer.setXLabels(0);
        //  int length = xValues.length;
        // for (int i = 0; i < length; i++) {
        //     multiRenderer.addXTextLabel(i * 3 + 1, month + "/" + (int) xValues[i]);
        // }
        multiRenderer.setLabelsTextSize(18);
        multiRenderer.setShowGridX(true);
        multiRenderer.setShowLegend(false);
        multiRenderer.setGridColor(Color.LTGRAY);
        multiRenderer.setPointSize(5f);
        multiRenderer.setPanEnabled(true, false);
        multiRenderer.setApplyBackgroundColor(true);
        multiRenderer.setBackgroundColor(Color.TRANSPARENT);
        multiRenderer.setYLabelsAlign(Paint.Align.RIGHT);
        multiRenderer.setChartTitle("Avg Heart beats");
        multiRenderer.setXTitle("date of measurement reading");
        multiRenderer.setYTitle("No. of heartbeats in one minute");
        multiRenderer.setZoomButtonsVisible(true);
        for(int i=0;i<x.length;i++){
            multiRenderer.addXTextLabel(i+1, mMonth[i]);
        }
        // Adding incomeRenderer and expenseRenderer to multipleRenderer
        // The order of adding dataseries to dataset and renderers to multipleRenderer
        multiRenderer.addSeriesRenderer(heartRenderer);
        // Creating an intent to plot line chart using dataset and multipleRenderer
        Intent intent = ChartFactory.getLineChartIntent(getBaseContext(), dataset, multiRenderer);
        // Start Activity
        startActivity(intent);
    }
    /// temperature chart
    private void openTempGraph(){
        int[] x = {1,2,3,4,5,6,7};
        double[] Temp = {36.8,37,37.2,37.5,38,38.3,40,41.8}; //get values from database
        // Creating an  XYSeries for Income
        XYSeries tempSeries = new XYSeries("Temperature");
        // Adding data to temperature  Series
        for(int i=0; i < x.length; i++){
            tempSeries.add(x[i],Temp[i]);
        }
        // Creating a dataset to hold  series
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        // Adding temperature Series to the dataset
        dataset.addSeries(tempSeries);
        // Creating XYSeriesRenderer to customize tempSeries
        XYSeriesRenderer tempRenderer = new XYSeriesRenderer();
        tempRenderer.setChartValuesTextSize(18);
        tempRenderer.setColor(Color.GREEN);
        tempRenderer.setPointStyle(PointStyle.CIRCLE);
        tempRenderer.setFillPoints(true);
        tempRenderer.setLineWidth(2);
        tempRenderer.setDisplayChartValues(true);
        // Creating a XYMultipleSeriesRenderer to customize the whole chart
        XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
        multiRenderer.setXLabels(0);
        multiRenderer.setLabelsTextSize(18);
        multiRenderer.setShowGridX(true);
        multiRenderer.setShowLegend(false);
        multiRenderer.setGridColor(Color.LTGRAY);
        multiRenderer.setPointSize(5f);
        multiRenderer.setPanEnabled(true, false);
        multiRenderer.setApplyBackgroundColor(true);
        multiRenderer.setBackgroundColor(Color.TRANSPARENT);
        multiRenderer.setYLabelsAlign(Paint.Align.RIGHT);
        multiRenderer.setChartTitle("Avg temperature");
        multiRenderer.setXTitle("date of measurement reading");
        multiRenderer.setYTitle("Temperature degree");
        multiRenderer.setZoomButtonsVisible(true);
        for(int i=0;i<x.length;i++){
            multiRenderer.addXTextLabel(i+1, mMonth[i]);
        }
        // Adding tempRenderer to multipleRenderer
        // The order of adding dataseries to dataset and renderer to multipleRenderer
        multiRenderer.addSeriesRenderer(tempRenderer);
        // Creating an intent to plot line chart using dataset and multipleRenderer
        Intent intent = ChartFactory.getLineChartIntent(getBaseContext(), dataset, multiRenderer);
        // Start Activity
        startActivity(intent);
    }
    //// pressure chart
    private void openPreGraph(){
        int[] x = {1,2,3,4,5,6,7};
        int[] dia = {80,110,85,100,60,90,130}; //get values from database
        int[] sys = {120,180,130,160,100,140,80};
        // Creating an
        //  XYSeries for pressure
        XYSeries diaSeries = new XYSeries("Diastolic");
        XYSeries sysSeries = new XYSeries("Systolic");
        // Adding data to pressure  Series
        for(int i=0; i < x.length; i++){
            diaSeries.add(x[i],dia[i]);
            sysSeries.add(x[i],sys[i]);
        }
        // Creating a dataset to hold each series
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        // Adding pressure Series to the dataset
        dataset.addSeries(diaSeries);
        // Adding Expense Series to dataset
        dataset.addSeries(sysSeries);
        // Creating XYSeriesRenderer to customize presSeries
        XYSeriesRenderer diaRenderer = new XYSeriesRenderer();
        diaRenderer.setColor(Color.GREEN);
        diaRenderer.setPointStyle(PointStyle.CIRCLE);
        diaRenderer.setChartValuesTextSize(18);
        diaRenderer.setFillPoints(true);
        diaRenderer.setLineWidth(2);
        diaRenderer.setDisplayChartValues(true);
        // Creating XYSeriesRenderer to customize expenseSeries
        XYSeriesRenderer sysRenderer = new XYSeriesRenderer();
        sysRenderer.setColor(Color.YELLOW);
        sysRenderer.setChartValuesTextSize(18);
        sysRenderer.setPointStyle(PointStyle.CIRCLE);
        sysRenderer.setFillPoints(true);
        sysRenderer.setLineWidth(2);
        sysRenderer.setDisplayChartValues(true);
        // Creating a XYMultipleSeriesRenderer to customize the whole chart
        XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
        multiRenderer.setXLabels(0);
        multiRenderer.setLabelsTextSize(18);
        multiRenderer.setShowGridX(true);
        multiRenderer.setShowLegend(false);
        multiRenderer.setGridColor(Color.LTGRAY);
        multiRenderer.setPointSize(5f);
        multiRenderer.setPanEnabled(true, false);
        multiRenderer.setApplyBackgroundColor(true);
        multiRenderer.setBackgroundColor(Color.TRANSPARENT);
        multiRenderer.setYLabelsAlign(Paint.Align.RIGHT);
        multiRenderer.setChartTitle("pressure");
        multiRenderer.setXTitle("date of measurement reading");
        multiRenderer.setYTitle("systolic vs diastolic pressure");
        multiRenderer.setZoomButtonsVisible(true);
        for(int i=0;i<x.length;i++){
            multiRenderer.addXTextLabel(i+1, mMonth[i]);
        }
        // Adding incomeRenderer and expenseRenderer to multipleRenderer
        // The order of adding dataseries to dataset and renderer to multipleRenderer
        multiRenderer.addSeriesRenderer(diaRenderer);
        multiRenderer.addSeriesRenderer(sysRenderer);
        // Creating an intent to plot line chart using dataset and multipleRenderer
        Intent intent = ChartFactory.getLineChartIntent(getBaseContext(), dataset, multiRenderer);
        // Start Activity
        startActivity(intent);
    }

    Runnable server = new Runnable() {
        @Override
        public void run() {
            try {
                checkserver(); //this function can change value of mInterval.
            } finally {
                // 100% guarantee that this always happens, even if
                // your update method throws an exception
                serverHandler.postDelayed(server, 30000);
            }
        }
    };

    private void checkserver() {
        try {
            Login_server lg =new Login_server(c);

            BackgroundTask task = new BackgroundTask(c);
            if (isNetworkAvailable()==true) {
                Toast.makeText(getBaseContext(), isNetworkAvailable() + "", Toast.LENGTH_LONG).show();
                task.execute("regester", get.Fname(email), get.Lname(email), get.Pass(email), get.mobile(email), get.birthdate(email), get.weight(email), get.height(email), get.gendre(email), email, "patient");
                lg.execute("", get.Fname(email), get.Lname(email), get.Pass(email), get.mobile(email), get.birthdate(email), get.weight(email), get.height(email), get.gendre(email), email,get.Demail(email), "patient");
             send.getDataofPatient(email) ;
            }

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e + "", Toast.LENGTH_LONG).show();
        } finally {


        }

    }
    void startcheck() {
        server.run();
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
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e + "", Toast.LENGTH_LONG).show();
        } finally {
            mConnectedThread.write("0");

        }

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    void startRepeatingTask() {
        mStatusChecker.run();
    }

    void stopRepeatingTask() {
        mHandler.removeCallbacks(mStatusChecker);
    }

    private void turnOn() {
        try {
            if (!BA.isEnabled()) {
                Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(turnOn, 0);
                Toast.makeText(getApplicationContext(), "Turned on", Toast.LENGTH_LONG).show();
            } else {
                // Toast.makeText(getApplicationContext(), "Already on", Toast.LENGTH_LONG).show();
            }
        } catch (Exception r) {
            Toast.makeText(getApplicationContext(), r + "", Toast.LENGTH_LONG).show();

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
            }
            return;
        }


        mConnectedThread = new ConnectedThread(mmSocket);
        // mConnectedThread.write("0");
        mConnectedThread.start();


    }


    String heart = "";
    String temp = "";
    String press = "";
    String comm = "";

    public void record() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String Time = sdf.format(new Date());

        // Create the MySQL datetime string
// Parse the input date
        SimpleDateFormat fmt = new SimpleDateFormat("MM-dd-yyyy HH:mm");
        Date inputDate = null;
        try {
            inputDate = fmt.parse("10-22-2011 01:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }

// Create the MySQL datetime string
        fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = fmt.format(inputDate);

        inite();
//String Heart,String Temperture,String Pressure,String Comments,String Time,String Email
        long id = reg.checkUP(heart, temp, press, Time, email);
        GetDataOfPatient reg1 = new GetDataOfPatient(c);
        //  pressure.setText(reg1.date(email));
        Toast.makeText(getBaseContext(), Time, Toast.LENGTH_LONG).show();
        Alarm alarm=new Alarm(c,email);
        alarm.checkHeart(heart);//alarms
        alarm.checktemp(temp);
        alarm.checkpress(press);
        if (id > 0) {
            BackgroundTask task = new BackgroundTask(c);
            String method = "record";
            task.execute(method, heart, temp, press, Time, email, "patient");


            Toast.makeText(getBaseContext(), "successful", Toast.LENGTH_LONG).show();
        } else
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
                    // pressure.setText(reg1);
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
        sett = menu.findItem(R.id.setting_id);
        userProf = menu.findItem(R.id.profile_id);
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
                    Intent intent = new Intent(Patient_Taphost.this, Login_Register.class);
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
        } else { //open the setting activity
            /*
            Intent intent = new Intent(this,//.class);
            startActivity(intent);
                   */
        }
    }

    private void gotoProf() { //profile screen
        ActionBar action = getSupportActionBar();
        if (isProfOpened)  //test if the profile icon selected
        {
            action.setDisplayShowCustomEnabled(false);
            action.setDisplayShowTitleEnabled(true);
            isProfOpened = false;
        } else { //open the profile activity

            Intent intent = new Intent(this, Patient_Profile.class);
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
        OutputStream mmOutStream;
        InputStream mmInStream;

        private ConnectedThread(BluetoothSocket socket) {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the input and output streams, using temp objects because
            // member streams are final
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
            }

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
            }

            ;
        };

        /* Call this from the main activity to send data to the remote device */
        public void write(String message) {

            byte[] msgBuffer = message.getBytes();
            try {
                mmOutStream.write(msgBuffer);
            } catch (IOException e) {
            }
        }

    }


}
