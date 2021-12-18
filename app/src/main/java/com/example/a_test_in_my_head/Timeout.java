package com.example.a_test_in_my_head;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Timeout extends AppCompatActivity {
    Button RetryBtn, BackBtn;
    TextView minusScore;
    GuessNumberInGame easyMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeout);
        //requestWindowFeature( Window.FEATURE_NO_TITLE );

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        RetryBtn = (Button) findViewById(R.id.RetryBtn);
        BackBtn = (Button) findViewById(R.id.BackBtn);
        easyMode = new GuessNumberInGame();
        minusScore = (TextView) findViewById(R.id.minusScore);
        minusScore.setText("누적된 값은 : " + String.valueOf(GuessNumberInGame.score));
    }

    public void Back(View v){
        Intent intent = new Intent(getApplicationContext(),MenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Toast.makeText(getApplicationContext(), "최종 점수는 : " + GuessNumberInGame.score + "입니다!", Toast.LENGTH_SHORT).show();
        GuessNumberInGame.score = 0;

        startActivity(intent);
    }

    public void Retry(View v){
        Intent intent = new Intent(getApplicationContext(),GuessNumberMain.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public boolean onTouchEvent(MotionEvent event){
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;

    }
    public void onBackPressed(){
        return;
    }
}