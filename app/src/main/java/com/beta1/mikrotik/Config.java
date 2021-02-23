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

import me.legrange.mikrotik.ApiConnection;
import me.legrange.mikrotik.MikrotikApiException;

public class Config extends AppCompatActivity {
    EditText e1;

    Button btn_save;
    Button send_file;
    Button btn_wifi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        send_file= findViewById(R.id.send_file);
        btn_save = findViewById(R.id.btn_createfile);
        btn_wifi = findViewById(R.id.id_wifi);

        btn_save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                create_file();
            }
        });

        btn_wifi.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Thread thread_ = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try  {
                            try{
                                request_wifi_status();
                            }catch(Exception e){
                                System.out.println(e);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread_.start();
            }
        });

        send_file.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                Thread thread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try  {
                            try{
                                send_file_();

                            }catch(Exception e){
                                System.out.println(e);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
            }
            });
    }

    private void request_wifi_status()   throws Exception{
        Intent log = new Intent(Config.this,wifi_details.class);
        startActivity(log);
        finish();

    }


    private void create_file(){
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType(".cfg.rsc -> text/plain");
        intent.putExtra(Intent.EXTRA_TITLE, "text.cfg.rsc");
        startActivityForResult(intent,1);


    }

    private void send_file_() throws Exception{
     try{
         e1 = (EditText) findViewById(R.id.text_input);
         ApiConnection con = ApiConnection.connect("192.168.88.1"); // connect to router
         con.login("admin",""); // log in to router
         con.execute("/system/reboot"); // execute a command
         con.close();
     }  catch(MikrotikApiException ex){
         System.out.println(ex);

     }catch (Exception e){
         System.out.println(e);
     }


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