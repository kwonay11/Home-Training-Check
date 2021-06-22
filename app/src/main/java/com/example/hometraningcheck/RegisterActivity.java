package com.example.hometraningcheck;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.registerButton).setOnClickListener(onClickListener);
        findViewById(R.id.gotoLoginButton).setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.registerButton: //회원가입버튼 눌렀을 때 레지트터라는 함수 실행
                    Register();
                    break;

                case R.id.gotoLoginButton: //회원가입에서 로그인버튼 눌렀을 때  로그인으로 감
                    startLoginActivity(LoginActivity.class);
                    break;
            }
        }
    };
    private void Register(){
    //일반 뷰는 getText를 하지 못해서 EditText로 형변환
        String email = ((EditText)findViewById(R.id.emailText)).getText().toString();
        String password = ((EditText)findViewById(R.id.passwordText)).getText().toString();
        String passwordCheck = ((EditText)findViewById(R.id.passwordCheckText)).getText().toString();

        if(email.length() > 0 && password.length() > 0 && passwordCheck.length() > 0){
            if(password.length() < 6){
                startTost("비밀번호는 최소 6자 이상 입력해주세요.");
            }
            if(password.equals(passwordCheck)){
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    //성공했을 때 UI로직
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    startTost("회원가입에 성공했습니다.");
                                    finish();//로그인은 끝냄

                                } else {
                                    // 실패했을 때 에러 내용 띄움
                                    if(task.getException()!= null){
                                        startTost(task.getException().toString());
                                    }
                                }
                            }
                        });
            }else{
                startTost("비밀번호가 일치하지 않습니다.");
            }
        }else{
            startTost("이메일 또는 비밀번호를 입력해 주세요.");
        }




    }
    private void startTost(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show(); }
    private void startLoginActivity(Class c){
        Intent intent = new Intent(this,c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //전에 했던 엑티비티 삭제
        startActivity(intent);
    }

}