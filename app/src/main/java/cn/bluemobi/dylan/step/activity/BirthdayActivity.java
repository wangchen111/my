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

import com.totcy.salelibrary.HorizontalScaleScrollView;

import java.util.List;

import cn.bluemobi.dylan.step.R;
import cn.bluemobi.dylan.step.constant.Constants;
import cn.bluemobi.dylan.step.step.pojo.BodyData;
import cn.bluemobi.dylan.step.step.utils.DbUtils;

/**
 * Created by wangchen on 2017/3/17.
 */

public class BirthdayActivity extends Activity implements View.OnClickListener, HorizontalScaleScrollView.OnScrollListener {
    private ImageView iv_left;
    private LinearLayout layout_titlebar;
    private ImageView male;
    private LinearLayout layout_female;
    private TextView tv_scale;
    private HorizontalScaleScrollView hsScale;
    private Button last;
    private Button next;
    private LinearLayout layout_rule;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birthday);
        initView();
    }

    private void initView() {
        iv_left = (ImageView) findViewById(R.id.iv_left);
        iv_left.setOnClickListener(this);
        layout_titlebar = (LinearLayout) findViewById(R.id.layout_titlebar);
        male = (ImageView) findViewById(R.id.male);
        layout_female = (LinearLayout) findViewById(R.id.layout_female);
        tv_scale = (TextView) findViewById(R.id.tv_scale);
        hsScale = (HorizontalScaleScrollView) findViewById(R.id.hsScale);
        hsScale.setScrollListener(this);
        last = (Button) findViewById(R.id.last);
        next = (Button) findViewById(R.id.next);
        layout_rule = (LinearLayout) findViewById(R.id.layout_rule);

        last.setOnClickListener(this);
        next.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.last:
                startActivity(new Intent(this, HeightActivity.class));
                break;
            case R.id.next:
                save();
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.iv_left:
                startActivity(new Intent(this, MainActivity.class));
        }
    }

    @Override
    public void onScaleScroll(float scale) {

    }

    @Override
    public void onScaleScroll(int scale) {
        tv_scale.setText(scale + "年");
    }
    private void save() {
        if (DbUtils.getLiteOrm() == null) {
            DbUtils.createDb(this, "body");
        }
        List<BodyData> list = DbUtils.getQueryAll(BodyData.class);
        if (list.size() == 0 || list.isEmpty()) {
            BodyData data = new BodyData();
            data.setSex(Constants.sex);
            data.setHeight(Constants.height);
            data.setWeight(Constants.weight);
            data.setBirthday(tv_scale.getText().toString());
            DbUtils.insert(data);
        } else if (list.size() == 1) {
            BodyData data = list.get(0);
            data.setSex(Constants.sex);
            data.setHeight(Constants.height);
            data.setWeight(Constants.weight);
            data.setBirthday(tv_scale.getText().toString());
            DbUtils.update(data);
        }
    }
}
