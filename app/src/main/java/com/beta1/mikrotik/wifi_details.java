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
import java.util.List;
import java.util.Map;

import me.legrange.mikrotik.ApiConnection;
import me.legrange.mikrotik.MikrotikApiException;

public class wifi_details extends AppCompatActivity {

    Button btn_savewifi;
    EditText mikrotik_ip;
   int datos = 10000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_details);

        btn_savewifi = findViewById(R.id.wifi_details);

        btn_savewifi.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                getData();
            }
        });
    }
    private void getData(){
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TITLE, "text.txt");
        startActivityForResult(intent,1);
    }

    Thread thread_reboot = new Thread(new Runnable() {

        @Override
        public void run() {
            try  {
                //Your code goes here
                mikrotik_ip = (EditText) findViewById(R.id.ip_mikrotik);
                String ip = mikrotik_ip.getText().toString();
                ApiConnection con = ApiConnection.connect(ip); // connect to router
                con.login("admin",""); // log in to router

                con.execute("/system/reboot");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    });

    Thread thread_wifi = new Thread(new Runnable() {

        @Override
        public void run() {
            try  {
                //Your code goes here

                String ip = mikrotik_ip.getText().toString();
                ApiConnection con = ApiConnection.connect(ip); // connect to router
                con.login("admin",""); // log in to router
                con.execute("/system/reboot");
                //datos = con.execute("/interface/wireless/registration-table/print"); // execute a command
                datos = datos + 99;
                System.out.println(datos);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    });


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        mikrotik_ip = (EditText) findViewById(R.id.ip_mikrotik);
        String ip = mikrotik_ip.getText().toString();

        super.onActivityResult(requestCode,resultCode,data);

        System.out.println(datos + "Soy datos activity result");

        try{
            System.out.println(ip);
            thread_reboot.start();
            thread_wifi.start();

            if(requestCode == 1){
                if(resultCode == RESULT_OK){
                    Uri uri= data.getData();
                    try{
                        OutputStream outputStream  = getContentResolver().openOutputStream(uri);
                        outputStream.write( ip.getBytes());
                        System.out.println(ip.getBytes());

                        outputStream.close();
                        Toast.makeText(this,"Doc saved Succesfully ", Toast.LENGTH_SHORT).show();
                    }catch (IOException e){
                        Toast.makeText(this,"Fail to save doc", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this,"File not saved", Toast.LENGTH_SHORT).show();
                }
            }
        }catch (Exception e){
            System.out.println(e);
        }

    }

}