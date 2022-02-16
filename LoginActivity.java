package com.codewithsujan.journeyjournal;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText text_email, text_password;
    Button btn_signup;
    ImageButton btn_signin, btn_google, btn_facebook, btn_twitter;
    CheckBox checkBox;
    TextView text_forgetpassword;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        text_email=(EditText) findViewById(R.id.email);
        text_password=(EditText) findViewById(R.id.password);
        btn_signin=(ImageButton) findViewById(R.id.signin);
        btn_signup=(Button) findViewById(R.id.signup);
        btn_google = (ImageButton) findViewById(R.id.google);
        btn_facebook = (ImageButton) findViewById(R.id.facebook);
        btn_twitter = (ImageButton) findViewById(R.id.twitter);
        checkBox = (CheckBox) findViewById(R.id.checkbox);
        text_forgetpassword = (TextView) findViewById(R.id.forgot_password);
        firebaseAuth = FirebaseAuth.getInstance();

        text_forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        btn_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://facebook.com/login"));
                startActivity(browserIntent);
            }
        });

        btn_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://google.com/login"));
                startActivity(browserIntent);
            }
        });

        btn_twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/login"));
                startActivity(browserIntent);
            }
        });


        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = text_email.getText().toString().trim();
                String password = text_password.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    text_email.setError("Email is Required");

                }

                if (TextUtils.isEmpty(password)) {
                    text_password.setError("Password is Required");

                }

                if (password.length() < 6) {
                    text_password.setError("Password must be atleast 6 characters");
                }

                //authentication the user

                firebaseAuth.signInWithEmailAndPassword(email, password).
                        addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(LoginActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), HomepageActivity.class));
                                } else {
                                    Toast.makeText(LoginActivity.this, "Error while logging" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });
    }
}
