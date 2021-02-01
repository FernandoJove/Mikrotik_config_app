package com.beta1.mikrotik;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private TextView registro;
    FirebaseAuth mFirebaseAuth;
    Button btn_login_;
    EditText login_email_, login_password_;

    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();
        login_email_ = findViewById(R.id.login_email);
        login_password_ = findViewById(R.id.login_password);
        btn_login_ = findViewById(R.id.btn_login);
        registro = findViewById(R.id.text_register);

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if (mFirebaseUser != null) {
                    Toast.makeText(MainActivity.this,"You're now logged in", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MainActivity.this, Config.class);
                    startActivity(i);
                }else{
                    Toast.makeText(MainActivity.this,"Please login first", Toast.LENGTH_SHORT).show();

                }
            }
        };
        btn_login_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String login_email_ID = login_email_.getText().toString();
            String login_password_ID= login_password_.getText().toString();

                if(login_email_ID.isEmpty()){
                    login_email_.setError("Enter a valid email");
                    login_email_.requestFocus();
                }else if(login_password_ID.isEmpty()){
                    login_password_.setError("Enter a valid password");
                    login_password_.requestFocus();
                }else if(login_password_ID.length()<6){
                    login_password_.setError("The password must be more than 6 characters");
                    login_password_.requestFocus();
                }else if (login_password_ID.isEmpty() && login_email_ID.isEmpty()){
                    Toast.makeText(MainActivity.this,"Fields are empty",Toast.LENGTH_SHORT).show();
                }else if (!(login_password_ID.isEmpty() && login_email_ID.isEmpty())) {
                mFirebaseAuth.signInWithEmailAndPassword(login_email_ID,login_password_ID).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(MainActivity.this,"Verify credentials", Toast.LENGTH_LONG).show();
                        }else{
                            Intent log = new Intent(MainActivity.this,Config.class);
                            startActivity(log);
                            finish();
                        }
                    }
                });
                }else{
                    Toast.makeText(MainActivity.this,"Error Occurred", Toast.LENGTH_LONG).show();
                }
            }
        });
        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent singup = new Intent(MainActivity.this, RegisterUser.class);
                startActivity(singup);

            }
        });

    }
}
      //
/*

        input_email = findViewById(R.id.input_email);

        input_password = findViewById(R.id.input_password);
        btn_register = findViewById(R.id.btn_login);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = input_email.getText().toString();
                String password = input_password.getText().toString();

                if(email.isEmpty()){
                    input_email.setError("Enter a valid email");
                    input_email.requestFocus();
                }else if(password.isEmpty()){
                    input_password.setError("Enter a valid email");
                    input_password.requestFocus();
                }else if (password.isEmpty() && email.isEmpty()){
                    Toast.makeText(MainActivity.this,"Fields are empty",Toast.LENGTH_SHORT).show();
                }else if (!(password.isEmpty() && email.isEmpty())) {
                    mFirebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(MainActivity.this,"Failed",Toast.LENGTH_SHORT).show();
                            }else {
                                startActivity(new Intent(MainActivity.this, Config.class));
                            }
                        }
                    });
                }

            }
        });*/

/*


    @Override


    public void onClick(View v) {
    super.onStart();
     mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }*/

