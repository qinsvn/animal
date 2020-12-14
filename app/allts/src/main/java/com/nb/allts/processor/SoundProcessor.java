package com.nb.allts.processor;

import android.media.AudioManager;
import android.media.SoundPool;

import com.nb.allts.MainActivity;
import com.nb.allts.R;

import java.util.HashMap;

public class SoundProcessor {

    HashMap<String, Integer> soundMap = new HashMap<String, Integer>();
    private SoundPool soundPool;
    private float volumnRatio;
    private AudioManager am;



    public static SoundProcessor soundProcessor;

    //不允许通过构造函数创建
    private SoundProcessor() {
    }

    public static SoundProcessor getInstance() {
        if (soundProcessor==null){
            soundProcessor=new SoundProcessor();
        }
        return soundProcessor;
    }


    public void init(){
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 5);
        soundMap.put("0", soundPool.load(MainActivity.m, R.raw.barcodebeep, 1));
        soundMap.put("-1", soundPool.load(MainActivity.m, R.raw.serror, 1));
        am = (AudioManager) MainActivity.m.getSystemService(MainActivity.m.AUDIO_SERVICE);// 实例化AudioManager对象
    }
    /**
     * 播放提示音
     *
     * @param id 成功0，失败-1
     */
    public void playSound(String id) {

        float audioMaxVolumn = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC); // 返回当前AudioManager对象的最大音量值
        float audioCurrentVolumn = am.getStreamVolume(AudioManager.STREAM_MUSIC);// 返回当前AudioManager对象的音量值
        volumnRatio = audioCurrentVolumn / audioMaxVolumn;
        try {
            soundPool.play(soundMap.get(id), volumnRatio, // 左声道音量
                    volumnRatio, // 右声道音量
                    1, // 优先级，0为最低
                    0, // 循环次数，0无不循环，-1无永远循环
                    1 // 回放速度 ，该值在0.5-2.0之间，1为正常速度
            );
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
