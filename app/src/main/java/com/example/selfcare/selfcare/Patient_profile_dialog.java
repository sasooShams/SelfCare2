package com.example.selfcare.selfcare;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by h on 27/02/2016.
 */
public class Patient_profile_dialog extends DialogFragment implements View.OnClickListener {
    Button save ,cancle ;
    EditText fname,lname;
    P_Update_dB up;

    GetEmail mail;



    @Nullable


    @Override

    public void onAttach(Activity activity){
        super.onAttach(activity);



      //  up=new P_Update_dB(activity);
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        return new AlertDialog.Builder(getActivity())
                .setTitle("Dialog Title")
                .setPositiveButton("OK", null)
                .create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View  view= inflater.inflate(R.layout.update_patient_profile,null);
        //Toast.makeText(getActivity(), key, Toast.LENGTH_LONG).show();
        up=new P_Update_dB(getActivity());
        mail=new GetEmail();
        //key =mail.getEmail();


        //Toast.makeText(getActivity(), key, Toast.LENGTH_LONG).show();

        save =(Button) view.findViewById(R.id.save);
        cancle=(Button)view.findViewById(R.id.cancle);

        fname=(EditText)view.findViewById(R.id.edfirst);
        lname=(EditText)view.findViewById(R.id.edlast);

        save.setOnClickListener(this);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });



        return view;
    }



    @Override
    public void onClick(View v) {

        String key="";
/*
        try {
            Bundle mArgs = getArguments();
            key = mArgs.getString("key");
        }
        catch (Exception e){  Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();}*/
        String Fname =fname.getText().toString();
        String Lname =lname.getText().toString();
        if (Fname!=null)
        {
       long id=  up.updateFName(key,Fname);
            if (id > 0)
            Toast.makeText(getActivity(), "successful"+Fname, Toast.LENGTH_LONG).show();
            else
                Toast.makeText(getActivity(), "Faild"+id+key, Toast.LENGTH_LONG).show();

        }

        if (Lname!=null)
        {
            long id=  up.updateLName(key,Lname);
            if (id > 0)
                Toast.makeText(getActivity(), "successful"+Lname, Toast.LENGTH_LONG).show();
            else
                Toast.makeText(getActivity(), "Faild"+id+key, Toast.LENGTH_LONG).show();

        }




        dismiss();

    }
}
