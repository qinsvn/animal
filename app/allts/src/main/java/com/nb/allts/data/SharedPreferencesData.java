package com.nb.allts.data;

import android.content.SharedPreferences;

import com.nb.allts.MainActivity;
import com.nb.allts.constant.MgsConstant;

public class SharedPreferencesData {
    private static String host_default = "106.52.176.83";
    private static String project_default = "allts#280#0";//项目名称  按键值  打印按键值0不打印
    private static SharedPreferences sharedPreferences;
    static{
        sharedPreferences = MainActivity.m.getSharedPreferences("allts-sharedPreferences", MainActivity.m.MODE_PRIVATE);
        String _host=sharedPreferences.getString(MgsConstant.host, null);
        SharedPreferences.Editor edit=sharedPreferences.edit();
        if(_host==null){
            edit.putString(MgsConstant.host, host_default);
            edit.commit();
        }
        String _project=sharedPreferences.getString(MgsConstant.project, null);
        if(_project==null){
            edit.putString(MgsConstant.project, project_default);
            edit.commit();
        }

    }

    public static String getString(String key){
        String val=sharedPreferences.getString(key, null);
        return val;
    }

    public static void setString(String key,String values){
        SharedPreferences.Editor edit=sharedPreferences.edit();
        edit.putString(key,values);
        edit.commit();
    }
}
