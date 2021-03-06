package com.example.a_test_in_my_head;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;

public class JoinMembershipActivity extends AppCompatActivity {
    private final String checkOverlapURL = "http://192.168.0.10:3000/checkOverlap";
    private final String joinURL = "http://192.168.0.10:3000/join";
    private EditText name;
    private EditText id;
    private EditText password;
    private EditText password2;
    private EditText nickname;
    private EditText phoneNumber;
    private EditText email;
    private Boolean idOverlapFlag;
    private Boolean nicknameOverlapFlag;
    private Boolean pwCheckFlag;
    private Boolean phoneNumCheckFlag;
    private Boolean emailCheckFlag;

    private String secretPassword;
    private JSONObject JsonObj;
    String tag = "JoinMembershipActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_membership);

        name = findViewById(R.id.nameEditText);
        id = findViewById(R.id.idEditText);
        password = findViewById(R.id.pwEditText);
        password2 = findViewById(R.id.pwEditText2);
        nickname = findViewById(R.id.nicknameEditText);
        phoneNumber = findViewById(R.id.phoneNumberEditText);
        email = findViewById(R.id.emailEditText);

        idOverlapFlag = false;
        pwCheckFlag = false;
        nicknameOverlapFlag = false;
        phoneNumCheckFlag = false;
        emailCheckFlag = false;
    }

    public void onClickCheckOverlap(View v){

        JsonObj = new JSONObject();
        Boolean overlapFlag = v.getId() == R.id.idCheckBtn;                        // id: ture, nickname: false
        Log.i(tag, "overlapFlag: " + overlapFlag);

        try {
            if (overlapFlag)
                JsonObj.accumulate("id", id.getText());
            else
                JsonObj.accumulate("nickname", nickname.getText());

            RequestingServer req = new RequestingServer(this, JsonObj);
            String response = req.execute(checkOverlapURL).get();
            Log.i(tag, "result: " + response);

            if (response == null)
                return;
            String[] resResult = response.split(",");

            Log.i(tag, "overlap: " + resResult[0]);

            switch (resResult[0]){
                case "can be used":
                    if (overlapFlag)
                        idOverlapFlag = true;
                    else
                        nicknameOverlapFlag = true;
                    Toast.makeText(this, "?????? ???????????????!!", Toast.LENGTH_SHORT).show();
                    break;
                case "overlap":
                    Toast.makeText(this, "???????????????!!", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void onClickCheckPw(View v){

        if (password.getText().toString().equals(password2.getText().toString())){
            
            //???????????? ????????? ??????
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-512");
                md.update(password.getText().toString().getBytes());
                secretPassword = String.format("%128x", new BigInteger(1, md.digest()));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

            Log.i(tag, "secretPassword: " + secretPassword);
            Log.i(tag, "secretPassword ??????: " + secretPassword.length() + "");

            pwCheckFlag = true;
            Toast.makeText(getApplicationContext(), "???????????? ?????? ?????????????????????!", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(getApplicationContext(), "??????????????? ?????? ????????????!", Toast.LENGTH_SHORT).show();
    }

    public void onClickCheckPhoneNumber(View v){
        String phoneNum = phoneNumber.getText().toString();

        if (phoneNum.length() != 13)
            Toast.makeText(getApplicationContext(), "???????????? '-'?????? 13????????? ??????????????????!", Toast.LENGTH_SHORT).show();
        else if ((phoneNum.charAt(3) != '-') || (phoneNum.charAt(8) != '-'))
            Toast.makeText(getApplicationContext(), "4??????, 8?????? ????????? '-'??? ???????????? ??????????????????!", Toast.LENGTH_SHORT).show();
        else if (CheckChar(phoneNum)) {                                                                              
            Toast.makeText(getApplicationContext(), "???????????? ????????? ?????????????????????!", Toast.LENGTH_SHORT).show();
            phoneNumCheckFlag = true;
        }
        else
            Toast.makeText(getApplicationContext(), "??????????????? ????????? ???????????????!", Toast.LENGTH_SHORT).show();
    }
    
    // ??????????????? ????????? ??????????????? ???????????? ?????????
    public Boolean CheckChar(String phoneNum){
        String numberChar = "0123456789";

        for (int i=0; i<phoneNum.length(); i++){
            if ((i == 3) || (i == 8)) continue;
            if (!(numberChar.contains(phoneNum.charAt(i)+"")))
                return false;
            Log.i(tag, "CheckCharacter: " + i);
        }
        return true;
    }
    
    public void onClickCheckEmail(View v){

        String emailText = email.getText().toString();

        String[] emailSplitAt = emailText.split("@");
        String[] emailSplitDot = emailText.split("[.]");             //  '.' ????????? ??? "[.]" or "\\."
//        Log.i(tag, "emailTextSplit.length(): " + emailSplitAt.length);
//        Log.i(tag, "emailTextSplit2.length(): " + emailSplitDot.length);

        if ((emailSplitDot.length < 2) || (emailSplitDot.length > 2)) {
            Toast.makeText(getApplicationContext(), "????????? ????????? ?????? ??????????????????!", Toast.LENGTH_SHORT).show();
            return;
        }
        String[] emailSplitAt2 = emailSplitDot[0].split("@");

        if (emailText.length() > 30)
            Toast.makeText(getApplicationContext(), "???????????? 30????????? ?????? ??? ????????????!", Toast.LENGTH_SHORT).show();
        else if ((emailSplitAt.length < 2) || (emailSplitAt.length > 2))
            Toast.makeText(getApplicationContext(), "'@'??? ????????? ?????? ??????????????????!", Toast.LENGTH_SHORT).show();
        else if ((emailSplitAt[0].length() < 1) || (emailSplitAt[1].length() < 1))
            Toast.makeText(getApplicationContext(), "@ ?????? ?????? ????????? ?????? ????????????!", Toast.LENGTH_SHORT).show();
        else if ((emailSplitAt2.length < 2) || (emailSplitDot[1].length() < 1))
            Toast.makeText(getApplicationContext(), "'.' ?????? ?????? ????????? ?????? ????????????!", Toast.LENGTH_SHORT).show();
        else if (!emailSplitDot[1].equals("com"))
            Toast.makeText(getApplicationContext(), "'.com'?????? ??????????????????!", Toast.LENGTH_SHORT).show();
        else if (CheckSpecialChar(emailText))
            Toast.makeText(getApplicationContext(), "'@'??? '.'??? ????????? ??????????????? ????????????!", Toast.LENGTH_SHORT).show();
        else {
            Toast.makeText(getApplicationContext(), "????????? ????????? ?????????????????????!", Toast.LENGTH_SHORT).show();
            emailCheckFlag = true;
        }
    }

    // ???????????? ??????????????? ??????????????? ???????????? ????????? => ????????? true, ????????? false
    public Boolean CheckSpecialChar(String emailText){
        String specialChar = "~!#$%^&*()_+`-={}|[]\\:\";'<>?,/ ";      // '@', '.'??? ????????? ???????????????

        for (int i=0; i<emailText.length(); i++){
            Log.i(tag, "CheckSpecialChar: " + emailText.charAt(i) + "  i: " +i);
            if (specialChar.contains(emailText.charAt(i)+""))
                return true;
        }
        return false;
    }

    public void onClickJoin(View v){

        if (!idOverlapFlag)
            Toast.makeText(getApplicationContext(), "????????? ?????? ?????? ??????????????? ?????????!", Toast.LENGTH_SHORT).show();
        else if (!pwCheckFlag)
            Toast.makeText(getApplicationContext(), "???????????? ?????? ??????????????? ?????????!", Toast.LENGTH_SHORT).show();
        else if (!nicknameOverlapFlag)
            Toast.makeText(getApplicationContext(), "????????? ?????? ?????? ??????????????? ?????????!", Toast.LENGTH_SHORT).show();
        else if (!phoneNumCheckFlag)
            Toast.makeText(getApplicationContext(), "???????????? ?????? ??????????????? ?????????!", Toast.LENGTH_SHORT).show();
        else if (!emailCheckFlag)
            Toast.makeText(getApplicationContext(), "????????? ?????? ??????????????? ?????????!", Toast.LENGTH_SHORT).show();
        else {
            JsonObj = new JSONObject();

            try {
                JsonObj.accumulate("id", id.getText());
                JsonObj.accumulate("pw", secretPassword);
                JsonObj.accumulate("name", name.getText());
                JsonObj.accumulate("phoneNumber", phoneNumber.getText());
                JsonObj.accumulate("email", email.getText());
                JsonObj.accumulate("nickname", nickname.getText());

                RequestingServer req = new RequestingServer(this, JsonObj);              // ?????? ?????? ??????

                String response = req.execute(joinURL).get();
                Log.i(tag, "result: " + response);

                if (response == null)
                    return;

                String[] resResult = response.split(",");
                Log.i(tag, "resResult[0]: " + resResult[0]);

                if (resResult[0].equals("Join success")){
                    Toast.makeText(getApplicationContext(), "???????????? ??????!", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                    Toast.makeText(getApplicationContext(), "???????????? ??????..", Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}