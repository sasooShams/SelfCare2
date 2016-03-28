package com.example.selfcare.selfcare;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by h on 28/02/2016.
 */
public class Update_name extends Dialog implements View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button save, cancel;
    TextView tv;
String key;
    EditText fname,lname;
    P_Update_dB up;
    public Update_name (Activity a,String email) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        key=email;
        up= new P_Update_dB(c);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.update_patient_profile);
        save = (Button) findViewById(R.id.save);
        cancel = (Button) findViewById(R.id.cancel);
        save.setOnClickListener(this);
        cancel.setOnClickListener(this);


        fname=(EditText)findViewById(R.id.edfirst);
        lname=(EditText)findViewById(R.id.edlast);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save:
                String Fname =fname.getText().toString();
                String Lname =lname.getText().toString();
                if (Fname!=null)
                {
                    long id=  up.updateFName(key,Fname);
                    if (id > 0)
                       Toast.makeText(getContext(),"success",Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(getContext(),"failed",Toast.LENGTH_LONG).show();


                }

                if (Lname!=null)
                {
                    long id=  up.updateLName(key,Lname);
                    if (id > 0)
                        Toast.makeText(getContext(),"success",Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(getContext(),"failed",Toast.LENGTH_LONG).show();

                }




                dismiss();



            case R.id.cancle:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}
