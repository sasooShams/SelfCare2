package com.example.selfcare.selfcare;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by sahar fathy on 2/14/2016.
 */
public class ImageSlider extends Activity {

    ViewPager viewPager;
    SwimpAdaptor adaptor;
    Button BtnSkip ;
    Context c ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_slider);
        viewPager =(ViewPager)findViewById(R.id.pager_main);
        adaptor = new SwimpAdaptor(this);
        viewPager.setAdapter(adaptor);
        BtnSkip  = (Button)findViewById(R.id.skipbtn);
        c = this ;

        // Skip Button Hit
        BtnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(c, " Welcome! ", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(ImageSlider.this, Login_Register.class);
                startActivity(i);

            }
        });

    }





}
