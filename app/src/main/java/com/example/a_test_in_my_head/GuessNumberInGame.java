package com.example.a_test_in_my_head;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GuessNumberInGame extends AppCompatActivity {
    List<String> buttonList = new ArrayList<>();
    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11, btn12, btn13, btn14, btn15, btn16;
    Button[] btnList = {btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11, btn12, btn13, btn14, btn15, btn16};
    Button submit; // 결과 버튼
    TextView resultText; // 결과 text
    TextView resText1;
    TextView resText2;
    TextView important;
    String res1Input, res2Input; // 실제 결과값이 들어갈 부분
    int watchResult;
    int i, j, a;
    // 타이머 변수
    TextView timer; // 타이머 textView
    int value; // 타이머 숫자 표시
    Intent intent1;
    Intent intent2;
    private long backKeyPressedTime = 0;
    boolean answer;
    private boolean flag = true;
    int successcount =0;

    static int score; // 점수
    TextView textViewScore; // 누적된 점수 입력되는 부분.

    public void StopThread(){
        this.flag=true;
    }
    public void Action(boolean flag){
        this.flag = flag;
    }
    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis()>backKeyPressedTime+2000){
            backKeyPressedTime=System.currentTimeMillis();
            Toast.makeText(this,"뒤로가기 버튼을 한번 더 누르시면 종료됩니다!",Toast.LENGTH_SHORT).show();
            GuessNumberInGame.score = 0;
            return;
        } else {
            GuessNumberInGame.score = 0;
            flag = false;
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_number_in_game);

        //textViewScore = (TextView)findViewById(R.id.score);

        // 각 버튼마다 랜덤으로 출력되도록 하기 (안겹치게)    코드 개선하기..
        buttonList.add("1");
        buttonList.add("2");
        buttonList.add("3");
        buttonList.add("4");
        buttonList.add("5");
        buttonList.add("6");
        buttonList.add("7");
        buttonList.add("8");
        buttonList.add("9");
        buttonList.add("10");
        buttonList.add("11");
        buttonList.add("12");
        buttonList.add("+");
        buttonList.add("-");
        buttonList.add("/");
        buttonList.add("*");
        btnList[0] = (Button) findViewById(R.id.btn1);
        btnList[1] = (Button) findViewById(R.id.btn2);
        btnList[2] = (Button) findViewById(R.id.btn3);
        btnList[3] = (Button) findViewById(R.id.btn4);
        btnList[4] = (Button) findViewById(R.id.btn5);
        btnList[5] = (Button) findViewById(R.id.btn6);
        btnList[6] = (Button) findViewById(R.id.btn7);
        btnList[7] = (Button) findViewById(R.id.btn8);
        btnList[8] = (Button) findViewById(R.id.btn9);
        btnList[9] = (Button) findViewById(R.id.btn10);
        btnList[10] = (Button) findViewById(R.id.btn11);
        btnList[11] = (Button) findViewById(R.id.btn12);
        btnList[12] = (Button) findViewById(R.id.btn13);
        btnList[13] = (Button) findViewById(R.id.btn14);
        btnList[14] = (Button) findViewById(R.id.btn15);
        btnList[15] = (Button) findViewById(R.id.btn16);

        Collections.shuffle(buttonList);
        resText1 = (TextView) findViewById(R.id.first);
        resText2 = (TextView) findViewById(R.id.second);
        TextView[] resultArray = {resText1, resText2};
        for (i = 0; i < buttonList.size(); i++) {
            // 코드 개선하기..
            btnList[0].setText(buttonList.get(0));
            btnList[1].setText(buttonList.get(1));
            btnList[2].setText(buttonList.get(2));
            btnList[3].setText(buttonList.get(3));
            btnList[4].setText(buttonList.get(4));
            btnList[5].setText(buttonList.get(5));
            btnList[6].setText(buttonList.get(6));
            btnList[7].setText(buttonList.get(7));
            btnList[8].setText(buttonList.get(8));
            btnList[9].setText(buttonList.get(9));
            btnList[10].setText(buttonList.get(10));
            btnList[11].setText(buttonList.get(11));
            btnList[12].setText(buttonList.get(12));
            btnList[13].setText(buttonList.get(13));
            btnList[14].setText(buttonList.get(14));
            btnList[15].setText(buttonList.get(15));
            // 왜 != 가 안되는지..?
        }

        // 랜덤 값 가져와서 result에 지정하기.
        int random = (int) (Math.random() * 20) + 1; // 1 ~ 20까지의 랜덤 값 지정.
        important = (TextView) findViewById(R.id.important);
        important.setText(String.valueOf(random));
        for (i = 0; i < buttonList.size(); i++) {
            // 코드 개선하기..
            btnList[0].setText(buttonList.get(0));
            btnList[1].setText(buttonList.get(1));
            btnList[2].setText(buttonList.get(2));
            btnList[3].setText(buttonList.get(3));
            btnList[4].setText(buttonList.get(4));
            btnList[5].setText(buttonList.get(5));
            btnList[6].setText(buttonList.get(6));
            btnList[7].setText(buttonList.get(7));
            btnList[8].setText(buttonList.get(8));
            btnList[9].setText(buttonList.get(9));
            btnList[10].setText(buttonList.get(10));
            btnList[11].setText(buttonList.get(11));
            btnList[12].setText(buttonList.get(12));
            btnList[13].setText(buttonList.get(13));
            btnList[14].setText(buttonList.get(14));
            btnList[15].setText(buttonList.get(15));
            // 왜 != 가 안되는지..?
            if (buttonList.get(i) == "1" || buttonList.get(i) == "2" || buttonList.get(i) == "3" || buttonList.get(i) == "4" || buttonList.get(i) == "5" || buttonList.get(i) == "6" || buttonList.get(i) == "7" || buttonList.get(i) == "8" || buttonList.get(i) == "9" || buttonList.get(i) == "10" || buttonList.get(i) == "11" || buttonList.get(i) == "12") {
                final int indexNum; // 이거 안해주면 오류생김..
                indexNum = i;
                btnList[indexNum].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        resultArray[a].setText(btnList[indexNum].getText().toString());
                        a++;
                        if (a == 2) {
                            a = 0;
                        }
                    }
                });
            }
        }
        // 만약 버튼의.getText()가 + 일때 그 버튼을 클릭 시 임시 결과 값을 더해줌.
        for (i = 0; i < buttonList.size(); i++) {
            resultText = (TextView) findViewById(R.id.result);
            if (buttonList.get(i) == "+") {
                btnList[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        res1Input = resText1.getText().toString();
                        res2Input = resText2.getText().toString();
                        watchResult = Integer.parseInt(res1Input) + Integer.parseInt(res2Input);
                        resultText.setText(String.valueOf(watchResult));
                    }
                });
            }
        }
        // 만약 버튼의.getText()가 - 일때 그 버튼을 클릭 시 임시 결과 값을 빼줌.
        for (i = 0; i < buttonList.size(); i++) {
            if (buttonList.get(i) == "-") {
                btnList[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        res1Input = resText1.getText().toString();
                        res2Input = resText2.getText().toString();
                        watchResult = Integer.parseInt(res1Input) - Integer.parseInt(res2Input);
                        resultText.setText(String.valueOf(watchResult));
                    }
                });
            }
        }
        // 만약 버튼의.getText()가 - 일때 그 버튼을 클릭 시 임시 결과 값을 빼줌.
        for (i = 0; i < buttonList.size(); i++) {
            if (buttonList.get(i) == "*") {
                btnList[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        res1Input = resText1.getText().toString();
                        res2Input = resText2.getText().toString();
                        watchResult = Integer.parseInt(res1Input) * Integer.parseInt(res2Input);
                        resultText.setText(String.valueOf(watchResult));
                    }
                });
            }
        }
        // 만약 버튼의.getText()가 / 일때 그 버튼을 클릭 시 임시 결과 값을 빼줌.
        for (i = 0; i < buttonList.size(); i++) {
            if (buttonList.get(i) == "/") {
                btnList[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        res1Input = resText1.getText().toString();
                        res2Input = resText2.getText().toString();
                        watchResult = Integer.parseInt(res1Input) / Integer.parseInt(res2Input);
                        resultText.setText(String.valueOf(watchResult));
                    }
                });
            }
        }
        // 타이머
        timer = (TextView) findViewById(R.id.timer);
        value = 0;
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 5초 카운트 다운
                if (flag) {
                    for (i = 5; i >= 0; i--) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        value = i;
                        GuessNumberInGame.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                timer.setText(String.valueOf(value));
                                if (value == 0) {
                                    if (flag == true) {
                                        Intent intent = new Intent(GuessNumberInGame.this, Timeout.class);

                                        score -= 3; // 실패시 점수 3점 감점.
                                        // score가 0보다 작으면 스코어 0점으로 유지시키기. (-로 안가게 하기)
                                        if(score < 0){
                                            score = 0;
                                        }
                                        Log.i("SCORE", "실패! 점수는 : " + score);
                                        startActivity(intent);
                                    }
                                }
                            }
                        });
                    }
                }
            }
        }).start();



        submit = (Button) findViewById(R.id.submit);
        // 제출을 클릭했을 때 문제랑 결과같 같으면 true, 다르면 false 출력 (임시로 Toast 출력)
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String resultInt = resultText.getText().toString();
                String importantInt = important.getText().toString();
                intent1 = new Intent(GuessNumberInGame.this, Success.class);
                intent2 = new Intent(GuessNumberInGame.this, Wrong.class);
                if (Integer.parseInt(resultInt) == Integer.parseInt(importantInt)) {
                    startActivity(intent1);
                    score += 3;
                } else {
                    score -= 3; // 실패시 점수 3점 감점.
                    // score가 0보다 작으면 스코어 0점으로 유지시키기. (-로 안가게 하기)
                    if(score < 0){
                        score = 0;
                    }
                    Log.i("SCORE", "실패! 점수는 : " + score);
                    startActivity(intent2);
                }
                flag = false;
            }
        });
    }
}