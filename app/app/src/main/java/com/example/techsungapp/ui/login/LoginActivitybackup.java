package com.example.techsungapp.ui.login;

import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import com.example.techsungapp.R;
import com.senter.function.openapi.unstable.StLf;
import com.senter.function.openapi.unstable.StLf.TransceiverModel;
import com.senter.support.openapi.demo.lf.util.HttpConnection;
import com.senter.support.openapi.demo.lf.util.HttpMethod;
import com.senter.support.openapi.demo.lf.util.ModelUtil;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LoginActivitybackup extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private Switch workstatus;
    private Spinner workmodel;
    private EditText username;
    private TextView logname;
    private Button claername;
    private static String url= "http://192.168.1.102:8080/breed/api/culture";
    StringBuilder sb=new StringBuilder();
    StLf.TransceiverModelB2 modelB2=StLf.getInstanceAs(TransceiverModel.TransceiverModelB2).getTransceiverAs(StLf.TransceiverModelB2.class);
    SimpleDateFormat dateFormat = new SimpleDateFormat("HH点mm分ss秒");

    @Override
    public void onCreate(Bundle savedInstanceState) {

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
                        Map<String,String> param = new HashMap<String,String>();
                        param.put("code",String.valueOf(ti.uid()));
                        System.out.println(workmodel.getSelectedItem().toString());
                        param.put("workmodel", ModelUtil.map.get(workmodel.getSelectedItem().toString()));
                        param.put("username",username.getText().toString());
                        toServer(param);
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
    protected  void playOk(){
        SoundPool soundPool = new SoundPool.Builder().build();
        final int soundID = soundPool.load(getApplication(), R.raw.v, 1);
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int i, int i2) {
                soundPool.play(soundID,  //声音id
                        1, //左声道
                        1, //右声道
                        1, //优先级
                        0, // 0表示不循环，-1表示循环播放
                        1);//播放比率，0.5~2，一般为1
            }
        });
    }

    /**
     * 错误音效
     */
    protected  void playError(){
        SoundPool soundPool = new SoundPool.Builder().build();
        final int soundID = soundPool.load(getApplication(), R.raw.f, 1);
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int i, int i2) {
                soundPool.play(soundID,  //声音id
                        1, //左声道
                        1, //右声道
                        1, //优先级
                        0, // 0表示不循环，-1表示循环播放
                        1);//播放比率，0.5~2，一般为1
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
                        playOk();
                    }else{
                        sb.insert(0,sim+":采集数据失败[ "+param.get("code")+" ]"+json.get("msg")+"\r\n\r\n");
                        logname.setText(sb.toString());
                        playError();
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
            }
        }, param);
    }

    protected  void play(){

    }
}
