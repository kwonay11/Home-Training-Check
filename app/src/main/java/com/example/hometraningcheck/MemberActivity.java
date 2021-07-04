package com.example.hometraningcheck;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;


public class MemberActivity extends AppCompatActivity {
    private static final String TAG = "MemberInitActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_init);

        findViewById(R.id.checkButton).setOnClickListener(onClickListener);
        findViewById(R.id.gotoPasswordButton).setOnClickListener(onClickListener);



    }
    @Override
    public void onBackPressed(){ //뒤로가기 버튼누르면 앱 나가기
        super.onBackPressed();
        finish();
    }

    View.OnClickListener onClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.checkButton:
                    profile();
                    break;
                case R.id.gotoPasswordButton: //비밀번호 변경 엑티비티로 보내기
                    myStartActivity(PasswordResetActivity.class);
                    break;

            }
        }
    };
    private void profile(){
        //일반 뷰는 getText를 하지 못해서 EditText로 형변환
        // 유저 이름 받아서
        String name = ((EditText)findViewById(R.id.nameText)).getText().toString();
        String recentWeight = ((EditText)findViewById(R.id.recentWeightText)).getText().toString();
        String wishWeight = ((EditText)findViewById(R.id.wishWeightText)).getText().toString();
        String wishDate = ((EditText)findViewById(R.id.wishtDateText)).getText().toString();


        if(name.length() > 0 && recentWeight.length() > 0 && wishWeight.length() > 0 && wishDate.length() > 0){
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            FirebaseFirestore db = FirebaseFirestore.getInstance(); //데이터베이스 초기화

            MemberInfo memberInfo = new MemberInfo(name,recentWeight,wishWeight,wishDate);
            if (user != null){
                db.collection("users").document(user.getUid()).set(memberInfo)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                startTost("회원 정보 등록에 성공하였습니다.");
//                                finish(); //등록하면 화면 나가기
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                startTost("회원 정보 등록에 실패하였습니다.");
//                                Log.w(TAG, "Error writing document", e);
                            }
                        });
            }

        }else{
            startTost("회원 정보를 입력해 주세요.");
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
