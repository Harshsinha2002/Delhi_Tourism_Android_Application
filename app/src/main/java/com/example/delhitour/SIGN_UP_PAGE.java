package com.example.delhitour;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SIGN_UP_PAGE extends AppCompatActivity {

    String email, password;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        Button sign_up_btn = findViewById(R.id.Sign_Up_btn);
        TextInputEditText email_txt = findViewById(R.id.Email_Address_edittxt);
        TextInputEditText password_txt = findViewById(R.id.Password_edittxt);

        sign_up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = String.valueOf(email_txt.getText());
                password = String.valueOf(password_txt.getText());

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(SIGN_UP_PAGE.this, "ENTER YOUR EMAIL ADDRESS!!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(SIGN_UP_PAGE.this, "ENTER YOUR PASSWORD!!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (password.length() < 6) {
                    Toast.makeText(SIGN_UP_PAGE.this, "YOUR PASSWORD MUST CONTAIN 6 LETTERS!!", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(SIGN_UP_PAGE.this, "Account Created", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(SIGN_UP_PAGE.this, START_PAGE.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(SIGN_UP_PAGE.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    public void onStart()
    {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null)
        {
            Intent intent = new Intent(SIGN_UP_PAGE.this, MAIN_ACTIVIY_1.class);
            startActivity(intent);
            finish();
        }
    }
}