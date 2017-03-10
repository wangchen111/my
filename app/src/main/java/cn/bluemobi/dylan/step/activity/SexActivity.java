package cn.bluemobi.dylan.step.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kogitune.activity_transition.ActivityTransitionLauncher;

import java.util.List;

import cn.bluemobi.dylan.step.R;
import cn.bluemobi.dylan.step.step.pojo.BodyData;
import cn.bluemobi.dylan.step.step.utils.DbUtils;

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


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sex);
        initView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.female:
                sex = "female";
                save();
                break;
            case R.id.male:
                sex = "male";
                save();
                startActivity(new Intent(this, WeightActivity.class));
                break;
            case R.id.iv_left:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.next:
                break;
        }

    }

    private void initView() {
        iv_left = (ImageView) findViewById(R.id.iv_left);
        iv_left.setOnClickListener(this);
        layout_titlebar = (LinearLayout) findViewById(R.id.layout_titlebar);
        female = (ImageView) findViewById(R.id.female);
        female.setOnClickListener(this);
        layout_female = (LinearLayout) findViewById(R.id.layout_female);
        male = (ImageView) findViewById(R.id.male);
        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(SexActivity.this, WeightActivity.class);
                ActivityTransitionLauncher.with(SexActivity.this).from(v).launch(intent);
            }
        });
    }

    private void save() {
        if (DbUtils.getLiteOrm() == null) {
            DbUtils.createDb(this, "body");
        }
        List<BodyData> list = DbUtils.getQueryAll(BodyData.class);
        if (list.size() == 0 || list.isEmpty()) {
            BodyData data = new BodyData();
            data.setSex(sex);
            DbUtils.insert(data);
        } else if (list.size() == 1) {
            BodyData data = list.get(0);
            data.setSex(sex);
            DbUtils.update(data);
        }
    }
}
