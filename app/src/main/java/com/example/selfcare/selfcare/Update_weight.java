package com.example.selfcare.selfcare;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by h on 27/02/2016.
 */
public class Update_weight extends Dialog implements View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button save, cancel;
    TextView tv;
    String key;
    EditText weight;
    P_Update_dB up;

    public Update_weight(Activity a, String email) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        key = email;
        up = new P_Update_dB(c);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.update_weight_);
        save = (Button) findViewById(R.id.save);
        cancel = (Button) findViewById(R.id.cancel);
        save.setOnClickListener(this);
        cancel.setOnClickListener(this);


        weight=(EditText)findViewById(R.id.edmobile);




    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save:
                String Weight =weight.getText().toString();

                if (Weight!=null)
                {
                    long id=  up.weight(key,Weight);
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

    }
}
