package cn.bluemobi.dylan.step.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

    private ImageView iv_left;
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
            case R.id.next:
                Constants.height = tv_height.getText().toString();
        startActivity(new Intent(this,BirthdayActivity.class));
            case R.id.iv_left:
                finish();
        }
    }

    @Override
    public void onScaleScroll(int scale) {
        veScale.setOnScrollListener(new VerticalSrollScaleView.OnScrollListener() {
            @Override
            public void onScaleScroll(int scale) {
                tv_height.setText(scale + "cm");
            }
        });
    }

    private void initView() {
        iv_left = (ImageView) findViewById(R.id.iv_left);
        iv_left.setOnClickListener(this);
        layout_titlebar = (LinearLayout) findViewById(R.id.layout_titlebar);
        tv_height = (TextView) findViewById(R.id.tv_height);
        veScale = (VerticalSrollScaleView) findViewById(R.id.veScale);
        ll = (LinearLayout) findViewById(R.id.ll);
        last = (Button) findViewById(R.id.last);
        last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(HeightActivity.this, WeightActivity.class);
                ActivityTransitionLauncher.with(HeightActivity.this).from(v).launch(intent);
            }
        });
        male = (ImageView) findViewById(R.id.male);
        next = (Button) findViewById(R.id.next);
        next.setOnClickListener(this);
    }
}
