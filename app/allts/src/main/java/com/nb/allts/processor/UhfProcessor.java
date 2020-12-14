package com.nb.allts.processor;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.rscja.deviceapi.RFIDWithUHF;

/**
 * 固件
 * @author bobby
 */
public class UhfProcessor {

    public RFIDWithUHF mReader;

    public static UhfProcessor uhf;

    //不允许通过构造函数创建
    private UhfProcessor() {
    }

    public static UhfProcessor getInstance() {
        if (uhf==null){
            uhf=new UhfProcessor();
        }
        return uhf;
    }

    public UhfProcessor init() {
        try {
            free();
            mReader = RFIDWithUHF.getInstance();
        } catch (Exception ex) {
            System.out.println( ex.getMessage());
        }

        if (mReader != null) {
           new AsyncTask<String, Integer, Boolean>(){
               @Override
               protected Boolean doInBackground(String... params) {
                   // TODO Auto-generated method stub
                   return mReader.init();
               }

               @Override
               protected void onPostExecute(Boolean result) {
                   super.onPostExecute(result);
                   if (!result) {
                       System.out.println( "init fail");
                   }else{
                       System.out.println( "init success");
                       //测试  定时读取标签号码
                   }
               }

               @Override
               protected void onPreExecute() {
                   // TODO Auto-generated method stub
                   super.onPreExecute();
                   System.out.println( "init ....");
               }
           }.execute();
        }
        return this;
    }


    public void free() {
        if (mReader != null) {
            mReader.free();
        }
    }



    /**
     * 读取标签 单步
     */
    public String readTag() {
        String strUII = mReader.inventorySingleTag();
        if (!TextUtils.isEmpty(strUII)) {
            String strEPC = mReader.convertUiiToEPC(strUII);
            System.out.println( "读取到数据："+strEPC);
            return strUII;
        } else {
            System.out.println( "读取数据失败");
            return null;
        }
    }


}

