package com.example.hometraningcheck;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.loginButton).setOnClickListener(onClickListener);
        findViewById(R.id.gotoPasswordButton).setOnClickListener(onClickListener);

        findViewById(R.id.registerButton).setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.loginButton: //로그인 엑티비티로 보내기
                    Login();
                    break;
                case R.id.gotoPasswordButton: //비밀번호 변경 엑티비티로 보내기
                    myStartActivity(PasswordResetActivity.class);
                    break;
                case R.id.registerButton: //회원가입액티비티로 보내기
                    myStartActivity(RegisterActivity.class);
                    break;

            }
        }
    };
    private void Login(){
        //일반 뷰는 getText를 하지 못해서 EditText로 형변환
        String email = ((EditText)findViewById(R.id.emailText)).getText().toString();
        String password = ((EditText)findViewById(R.id.passwordText)).getText().toString();

        if(email.length() > 0 && password.length() > 0){
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                startTost("로그인 성공");
                                myStartActivity(MainActivity.class); //메인으로 이동
                            } else {
                                // If sign in fails, display a message to the user.
                                if(task.getException()!= null){
                                    startTost(task.getException().toString());
                                }

                            }
                        }
                    });


        }else{
            startTost("이메일 또는 비밀번호를 입력해 주세요.");
        }




    }
    private void startTost(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

    }
    private void myStartActivity(Class c){
        Intent intent = new Intent(this,c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //전에 했던 엑티비티 삭제
        startActivity(intent);
    }

}
