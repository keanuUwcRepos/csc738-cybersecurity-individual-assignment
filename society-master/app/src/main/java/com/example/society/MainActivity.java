package com.example.society;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSION_CODE=1000;
    private static final int IMAGE_CAPTURE_CODE=1001;
    Button mCaptureBtn;
    ImageView mImageView;
    Uri image_uri;

    TextView decryptedText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = findViewById(R.id.image_view);
        mCaptureBtn = findViewById(R.id.capture_image_btn);

        //button click
        mCaptureBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public  void onClick(View v){
//                if system os is >= marshmallow, request runtime permission
                if(Build.VERSION.SDK_INT>Build.VERSION_CODES.M){
                    if(checkSelfPermission(Manifest.permission.CAMERA)== PackageManager.PERMISSION_DENIED ||
                            checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED ){
                        //PERMISSION NOT ENABLED, REQUEST IT

                        String[] permission= {Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        //show popup to request permissions
                        requestPermissions(permission,PERMISSION_CODE);

                    }else{
                        //permission already granted
                        openCamera();
                    }
                }else{
                    //system os <marshmallow
                    openCamera();
                }

            }

        });

    }

    private void openCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"cipher");
        values.put(MediaStore.Images.Media.DESCRIPTION,"Decrypt me!");
        image_uri=getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);

        // Camera intent
        Intent cameraIntent =new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,image_uri);
        startActivityForResult(cameraIntent,IMAGE_CAPTURE_CODE);

    }

    private void runTextRecognition(Bitmap mSelectedImage) {
        // Replace with code from the codelab to run text recognition.
        InputImage image= InputImage.fromBitmap(mSelectedImage,0);
        TextRecognizerOptions options = new TextRecognizerOptions.Builder().build();
        TextRecognizer recognizer= TextRecognition.getClient(options);
//        mTextButton.setEnabled(false);
        recognizer.process(image).addOnSuccessListener(
                new OnSuccessListener<Text>(){
                    @Override
                    public void onSuccess(Text texts){
//                        mTextButton.setEnabled(true);
                        processTextRecognitionResult(texts);

                    }
                }
        ).addOnFailureListener(
                new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
//                        mTextButton.setEnabled(true);
                        e.printStackTrace();
                    }
                }
        );
    }
//handling permission result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode){
            case PERMISSION_CODE:{
                if(grantResults.length>0 && grantResults[0]==
                        PackageManager.PERMISSION_GRANTED ){
                    //permission from popup was granted
                    openCamera();

                }else{
                    //permission from popup denied
                    Toast.makeText(this,"Permission denied....",Toast.LENGTH_SHORT).show();

                }

            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            // set the captured image to ImageView
            mImageView.setImageURI(image_uri);
            Bitmap image=getBitmapFromAsset(this,image_uri);
            runTextRecognition(image);
        }
    }
    public static Bitmap getBitmapFromAsset(Context context, Uri image_uri) {
        Bitmap bitmap= null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), image_uri);
            System.out.println("Image converted");
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
    private void processTextRecognitionResult(Text texts) {
        // Replace with code from the codelab to process the text recognition result.
        List<Text.TextBlock> blocks = texts.getTextBlocks();
        String cipherText="";
        if(blocks.size()==0){
//            showToast("No Text Found");
            System.out.println("No text Found");
            return;
        }
//        mGraphicOverlay.clear();
        for(int i=0; i< blocks.size();i++){
            List<Text.Line> lines =blocks.get(i).getLines();
            for(int j=0;j<lines.size();j++){
                List<Text.Element> elements =lines.get(j).getElements();
                for(int k=0;k<elements.size();k++){
//                    GraphicOverlay.Graphic textGraphic = new TextGraphic(mGraphicOverlay,elements.get(k));
//                    mGraphicOverlay.add(textGraphic);
                    cipherText=cipherText+" "+ elements.get(k).getText();
                }

            }
        }
        Intent intent= new Intent(getApplicationContext(),Decrypted.class);
        intent.putExtra("message", decryptText(cipherText.toLowerCase()));

        startActivity(intent);

    }

    public String decryptText(String cipherText){
//        cipherText=  "GFS WMY OG LGDVS MF SFNKYHOSU ESLLMRS, PC WS BFGW POL DMFRQMRS, PL OG CPFU M UPCCSKSFO HDMPFOSXO GC OIS LMES DMFRQMRS DGFR SFGQRI OG CPDD GFS LISSO GK LG, MFU OISF WS NGQFO OIS GNNQKKSFNSL GC SMNI DSOOSK. WS NMDD OIS EGLO CKSJQSFODY GNNQKKPFR DSOOSK OIS 'CPKLO', OIS FSXO EGLO GNNQKKPFR DSOOSK OIS 'LSNGFU' OIS CGDDGWPFR EGLO GNNQKKPFR DSOOSK OIS 'OIPKU', MFU LG GF, QFOPD WS MNNGQFO CGK MDD OIS UPCCSKSFO DSOOSKL PF OIS HDMPFOSXO LMEHDS. OISF WS DGGB MO OIS NPHISK OSXO WS WMFO OG LGDVS MFU WS MDLG NDMLLPCY POL LYEAGDL. WS CPFU OIS EGLO GNNQKKPFR LYEAGD MFU NIMFRS PO OG OIS CGKE GC OIS 'CPKLO' DSOOSK GC OIS HDMPFOSXO LMEHDS, OIS FSXO EGLO NGEEGF LYEAGD PL NIMFRSU OG OIS CGKE GC OIS 'LSNGFU' DSOOSK, MFU OIS CGDDGWPFR EGLO NGEEGF LYEAGD PL NIMFRSU OG OIS CGKE GC OIS 'OIPKU' DSOOSK, MFU LG GF, QFOPD WS MNNGQFO CGK MDD LYEAGDL GC OIS NKYHOGRKME WS WMFO OG LGDVS";

        Map<Character,Character> dKey = new HashMap<>();
        dKey.put('s', 'e');
        dKey.put('o', 't');
        dKey.put('g', 'o');
        dKey.put('f', 'n');
        dKey.put('m', 'a');
        dKey.put('l', 's');
        dKey.put('p', 'i');
        dKey.put('i', 'h');
        dKey.put('c', 'f');
        dKey.put('d', 'l');
        dKey.put('k', 'r');
        dKey.put('u', 'd');
        dKey.put('n', 'c');
        dKey.put('r', 'g');
        dKey.put('q', 'u');
        dKey.put('j', 'q');
        dKey.put('w', 'w');
        dKey.put('y', 'y');
        dKey.put('v', 'v');
        dKey.put('e', 'm');
        dKey.put('a', 'b');
        dKey.put('h', 'p');
        dKey.put('x', 'x');
        dKey.put('b', 'k');

        char[] cipherChar=cipherText.toCharArray();

        for(int i=0;i<cipherChar.length;i++){
            char decryptedLetter= dKey.getOrDefault(cipherChar[i],cipherChar[i]);
            cipherChar[i]=decryptedLetter;

        }

        return  new String(cipherChar);

    }
}