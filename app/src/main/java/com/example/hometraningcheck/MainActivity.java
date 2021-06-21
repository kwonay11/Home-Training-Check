package com.example.hometraningcheck;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(FirebaseAuth.getInstance().getCurrentUser() == null){
            //확인된 유저가 없으면 회원가입으로 넘어감
            startRegisterActivity();
        }
        findViewById(R.id.logoutButton).setOnClickListener(onClickListener);

    }
    View.OnClickListener onClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.logoutButton: //로그아웃하면 로그인화면으로 넘어감
                    FirebaseAuth.getInstance().signOut();
                    startLoginActivity();

                    break;

            }
        }
    };

    private void startRegisterActivity(){
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }

    private void startLoginActivity(){
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }
}
