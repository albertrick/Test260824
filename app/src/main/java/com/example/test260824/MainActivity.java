package com.example.test260824;

import static com.example.test260824.FBRef.refAuth;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createUser("a@a.com", "123456");

    }
    private void createUser(String email, String pass) {
        refAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.i("MainActivity", "createUserWithEmailAndPassword:success");
                            FirebaseUser user = refAuth.getCurrentUser();
                            Toast.makeText(MainActivity.this, "User created successfully\nUid: "+user.getUid(), Toast.LENGTH_LONG).show();
                        } else {
                            Exception exp = task.getException();
                            if (exp instanceof FirebaseAuthInvalidUserException){
                                Toast.makeText(MainActivity.this, "Invalid email address.", Toast.LENGTH_SHORT).show();
                            } else if (exp instanceof FirebaseAuthWeakPasswordException) {
                                Toast.makeText(MainActivity.this, "Password too weak.", Toast.LENGTH_SHORT).show();
                            } else if (exp instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(MainActivity.this, "User already exists.", Toast.LENGTH_SHORT).show();
                            } else if (exp instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(MainActivity.this, "General authentication failure.", Toast.LENGTH_SHORT).show();
                            } else if (exp instanceof FirebaseNetworkException) {
                                Toast.makeText(MainActivity.this, "Network error. Please check your connection.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, "An error occurred. Please try again later.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }
}