package sagar.android.com.saglogpage;

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

public class RegistrationActivity extends AppCompatActivity {
private EditText regName,regEmail,regMob,regPass;
private Button registerButton;
private TextView regLogin;
private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        regName=findViewById(R.id.regName);
        regEmail=findViewById(R.id.regEmail);
        regMob=findViewById(R.id.regMob);
        regPass=findViewById(R.id.regPass);
        registerButton=findViewById(R.id.regButton);
        regLogin=findViewById(R.id.regLogin);

        firebaseAuth=FirebaseAuth.getInstance();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isvalidate()){
String user_email=regEmail.getText().toString().trim();
String user_pass=regPass.getText().toString().trim();

firebaseAuth.createUserWithEmailAndPassword(user_email,user_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        if (task.isSuccessful()){
           sendEmailVarificationLink();
        }else{
            Toast.makeText(RegistrationActivity.this,"Registration is Failed",Toast.LENGTH_SHORT).show();
        }
    }
});
                }

            }
        });

        regLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationActivity.this,LoginActivity.class));
            }
        });


    }

    private boolean isvalidate(){
        String name=regName.getText().toString();
        String email=regEmail.getText().toString();
        String mobile=regMob.getText().toString();
        String pass=regPass.getText().toString();

        if(name.isEmpty()|| email.isEmpty()||mobile.isEmpty()||pass.isEmpty()){

            Toast.makeText(this,"Fields are Empty",Toast.LENGTH_SHORT).show();
        }else{
            return true;
        }

        return false;
    }

    private void sendEmailVarificationLink(){
        final FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        if (firebaseUser!=null) {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                    Toast.makeText(RegistrationActivity.this,"Registration Done Successfully, Varification email sent...",Toast.LENGTH_SHORT).show();
                    firebaseAuth.signOut();
                    finish();
                    startActivity(new Intent(RegistrationActivity.this,LoginActivity.class));
                    }else {
                        Toast.makeText(RegistrationActivity.this,"Technical Issues, Please Register again... ",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
}
