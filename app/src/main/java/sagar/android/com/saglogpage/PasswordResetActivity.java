package sagar.android.com.saglogpage;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class PasswordResetActivity extends AppCompatActivity {
EditText emailVarify;
Button resetPass;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);

        firebaseAuth=FirebaseAuth.getInstance();
        emailVarify=findViewById(R.id.emailVarify);
        resetPass=findViewById(R.id.resetButton);

        resetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email_verify=emailVarify.getText().toString().trim();
                if (email_verify.isEmpty()){
                    Toast.makeText(PasswordResetActivity.this,"Please enter registered email id..",Toast.LENGTH_SHORT).show();
                }else {

                    firebaseAuth.sendPasswordResetEmail(email_verify).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                    Toast.makeText(PasswordResetActivity.this,"Password Reset Link sent to Registered email id..",Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(PasswordResetActivity.this,LoginActivity.class));
                            }else{
                                Toast.makeText(PasswordResetActivity.this,"Invalid Email Id ....",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }
}
