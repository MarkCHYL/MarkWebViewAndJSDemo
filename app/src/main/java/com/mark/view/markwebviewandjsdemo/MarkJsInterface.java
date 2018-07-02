package com.mark.view.markwebviewandjsdemo;


import android.util.Log;
import android.webkit.JavascriptInterface;

/**
 * 第二步
 * JS的接口类
 */
public class MarkJsInterface {
    public static final String TAG = "JsInterfaceDemo";
    private JSBridge jsBridge;

    public MarkJsInterface(JSBridge jsBridge) {
        this.jsBridge = jsBridge;
    }

    /**
     * 这个方法不在主线程运行
     * @param value
     */
    @JavascriptInterface
    public void setValue(String value){
        Log.d(TAG,"value = " + value);
        jsBridge.setTextViewValue(value);
    }

}
