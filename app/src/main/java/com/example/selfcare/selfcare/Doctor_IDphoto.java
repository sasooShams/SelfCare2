package com.example.selfcare.selfcare;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Doctor_IDphoto extends Activity {


    public static final int Image_Gallery_REQUEST = 2 ;

    ImageView imageView;
    Button  TakePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor__idphoto);
        imageView = (ImageView)findViewById(R.id.imageView);
        TakePhoto = (Button)findViewById(R.id.takePhoto);

        TakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);

            }
        });
    }

    public void onImageGalleryClicked (View v){

        Intent photoPickerTintent = new Intent(Intent.ACTION_PICK);
        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String pictureDirectoryPath = pictureDirectory.getPath();
        Uri data = Uri.parse(pictureDirectoryPath);
        photoPickerTintent.setDataAndType(data,"image/*");
        startActivityForResult(photoPickerTintent,Image_Gallery_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Image_Gallery_REQUEST){
            Uri imageUri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(imageUri);
                Bitmap image = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(image);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this,"Unable to find the image try again later.",Toast.LENGTH_LONG).show();
            }
        }

        else {
            Bitmap photoTaken = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photoTaken);
        }
    }
}