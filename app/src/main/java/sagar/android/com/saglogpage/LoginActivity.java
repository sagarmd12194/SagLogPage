package sagar.android.com.saglogpage;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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

public class LoginActivity extends AppCompatActivity {
private EditText userName,passWord;
private Button login;
private TextView sinup,passwordReset;
private FirebaseAuth firebaseAuth;
private ProgressDialog progressDialog;
private int counter=5;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userName=findViewById(R.id.uName);
        passWord=findViewById(R.id.pWord);
        login= findViewById(R.id.loginbutton);
        firebaseAuth=FirebaseAuth.getInstance();
        sinup=findViewById(R.id.signupLink);

        passwordReset=findViewById(R.id.forgotPass);



        firebaseAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);
        final FirebaseUser user= firebaseAuth.getCurrentUser();
   /*     if (user!= null){
            finish();
            startActivity(new Intent(LoginActivity.this,AfterLoginActivity.class));
        }*/

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_name=userName.getText().toString().trim();
                String user_email=passWord.getText().toString().trim();
                validate(user_name,user_email);
            }
        });

        sinup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegistrationActivity.class));
            }
        });
        passwordReset.setOnClickListener(new View.OnClickListener() {
        @Override
         public void onClick(View view) {
        startActivity(new Intent(LoginActivity.this,PasswordResetActivity.class));
        }
});

    }
    @SuppressLint("SetTextI18n")
    private void validate(String uName,String uEmail) {
        progressDialog.setMessage("You are entering to Sagar's New Android App....");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(uName, uEmail).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    progressDialog.dismiss();
                    checkEmailVarification();
                }else {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this,"Login Failed....",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void checkEmailVarification(){

        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        boolean isEmailVarified=firebaseUser.isEmailVerified();
        if(isEmailVarified){
            finish();
            Toast.makeText(LoginActivity.this,"Login Successfull....",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(LoginActivity.this,AfterLoginActivity.class));
        }else{
            Toast.makeText(this,"Please varify your email address ..",Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }

    }
}
