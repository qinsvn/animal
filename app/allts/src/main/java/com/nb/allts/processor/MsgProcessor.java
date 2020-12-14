package com.nb.allts.processor;

import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nb.allts.MainActivity;
import com.nb.allts.bean.Message;
import com.nb.allts.constant.MgsConstant;
import com.nb.allts.data.SharedPreferencesData;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

public class MsgProcessor {

    public static MsgProcessor msgProcessor;

    //阻塞队列 存放消息
    private final static ArrayBlockingQueue<Message> messageQueue= new ArrayBlockingQueue<Message>(100,true);

    //不允许通过构造函数创建
    private MsgProcessor() {
    }

    public static MsgProcessor getInstance() {
        if (msgProcessor==null){
            msgProcessor = new MsgProcessor();
        }
        return msgProcessor;
    }

    //入队列
    public void putMessage(Message msg){
        try {
            messageQueue.put(msg);
            System.out.println("放入一个对象");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //出队列
    public Message takeMessage(){
        try {
            Message msg = messageQueue.take();
            System.out.println("放入一个对象");
           return msg;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Thread th;
    //初始化
    public void init(){
        if(th!=null){
            return;
        }
        //发送信息到  h5的线程
        th =  new Thread() {
            @Override
            public void run() {
                while (true){
                    final Message msg =  takeMessage();
                    if(MgsConstant.type_0.equals(msg.getType())){//读标签
                      String ridNun =  UhfProcessor.getInstance().readTag();
                      if (ridNun!=null){
                          msg.setCode(MgsConstant.code_success);
                      }else{
                          msg.setCode(MgsConstant.code_fail);
                      }
                      msg.setType(MgsConstant.type_1);
                      msg.setData(ridNun);
                      System.out.println("发送一个对象到H5");
                      MainActivity.m.webView.post(new Runnable() {
                            @Override
                            public void run() {
                                MainActivity.m.webView.evaluateJavascript("javascript:receiveAndroid('"+ JSON.toJSONString(msg)+"')",new ValueCallback<String>() {
                                    @Override
                                    public void onReceiveValue(String value) {

                                    }
                                });
                            }
                        });

                    }else if(MgsConstant.type_2.equals(msg.getType())){//播放声音
                        SoundProcessor.getInstance().playSound(""+msg.getData());
                    }else if(MgsConstant.type_3.equals(msg.getType())){//获取系统参数
                        Map data = new HashMap();
                        data.put(MgsConstant.host, SharedPreferencesData.getString(MgsConstant.host));
                        data.put(MgsConstant.project, SharedPreferencesData.getString(MgsConstant.project));
                        msg.setCode(MgsConstant.code_success);
                        msg.setType(MgsConstant.type_4);
                        msg.setData(data);
                        MainActivity.m.webView.post(new Runnable() {
                            @Override
                            public void run() {
                                MainActivity.m.webView.evaluateJavascript("javascript:receiveAndroid('"+ JSON.toJSONString(msg)+"')",new ValueCallback<String>() {
                                    @Override
                                    public void onReceiveValue(String value) {

                                    }
                                });
                            }
                        });
                    }else if(MgsConstant.type_5.equals(msg.getType())){//设置系统参数
                        Object obj = msg.getData();
                        JSONObject jsonObject = (JSONObject)obj;
                        String host =  jsonObject.getString(MgsConstant.host);
                        String project =  jsonObject.getString(MgsConstant.project);
                        SharedPreferencesData.setString(MgsConstant.host,host);
                        SharedPreferencesData.setString(MgsConstant.project,project);
                        Message msgObj = JSON.toJavaObject(jsonObject,Message.class);
                    }else if(MgsConstant.type_6.equals(msg.getType())) {//获取按键
                        System.out.println("发送一个对象到H5");
                        MainActivity.m.webView.post(new Runnable() {
                            @Override
                            public void run() {
                                MainActivity.m.webView.evaluateJavascript("javascript:receiveAndroid('"+ JSON.toJSONString(msg)+"')",new ValueCallback<String>() {
                                    @Override
                                    public void onReceiveValue(String value) {

                                    }
                                });
                            }
                        });
                    }
                }
            }

        };
        th.start();
    }

    /**
     * 接收来自H5的消息
     * @param msg
     */
    @JavascriptInterface
    public void action(String msg) {
        JSONObject jsonObject =  JSON.parseObject(msg);
        Message msgObj = JSON.toJavaObject(jsonObject,Message.class);
        putMessage(msgObj);
    }

}
