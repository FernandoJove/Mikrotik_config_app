package com.beta1.mikrotik;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;

public class Config extends AppCompatActivity {
    EditText e1;

    Button btn_save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);


        btn_save = findViewById(R.id.btn_createfile);
        btn_save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                create_file();
            }
        });
    }


    private void create_file(){
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType(".cfg.rsc -> text/plain");
        intent.putExtra(Intent.EXTRA_TITLE, "text.cfg.rsc");
        startActivityForResult(intent,1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        e1 = (EditText) findViewById(R.id.text_input);
        super.onActivityResult(requestCode,resultCode,data);

        //Contenido

        String text_ ="add name=WIFI-"+ e1.getText().toString();


        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                Uri uri= data.getData();
                try{
                    OutputStream outputStream  = getContentResolver().openOutputStream(uri);
                    outputStream.write( text_.getBytes());
                    outputStream.close();
                    Toast.makeText(this,"Doc saved Succesfully ", Toast.LENGTH_SHORT).show();
                }catch (IOException e){
                    Toast.makeText(this,"Fail to save doc", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this,"File not saved", Toast.LENGTH_SHORT).show();
            }
        }
    }
}