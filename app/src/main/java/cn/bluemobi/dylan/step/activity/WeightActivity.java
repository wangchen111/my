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

import com.kogitune.activity_transition.ActivityTransition;
import com.kogitune.activity_transition.ExitActivityTransition;
import com.totcy.salelibrary.HorizontalScaleScrollView;

import cn.bluemobi.dylan.step.R;
import cn.bluemobi.dylan.step.constant.Constants;

/**
 * Created by wangchen on 2017/3/9.
 */

public class WeightActivity extends Activity implements View.OnClickListener, HorizontalScaleScrollView.OnScrollListener {
    private ExitActivityTransition exitTransition;

    private LinearLayout layout_titlebar;
    private ImageView male;
    private ImageView female;
    private LinearLayout layout_female;
    private HorizontalScaleScrollView hsScale;
    private Button last;
    private Button next;
    private LinearLayout layout_rule;
    private TextView tv_scale;
    private TextView tv_weight;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight);
        ActivityTransition.with(getIntent()).to(findViewById(R.id.male)).start(savedInstanceState);
        ActivityTransition.with(getIntent()).to(findViewById(R.id.female)).start(savedInstanceState);
        initView();
    }

    private void initView() {
        layout_titlebar = (LinearLayout) findViewById(R.id.layout_titlebar);
        if (getIntent().getIntExtra("sex", -1) == 1) {
            findViewById(R.id.male).setVisibility(View.VISIBLE);
        } else if (getIntent().getIntExtra("sex", -1) == 0) {
            findViewById(R.id.female).setVisibility(View.VISIBLE);
        }
        male = (ImageView) findViewById(R.id.male);
        layout_female = (LinearLayout) findViewById(R.id.layout_female);
        hsScale = (HorizontalScaleScrollView) findViewById(R.id.hsScale);
        hsScale.setAlpha(0f);
        hsScale.setVisibility(View.VISIBLE);
        hsScale.animate().alpha(1f).setDuration(2500);
        last = (Button) findViewById(R.id.last);
        next = (Button) findViewById(R.id.next);
        layout_rule = (LinearLayout) findViewById(R.id.layout_rule);

        last.setOnClickListener(this);
        next.setOnClickListener(this);
        tv_scale = (TextView) findViewById(R.id.tv_scale);
        tv_scale.setAlpha(0f);
        tv_scale.setVisibility(View.VISIBLE);
        tv_scale.animate().alpha(1f).setDuration(2500);
        hsScale.setScrollListener(this);
        tv_weight = (TextView) findViewById(R.id.tv_weight);
        tv_weight.setAlpha(0f);
        tv_weight.setVisibility(View.VISIBLE);
        tv_weight.animate().alpha(1f).setDuration(2500);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.last:
                startActivity(new Intent(this, SexActivity.class));
                break;
            case R.id.next:
                Constants.weight = Integer.valueOf(tv_scale.getText().toString());
                startActivity(new Intent(this, HeightActivity.class));
                finish();
                break;
        }
    }

    @Override
    public void onScaleScroll(float scale) {

    }

    @Override
    public void onScaleScroll(int scale) {
        tv_scale.setText(scale+"");
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setTitle("确认退出设置个人资料吗？")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 点击“确认”后的操作
                        startActivity(new Intent(WeightActivity.this,MainActivity.class));
                        WeightActivity.this.finish();

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
