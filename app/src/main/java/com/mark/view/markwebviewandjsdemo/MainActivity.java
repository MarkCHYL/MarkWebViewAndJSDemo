package com.mark.view.markwebviewandjsdemo;

import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements JSBridge {

    private static final String TAG = "MainActivity";
    private WebView wvshow;
    private TextView tvShow;
    private Handler mhandler;
    private EditText editText;
    private Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView(savedInstanceState);
        setContent();

        Log.d(TAG, "mainThreadvalue = " + Thread.currentThread());

    }

    private void setContent() {
        /*第一步*/
        //允许WebView家在JS代码
        wvshow.getSettings().setJavaScriptEnabled(true);
        /*第三部*/
        //给WebView添加js接口
        wvshow.addJavascriptInterface(new MarkJsInterface(this), "MarkLauncher");

        /*第四步*/
        /*加载h5页面*/
        wvshow.loadUrl("file:///android_asset/test.html");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //打开允许调试的开关
            wvshow.setWebContentsDebuggingEnabled(true);
        }

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = editText.getText().toString().trim();
                /**
                 * 传值到js页面，调用的是h5中的方法
                 *
                 */
                wvshow.loadUrl("javascript:if(window.remote){window.remote('" + str + "')}");
            }
        });

        mhandler = new Handler();
    }

    private void initView(Bundle savedInstanceState) {

        wvshow = findViewById(R.id.wv_show);
        tvShow = findViewById(R.id.tv_show);
        editText = findViewById(R.id.et_text);
        btnStart = findViewById(R.id.btn_start);

    }

    /**
     * JS调用安卓中的代码,传值到原生
     */
    @Override
    public void setTextViewValue(final String value) {
        mhandler.post(new Runnable() {
            @Override
            public void run() {
                tvShow.setText(value);
            }
        });
    }
}
