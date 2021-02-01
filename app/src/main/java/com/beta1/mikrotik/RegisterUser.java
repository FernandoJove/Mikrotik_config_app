package com.beta1.mikrotik;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterUser extends AppCompatActivity {


    EditText email, password;
    FirebaseAuth mFirebaseAuth;

    EditText input_email, input_password;
    Button btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        mFirebaseAuth = FirebaseAuth.getInstance();

        email = (EditText)findViewById(R.id.input_email);

        password = (EditText)findViewById(R.id.input_password);

        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_ = email.getText().toString();
                String password_ = password.getText().toString();

                if(email_.isEmpty()){
                    input_email.setError("Enter a valid email");
                    input_email.requestFocus();
                }else if(password_.isEmpty()){
                    input_password.setError("Enter a valid password");
                    input_password.requestFocus();
                }else if(password_.length()<6){
                    input_password.setError("The password must be more than 6 characters");
                    input_password.requestFocus();
                }else if (password_.isEmpty() && email_.isEmpty()){
                    Toast.makeText(RegisterUser.this,"Fields are empty",Toast.LENGTH_SHORT).show();
                }else if (!(password_.isEmpty() && email_.isEmpty())) {
                    mFirebaseAuth.createUserWithEmailAndPassword(email_,password_).addOnCompleteListener(RegisterUser.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(RegisterUser.this,"Failed",Toast.LENGTH_SHORT).show();
                            }else {
                                startActivity(new Intent(RegisterUser.this, Config.class));
                            }
                        }
                    });
                }else{
                    Toast.makeText(RegisterUser.this,"Error Occurred", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    public void register_user(){
        /*String regi_name = name.getText().toString().trim();
        String regi_email = email.getText().toString().trim();
        String regi_password = password.getText().toString().trim();



        mFirebaseAuth = FirebaseAuth.getInstance();
        input_email = findViewById(R.id.input_email);

        input_password = findViewById(R.id.input_password);
 */

/*

    mAuth.createUserWithEmailAndPassword(regi_email,regi_password)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        User user = new User(regi_name,regi_email,regi_password);

                        FirebaseDatabase.getInstance().getReference("Users")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(RegisterUser.this,"Se registro el usuario satisfactoriamente",Toast.LENGTH_LONG).show();
                                }else{
                                    Toast.makeText(RegisterUser.this,"Error al registrar", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }else{
                        Toast.makeText(RegisterUser.this,"Error al registrar", Toast.LENGTH_LONG).show();
                    }
                }
            });*/
    }

}