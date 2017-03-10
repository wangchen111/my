package cn.bluemobi.dylan.step.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kogitune.activity_transition.ActivityTransition;
import com.kogitune.activity_transition.ExitActivityTransition;
import com.totcy.salelibrary.HorizontalScaleScrollView;

import cn.bluemobi.dylan.step.R;

/**
 * Created by wangchen on 2017/3/9.
 */

public class WeightActivity extends Activity implements View.OnClickListener, HorizontalScaleScrollView.OnScrollListener {
    private ExitActivityTransition exitTransition;

    private ImageView iv_left;
    private LinearLayout layout_titlebar;
    private ImageView male;
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
        initView();
    }

    private void initView() {
        iv_left = (ImageView) findViewById(R.id.iv_left);
        iv_left.setOnClickListener(this);
        layout_titlebar = (LinearLayout) findViewById(R.id.layout_titlebar);
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

                break;
            case R.id.iv_left:
                startActivity(new Intent(this,MainActivity.class));
        }
    }

    @Override
    public void onScaleScroll(float scale) {

    }

    @Override
    public void onScaleScroll(int scale) {
        tv_scale.setText(scale + "KG");
    }

}
