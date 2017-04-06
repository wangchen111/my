package cn.bluemobi.dylan.step.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kogitune.activity_transition.ActivityTransitionLauncher;
import com.totcy.salelibrary.VerticalSrollScaleView;

import cn.bluemobi.dylan.step.R;
import cn.bluemobi.dylan.step.constant.Constants;

/**
 * Created by wangchen on 2017/3/16.
 */

public class HeightActivity extends Activity implements View.OnClickListener, VerticalSrollScaleView.OnScrollListener {

    private LinearLayout layout_titlebar;
    private ImageView male;
    private VerticalSrollScaleView veScale;
    private TextView tv_height;
    private LinearLayout ll;
    private Button last;
    private Button next;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_height);
        initView();
        veScale.setOnScrollListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.last:
                startActivity(new Intent(this, WeightActivity.class));
                break;
            case R.id.next:
                Constants.height = Integer.valueOf(tv_height.getText().toString());
                startActivity(new Intent(this, BirthdayActivity.class));
                break;
        }
    }

    @Override
    public void onScaleScroll(int scale) {
        veScale.setOnScrollListener(new VerticalSrollScaleView.OnScrollListener() {
            @Override
            public void onScaleScroll(int scale) {
                tv_height.setText(scale + "");
            }
        });
    }

    private void initView() {
        if (Constants.sex.equals("男")) {
            findViewById(R.id.male).setVisibility(View.VISIBLE);
        } else if (Constants.sex.equals("女")) {
            findViewById(R.id.female).setVisibility(View.VISIBLE);
        }
        layout_titlebar = (LinearLayout) findViewById(R.id.layout_titlebar);
        tv_height = (TextView) findViewById(R.id.tv_height);
        veScale = (VerticalSrollScaleView) findViewById(R.id.veScale);
        ll = (LinearLayout) findViewById(R.id.ll);
        last = (Button) findViewById(R.id.last);
        last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(HeightActivity.this, WeightActivity.class);
                if (Constants.sex.equals("男")) {
                    intent.putExtra("sex", 1);
                } else if (Constants.sex.equals("女")) {
                    intent.putExtra("sex", 0);
                }
                ActivityTransitionLauncher.with(HeightActivity.this).from(v).launch(intent);
            }
        });
        male = (ImageView) findViewById(R.id.male);
        next = (Button) findViewById(R.id.next);
        next.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setTitle("确认退出设置个人资料吗？")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 点击“确认”后的操作
                        startActivity(new Intent(HeightActivity.this, MainActivity.class));
                        HeightActivity.this.finish();
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
