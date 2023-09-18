package com.example.society;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Decrypted extends  AppCompatActivity{

    Button tryAgain;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.decrypted_text);

        Intent intent = getIntent();
        String receivedData = intent.getStringExtra("message");

        tryAgain = findViewById(R.id.try_again_btn);
        textView = findViewById(R.id.plain_text_view);


        textView.setText(receivedData);

        //button click
        tryAgain.setOnClickListener(new View.OnClickListener() {

            @Override
            public  void onClick(View v){
//                go back to main
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);

            }

        });

    }


}
