package cn.bluemobi.dylan.step.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.bluemobi.dylan.step.R;

/**
 * Created by wangchen on 2017/3/20.
 */

public class SuggestActivity extends Activity implements View.OnClickListener {
    private ImageView iv_left;
    private LinearLayout layout_titlebar;
    private TextView suggest;
    private Button submit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggest);
        initView();
    }

    private void initView() {
        iv_left = (ImageView) findViewById(R.id.iv_left);
        iv_left.setOnClickListener(this);
        layout_titlebar = (LinearLayout) findViewById(R.id.layout_titlebar);
        suggest = (TextView) findViewById(R.id.suggest);
        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit:
                final ProgressDialog dialog = ProgressDialog.show(this, "",
                        "正在提交，请稍等 …", true, true);
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(3000);//让他显示10秒后，取消ProgressDialog
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        dialog.dismiss();
                    }
                });
                t.start();
                break;
            case R.id.iv_left:
                finish();
                break;
        }
    }
}
