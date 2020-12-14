package com.nb.allts;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.nb.allts.constant.MgsConstant;
import com.nb.allts.data.SharedPreferencesData;
import com.nb.allts.processor.EventService;
import com.nb.allts.processor.SoundProcessor;
import com.nb.allts.processor.UhfProcessor;
import com.nb.allts.processor.MsgProcessor;

import android.content.Context;
import static androidx.constraintlayout.widget.Constraints.TAG;

public class MainActivity extends Activity {
    public WebView webView;
    public WebSettings webSettings;

    public static MainActivity m;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //如果没开启，就提醒开启辅助功能
        if(!isAccessibilitySettingsOn(this)){
            Intent intent=new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS);
            startActivity(intent);
        }

        m = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = (WebView) findViewById(R.id.webView);

        //初始化固件
        UhfProcessor.getInstance().init();
        SoundProcessor.getInstance().init();
        //初始化消息处理器
        MsgProcessor.getInstance().init();

        webView.addJavascriptInterface(MsgProcessor.getInstance(), "androidJS");
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        String host = SharedPreferencesData.getString(MgsConstant.host);
        String project = SharedPreferencesData.getString(MgsConstant.project);
        String[] projects = project.split(MgsConstant.projectSplit);
        String projectName ="";
        if(projects.length>0){
            projectName =projects[0];
        }
        webView.loadUrl(String.format("http://%s/%s/index?from=mobile", host,projectName));
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                // 加载页面
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                // 断网或者网络连接超时
                if (errorCode == ERROR_HOST_LOOKUP || errorCode == ERROR_CONNECT || errorCode == ERROR_TIMEOUT) {
                    view.loadUrl("about:blank"); // 避免出现默认的错误界面
                    view.loadUrl("file:///android_asset/login.html");
                }
            }
            @TargetApi(android.os.Build.VERSION_CODES.M)
            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
                // 这个方法在6.0才出现
                int statusCode = errorResponse.getStatusCode();
                System.out.println("onReceivedHttpError code = " + statusCode);
                if (404 == statusCode) {
                    view.loadUrl("about:blank"); // 避免出现默认的错误界面
                    view.loadUrl("file:///android_asset/login.html");
                }
            }
        });


    }

    @Override
    protected void onDestroy() {
       UhfProcessor.getInstance().free();
       super.onDestroy();
    }

//    public boolean onKeyDown(int keyCode, KeyEvent event)  {
//        System.out.println(keyCode);
//        return super.onKeyDown(keyCode,event);
//    }

    //判断是否开启辅助功能
    private boolean isAccessibilitySettingsOn(Context mContext) {
        int accessibilityEnabled = 0;
        final String service = getPackageName() + "/" + EventService.class.getCanonicalName();
        try {
            accessibilityEnabled = Settings.Secure.getInt(
                    mContext.getApplicationContext().getContentResolver(),
                    android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
            Log.v(TAG, "accessibilityEnabled = " + accessibilityEnabled);
        } catch (Settings.SettingNotFoundException e) {
            Log.e(TAG, "Error finding setting, default accessibility to not found: "
                    + e.getMessage());
        }
        TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');

        if (accessibilityEnabled == 1) {
            Log.v(TAG, "***ACCESSIBILITY IS ENABLED*** -----------------");
            String settingValue = Settings.Secure.getString(
                    mContext.getApplicationContext().getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (settingValue != null) {
                mStringColonSplitter.setString(settingValue);
                while (mStringColonSplitter.hasNext()) {
                    String accessibilityService = mStringColonSplitter.next();

                    Log.v(TAG, "-------------- > accessibilityService :: " + accessibilityService + " " + service);
                    if (accessibilityService.equalsIgnoreCase(service)) {
                        Log.v(TAG, "We've found the correct setting - accessibility is switched on!");
                        return true;
                    }
                }
            }
        } else {
            Log.v(TAG, "***ACCESSIBILITY IS DISABLED***");
        }

        return false;
    }
}
