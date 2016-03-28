package com.example.selfcare.selfcare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

/**
 * Created by sahar fathy on 3/13/2016.
 */
public class Doctor_Profile extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_profile);

        Spinner dropdown = (Spinner)findViewById(R.id.spinner1);
        String[] items = new String[]{"         ","Allergy & Immunology"," Anesthesiology", "Colon and Rectal Surgery",
                "Dermatology"," Emergency Medicine"," Family Medicine"," Internal Medicine"," Medical Genetics",
                "Neurological Surgery", "Neurology"," Obstetrics and Gynecology","Orthopaedic Surgery"," Otolaryngology" ,
                " Radiation Oncology", " Surgery-General"," Thoracic Surgery-Integrated" ," Urology",
                "Vascular Surgery-Integrated","Internal Medicine/Pediatrics"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

    }


}
