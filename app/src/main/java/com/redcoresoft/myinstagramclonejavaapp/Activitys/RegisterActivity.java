package com.redcoresoft.myinstagramclonejavaapp.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.redcoresoft.myinstagramclonejavaapp.R;
import com.redcoresoft.myinstagramclonejavaapp.databinding.ActivityRegisterBinding;

import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageButton navigationDrawerButton;

    ImageView imageViewOnRegister;

    private ActivityRegisterBinding binding;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        auth = FirebaseAuth.getInstance();


        imageViewOnRegister = findViewById(R.id.imageViewOnRegister);
        imageViewOnRegister.setVisibility(View.VISIBLE);
        FirebaseUser user = auth.getCurrentUser();
        setUpToolbar();

    }

    public void txtIHaveAccountOnClick(View view){
        Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
    public void setUpToolbar() {
        toolbar = findViewById(R.id.toolBarAtRegister);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    public void onClickBtnRegister(View view) {

        String email = binding.txtEmailOnRegister.getText().toString().trim().toLowerCase(Locale.ROOT);
        String password = binding.txtPasswordOnRegister.getText().toString().trim();
        String passwordCheck = binding.txtPasswordChechOnRegister.getText().toString().trim();

        if (email.equals("") || password.equals("") || passwordCheck.equals("")){
            Toast.makeText(RegisterActivity.this,"Enter e-mail and passwords !",Toast.LENGTH_LONG).show();
        }else if (password.equals(passwordCheck) == false){
            Toast.makeText(RegisterActivity.this,"Passwords Do not match !",Toast.LENGTH_LONG).show();
        }
        else if(email.isEmpty()) {
            Toast.makeText(RegisterActivity.this,"Enter e-mail !",Toast.LENGTH_LONG).show();
        }else if(password.isEmpty() ){
            Toast.makeText(RegisterActivity.this,"Enter password !",Toast.LENGTH_LONG).show();
        }else if (password.length()<6){
            Toast.makeText(RegisterActivity.this,"password should be minimum 6 character",Toast.LENGTH_LONG).show();
        } else {
            auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Intent intent = new Intent(RegisterActivity.this, FeedActivity.class);
                    startActivity(intent);
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(RegisterActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            });

        }
    }
}