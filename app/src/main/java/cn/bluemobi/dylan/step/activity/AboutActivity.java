package cn.bluemobi.dylan.step.activity;

import android.app.Activity;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.bluemobi.dylan.step.R;

/**
 * Created by wangchen on 2017/2/21.
 * 关于模块
 */

public class AboutActivity extends Activity implements View.OnClickListener {
    private ImageView iv_left;
    private ImageView iv_right;
    private LinearLayout layout_titlebar;
    private ImageView logo;
    private ImageView yourstep;
    private TextView version;
    private LinearLayout line;
    private TextView auther;
    private TextView weibo;
    private TextView email;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        Transition fade = TransitionInflater.from(this).inflateTransition(R.transition.fade);
        //退出时使用
        getWindow().setExitTransition(fade);
        //第一次进入时使用
        getWindow().setEnterTransition(fade);
        setContentView(R.layout.activity_about);
        initView();
    }

    private void initView() {
        iv_left = (ImageView) findViewById(R.id.iv_left);
        iv_right = (ImageView) findViewById(R.id.iv_right);
        layout_titlebar = (LinearLayout) findViewById(R.id.layout_titlebar);
        logo = (ImageView) findViewById(R.id.logo);
        yourstep = (ImageView) findViewById(R.id.yourstep);
        version = (TextView) findViewById(R.id.version);
        line = (LinearLayout) findViewById(R.id.line);
        auther = (TextView) findViewById(R.id.auther);
        weibo = (TextView) findViewById(R.id.weibo);
        email = (TextView) findViewById(R.id.email);

        iv_left.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left:
                finish();
                break;
        }
    }
}
