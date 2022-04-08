package com.redcoresoft.myinstagramclonejavaapp.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.redcoresoft.myinstagramclonejavaapp.R;
import com.redcoresoft.myinstagramclonejavaapp.databinding.ActivityMainBinding;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private FirebaseAuth auth;
    ImageView imageViewOnEnterance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        auth = FirebaseAuth.getInstance();

        imageViewOnEnterance = findViewById(R.id.imageViewOnEnterance);
        imageViewOnEnterance.setVisibility(View.VISIBLE);

        FirebaseUser user = auth.getCurrentUser();
        if (user != null ){
            Intent intent = new Intent(MainActivity.this,FeedActivity.class);
            startActivity(intent);
            finish();
        }

    }


    public void txtCreateAnAccountOnClick(View view){
        Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
        startActivity(intent);
    }


    public void onClickBtnSignIn(View view){

        String email = binding.txtEmailOnEnterance.getText().toString().trim().toLowerCase(Locale.ROOT);
        String password = binding.txtPassword.getText().toString().trim();

        if (email.equals("") && password.equals("")){
            Toast.makeText(MainActivity.this,"Enter e-mail and password !",Toast.LENGTH_LONG).show();
        }
        else if(email.isEmpty()) {
            Toast.makeText(MainActivity.this,"Enter e-mail !",Toast.LENGTH_LONG).show();
        }else if(password.isEmpty()){
            Toast.makeText(MainActivity.this,"Enter password !",Toast.LENGTH_LONG).show();
        }else if (password.length()<6){
            Toast.makeText(MainActivity.this,"password should be minimum 6 character",Toast.LENGTH_LONG).show();
        }
        else {
            auth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Intent intent = new Intent(MainActivity.this,FeedActivity.class);
                    startActivity(intent);
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }

    }

}
