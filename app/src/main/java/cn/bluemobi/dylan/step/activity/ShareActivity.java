package cn.bluemobi.dylan.step.activity;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.bluemobi.dylan.step.R;

/**
 * Created by wangchen on 2017/3/20.
 */

public class ShareActivity extends Activity implements View.OnClickListener {
    private ImageView iv_left;
    private LinearLayout layout_titlebar;
    private TextView tv_git;
    private ImageView git;
    private Button gitbutton;
    private TextView tv_baidu;
    private ImageView baidu;
    private Button baiduButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        initView();
    }

    private void initView() {
        iv_left = (ImageView) findViewById(R.id.iv_left);
        iv_left.setOnClickListener(this);
        layout_titlebar = (LinearLayout) findViewById(R.id.layout_titlebar);
        tv_git = (TextView) findViewById(R.id.tv_git);
        git = (ImageView) findViewById(R.id.git);
        git.setOnClickListener(this);
        gitbutton = (Button) findViewById(R.id.gitbutton);
        gitbutton.setOnClickListener(this);
        tv_baidu = (TextView) findViewById(R.id.tv_baidu);
        tv_baidu.setOnClickListener(this);
        baidu = (ImageView) findViewById(R.id.baidu);
        baidu.setOnClickListener(this);
        baiduButton = (Button) findViewById(R.id.baiduButton);
        baiduButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.gitbutton:
                ClipboardManager clipboardManager = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                clipboardManager.setPrimaryClip(ClipData.newPlainText("data",tv_git.getText()));
                Toast.makeText(this, "复制成功，可以分享发给朋友们了。", Toast.LENGTH_LONG).show();
                break;
            case R.id.baiduButton:
                ClipboardManager cm = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setPrimaryClip(ClipData.newPlainText("data",tv_baidu.getText()));
                Toast.makeText(this, "复制成功，可以分享给朋友们了。", Toast.LENGTH_LONG).show();
                break;
            case R.id.iv_left:
                finish();
                break;
        }
    }
}
