package cn.bluemobi.dylan.step.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kogitune.activity_transition.ActivityTransitionLauncher;

import cn.bluemobi.dylan.step.R;
import cn.bluemobi.dylan.step.constant.Constants;

/**
 * Created by wangchen on 2017/3/2.
 */

public class SexActivity extends Activity implements View.OnClickListener {

    private static String sex;
    private ImageView iv_left;
    private LinearLayout layout_titlebar;
    private ImageView female;
    private LinearLayout layout_female;
    private ImageView male;
    private Button next;
    private TextView tv_female;
    private TextView tv_male;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sex);
        initView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left:
                finish();
                break;
        }

    }

    private void initView() {
        iv_left = (ImageView) findViewById(R.id.iv_left);
        iv_left.setOnClickListener(this);
        layout_titlebar = (LinearLayout) findViewById(R.id.layout_titlebar);
        female = (ImageView) findViewById(R.id.female);
        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.sex = tv_female.getText().toString();
                final Intent intent = new Intent(SexActivity.this, WeightActivity.class);
                intent.putExtra("sex", 0);
                ActivityTransitionLauncher.with(SexActivity.this).from(v).launch(intent);
            }
        });
        layout_female = (LinearLayout) findViewById(R.id.layout_female);
        male = (ImageView) findViewById(R.id.male);
        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.sex = tv_male.getText().toString();
                final Intent intent = new Intent(SexActivity.this, WeightActivity.class);
                intent.putExtra("sex", 1);
                ActivityTransitionLauncher.with(SexActivity.this).from(v).launch(intent);
            }
        });
        tv_female = (TextView) findViewById(R.id.tv_female);
        tv_male = (TextView) findViewById(R.id.tv_male);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setTitle("确认退出设置个人资料吗？")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 点击“确认”后的操作
                        SexActivity.this.finish();

                    }
                })
                .setNegativeButton("返回", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 点击“返回”后的操作,这里不设置没有任何操作
                    }
                }).show();
    }
}
