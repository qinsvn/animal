package com.example.techsungapp.ui.login;

import com.senter.function.openapi.unstable.StLf;
import com.senter.function.openapi.unstable.StLf.TransceiverModel;


import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.techsungapp.R;
import com.senter.support.openapi.demo.lf.util.HttpConnection;
import com.senter.support.openapi.demo.lf.util.HttpMethod;
import com.senter.support.openapi.demo.lf.util.ModelUtil;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import com.senter.support.openapi.StBarcodeScanner;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private Switch workstatus;
    private Spinner workmodel;
    private EditText username;
    private TextView logname;
    private Button claername;
    //dev
   // private static String url= "http://192.168.1.106:8080/breed/api/culture";
    //SIT
   // private static String url= "http://106.52.176.83/breed/api/culture";
    //PRO
   // private static String url= "http://120.77.178.175/breed/api/culture";
    private static String url= "http://106.53.50.166/breed/api/culture";


    static int okCount=0;
    static int errorCount=0;
    StringBuilder sb=new StringBuilder();
    StLf.TransceiverModelB2 modelB2=StLf.getInstanceAs(TransceiverModel.TransceiverModelB2).getTransceiverAs(StLf.TransceiverModelB2.class);
    SimpleDateFormat dateFormat = new SimpleDateFormat("HH点mm分ss秒");

    public static final String Tag = "MainActivity";
    AtomicBoolean isScanning = new AtomicBoolean(false);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        initPlayOk();
        initPlayError();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        workmodel =(Spinner)findViewById(R.id.workmodel);
        workstatus = (Switch) findViewById(R.id.workstatus);
        username =(EditText)findViewById(R.id.username);
        logname=(TextView)findViewById(R.id.logname);
        claername = (Button)findViewById(R.id.claername);

        workstatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    start();
                    username.setEnabled(false);
                    workmodel.setEnabled(false);
                } else {
                    stop();
                    username.setEnabled(true);
                    workmodel.setEnabled(true);
                }
            }
        });

        claername.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
               logname.setText("");
                sb = new StringBuilder();
            }
        });
    }

    /**
     * response to physical button
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case 235://ST327
            case 272://ST327A
            case 212://ST307
            case 221://ST308 ST309
            case 224://908
                scan();
                break;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }


    /**
     * 开始服务
     */
    protected void start()
    {
        stop();
        boolean initResult=modelB2.init();
        if (initResult==false) {
            //at.showToastShort("Cannot initialize the function,please check if some one function conflicting with LF is running");
            return;
        }
        modelB2.setTagListener(new StLf.TransceiverModelB2.TagInfoListener()
        {
            @Override
            public void onNewTag(final StLf.TransceiverModelB2.TagInfo ti)
            {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {;
                      String workmodelStr=  ModelUtil.map.get(workmodel.getSelectedItem().toString());
                    if(!("1002".equals(workmodelStr)||"1005".equals(workmodelStr)||"1007".equals(workmodelStr))){
                        Map<String,String> param = new HashMap<String,String>();
                        param.put("code",String.valueOf(ti.uid()));
                        System.out.println(workmodelStr);
                        param.put("workmodel",workmodelStr);
                        param.put("username",username.getText().toString());
                        toServer(param);
                    }
                    }
                });
            }
        });
        modelB2.trigInventoryOnce();
    }


    /**
     * 停止服务
     */
    protected void stop()
    {
        try {
            modelB2.stopInventory();
            modelB2.uninit();
        }catch (Exception e){

        }
    }

    /**
     * 正常音效
     */
    protected  void initPlayOk(){
        SoundPool soundPool = new SoundPool.Builder().build();
        final int soundID = soundPool.load(getApplication(), R.raw.v, 1);
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(final SoundPool soundPool, int i, int i2) {
                new Thread() {
                    @Override
                    public void run() {
                        while (true){
                            try {
                                Thread.sleep(1000);
                                if(okCount>0){

                                    soundPool.play(soundID,  //声音id
                                            1, //左声道
                                            1, //右声道
                                            1, //优先级
                                            0, // 0表示不循环，-1表示循环播放
                                            1);//播放比率，0.5~2，一般为1
                                    okCount--;
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }.start();

            }
        });
    }

    /**
     * 错误音效
     */
    protected  void initPlayError(){
        SoundPool soundPool = new SoundPool.Builder().build();
        final int soundID = soundPool.load(getApplication(), R.raw.f, 1);
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete( final SoundPool soundPool, int i, int i2) {
                new Thread(){
                    @Override
                    public void run() {
                        while (true){
                            try {
                                Thread.sleep(1000);
                                if(errorCount>0){
                                    soundPool.play(soundID,  //声音id
                                            1, //左声道
                                            1, //右声道
                                            1, //优先级
                                            0, // 0表示不循环，-1表示循环播放
                                            1);//播放比率，0.5~2，一般为1

                                    errorCount--;
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }.start();
            }
        });
    }

    /*
     *请求服务器
     */
    protected  void toServer(final Map<String,String> param){
        HttpConnection.http(url, HttpMethod.GET, new HttpConnection.SuccessCallback() {
            boolean ret = false;
            public void onSuccess(String result) {
                System.out.println("返回结果："+result);
                try {
                    JSONObject json = new JSONObject(result);
                    //编号
                    String code = (String) json.get("code");
                    Date date = new Date();
                    String sim = dateFormat.format(date);
                    if(code.equals("0")){
                        sb.insert(0,sim+":采集数据[ "+param.get("code")+" ]"+"\r\n\r\n");
                        logname.setText(sb.toString());
                        //playOk();
                        okCount++;
                    }else{
                        sb.insert(0,sim+":采集数据失败[ "+param.get("code")+" ]"+json.get("msg")+"\r\n\r\n");
                        logname.setText(sb.toString());
                       // playError();
                        errorCount++;
                    }
                }catch (Exception e){

                }
            }
        }, new HttpConnection.FailCallback() {
            @Override
            public void onFail() {
                Date date = new Date();
                String sim = dateFormat.format(date);
                sb.insert(0,sim+":连接服务器失败！\r\n\r\n");
                logname.setText(sb.toString());
                System.out.println("连接服务器失败！");
                errorCount++;
            }
        }, param);
    }

    protected  void play(){

    }

    private void scan() {
        new Thread() {
            public void run() {
                if (isScanning.compareAndSet(false, true) == false) {//at the same time only one thread can be allowed to scan
                    return;
                }

                try {
                    StBarcodeScanner scanner = StBarcodeScanner.getInstance();
                    if (scanner == null) {
                        Log.e(Tag, "!!!!!!!!!!!!sdk is to old to work，please update sdk");
                        return;
                    }
                    StBarcodeScanner.BarcodeInfo rslt = scanner.scanBarcodeInfo();//scan ,if failed,null will be return
                    if (rslt!=null){
                        String workmodelStr=  ModelUtil.map.get(workmodel.getSelectedItem().toString());
                        if("1002".equals(workmodelStr)||"1005".equals(workmodelStr)||"1007".equals(workmodelStr)){
                            Map<String,String> param = new HashMap<String,String>();
                            param.put("code",new String(rslt.getBarcodeValueAsBytes(),"utf-8"));
                            System.out.println(workmodel.getSelectedItem().toString());
                            param.put("workmodel", workmodelStr);
                            param.put("username",username.getText().toString());
                            toServer(param);
                        }
                    }
                    return;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }finally {
                    isScanning.set(false);
                }
            }
        }.start();
    }

}
