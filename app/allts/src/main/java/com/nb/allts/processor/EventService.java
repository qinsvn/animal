package com.nb.allts.processor;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;

import com.nb.allts.bean.Message;
import com.nb.allts.constant.MgsConstant;
import com.nb.allts.data.SharedPreferencesData;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class EventService extends AccessibilityService {

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

    }

    @Override
    public void onInterrupt() {

    }

    @Override
    protected boolean onKeyEvent(KeyEvent event) {
        if (event.getAction() == 0) {
            int key = event.getKeyCode();
            String project = SharedPreferencesData.getString(MgsConstant.project);
            String[] projects = project.split(MgsConstant.projectSplit);
            String keyCode = "";
            if (projects.length > 1) {
                keyCode = projects[1];
            }
            if (keyCode.equals(String.valueOf(key))) {
                Message msg = new Message();
                msg.setCode(MgsConstant.code_success);
                msg.setType(MgsConstant.type_0);
                MsgProcessor.msgProcessor.putMessage(msg);
            }

            //前端显示按键值
            String show = "";
            if (projects.length > 2) {
                show = projects[2];
            }
            if (MgsConstant.showCode.equals(show)) {
                Message msg = new Message();
                msg.setCode(MgsConstant.code_success);
                msg.setType(MgsConstant.type_6);
                msg.setData(key);
                MsgProcessor.msgProcessor.putMessage(msg);
            }
        }
        return super.onKeyEvent(event);
    }
}
