package com.example.delhitour;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class FORGET_PASSWORD_PAGE extends AppCompatActivity {

    String email;
    FirebaseAuth auth;
    TextInputEditText Email_Address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password_page);


        Button Forget_Password= findViewById(R.id.Forget_Password_btn);
        Email_Address = findViewById(R.id.Email_Address_edittxt);
        auth = FirebaseAuth.getInstance();

        Forget_Password.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                validate_password();
            }
        });
    }

    private void validate_password()
    {
        email = Email_Address.getText().toString();
        if(TextUtils.isEmpty(email))
        {
            Email_Address.setError("Required!!");
        }

        else
        {
            forget_password();
        }

    }

    private void forget_password()
    {
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(FORGET_PASSWORD_PAGE.this, "CHECK YOUR EMAIL", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(FORGET_PASSWORD_PAGE.this, START_PAGE.class);
                    startActivity(intent);
                    finish();
                }

                else
                {
                    Toast.makeText(FORGET_PASSWORD_PAGE.this, "ERROR " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    }
