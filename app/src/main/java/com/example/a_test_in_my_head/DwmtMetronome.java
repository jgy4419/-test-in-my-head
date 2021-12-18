package com.example.a_test_in_my_head;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import java.util.Random;

public class DwmtMetronome {
    SoundPool soundPool;
    int soundID;
    int cnt;
    Random random;

    public DwmtMetronome(Context activity){
        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        soundID = soundPool.load(activity, R.raw.beep,0);
        cnt = 0;
        random = new Random();
    }

    public void setRandomCnt(){
//        cnt = random.nextInt(20) + 10;      10 ~ 30 사이의 숫자
        cnt = 10;
        if (cnt < 5)
            setRandomCnt();
        else
            Log.i("cnt", "random cnt: " + cnt);
    }

    public void play(){
        soundPool.play(soundID,1f,1f, 0, 0, 1f);
        cnt--;
    }

    public int getCnt(){ return cnt; }
    public void setCnt(int cnt){ this.cnt = cnt; }
}

