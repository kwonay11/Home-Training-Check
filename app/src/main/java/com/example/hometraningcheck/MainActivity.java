package com.example.hometraningcheck;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user == null){
            //확인된 유저가 없으면 회원가입으로 넘어감
            myStartActivity(RegisterActivity.class);
        }else{
            // 유저 존재하면
            //사용자 프로필 정보 가져오기
            myStartActivity(MemberActivity.class);

        }
        findViewById(R.id.logoutButton).setOnClickListener(onClickListener);

    }
    View.OnClickListener onClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.logoutButton: //로그아웃하면 로그인화면으로 넘어감
                    FirebaseAuth.getInstance().signOut();
                    myStartActivity(LoginActivity.class);

                    break;

            }
        }
    };


    private void myStartActivity(Class c){
        Intent intent = new Intent(this,c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //전에 했던 엑티비티 삭제
        startActivity(intent);
    }
}
