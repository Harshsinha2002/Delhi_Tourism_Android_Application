package com.example.delhitour;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class START_PAGE extends AppCompatActivity {

    private FirebaseAuth mAuth;
    public String email_address, password;

    @Override
    public void onStart()
    {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null)
        {
            currentUser.reload();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        EditText emailAddress_txt = findViewById(R.id.Email_Address_edittxt);
        EditText passsword_txt = findViewById(R.id.Password_edittxt);
        ImageView Start_Exploring = findViewById(R.id.start_Exploring_txt);
        TextView forget_txt = findViewById(R.id.Forget_Password_txt);
        TextView sign_up_txt = findViewById(R.id.Sign_Up_txt);

        forget_txt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(START_PAGE.this, FORGET_PASSWORD_PAGE.class);
                startActivity(intent);
            }
        });

        sign_up_txt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(START_PAGE.this, SIGN_UP_PAGE.class);
                startActivity(intent);
            }
        });
        Start_Exploring.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                email_address = emailAddress_txt.getText().toString();
                password = passsword_txt.getText().toString();

                if (TextUtils.isEmpty(email_address))
                {
                    Toast.makeText(START_PAGE.this, "ENTER EMAIL ID...." , Toast.LENGTH_SHORT).show();
                    emailAddress_txt.setError("");
                }

                else if (TextUtils.isEmpty(password))
                {
                    Toast.makeText(START_PAGE.this, "ENTER PASSWORD...." , Toast.LENGTH_SHORT).show();
                    passsword_txt.setError("");
                }

                else
                {
                    mAuth.signInWithEmailAndPassword(email_address, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(START_PAGE.this, MAIN_ACTIVIY_1.class);
                                        startActivity(intent);
                                        finish();
                                    }

                                    else
                                    {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                                        Toast.makeText(START_PAGE.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }
}