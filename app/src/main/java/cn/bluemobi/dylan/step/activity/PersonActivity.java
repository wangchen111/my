package cn.bluemobi.dylan.step.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import cn.bluemobi.dylan.step.R;
import cn.bluemobi.dylan.step.step.pojo.BodyData;
import cn.bluemobi.dylan.step.step.utils.DbUtils;

/**
 * Created by wangchen on 2017/3/3.
 */

public class PersonActivity extends Activity implements View.OnClickListener {
    /*=======控件相关======*/
    private ImageView iv_left;
    private LinearLayout layout_titlebar;
    private ImageView date;
    private TextView tv_data;
    private TextView tv_date;
    private TextView tv_input_date;
    private ImageView cup;
    private TextView tv_cup;
    private TextView tv_input_cup;
    private TextView tv_input_mail;
    private ImageView zuji;
    private TextView tv_zuji;
    private TextView tv_input_zuji;
    private TextView medal;
    private TextView tv_input_medal;
    /*========== 数据相关 ==========*/
    String height;
    String weight;
    String birthday;
    String sex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) and run LayoutCreator again
        setContentView(R.layout.activity_person);
        initView();
        initData();
    }

    private void initView() {
        iv_left = (ImageView) findViewById(R.id.iv_left);
        iv_left.setOnClickListener(this);
        tv_data = (TextView) findViewById(R.id.tv_data);
        layout_titlebar = (LinearLayout) findViewById(R.id.layout_titlebar);
        date = (ImageView) findViewById(R.id.date);
        tv_date = (TextView) findViewById(R.id.tv_date);
        tv_input_date = (TextView) findViewById(R.id.tv_input_date);
        cup = (ImageView) findViewById(R.id.cup);
        tv_cup = (TextView) findViewById(R.id.tv_cup);
        tv_input_cup = (TextView) findViewById(R.id.tv_input_cup);
        tv_input_mail = (TextView) findViewById(R.id.tv_input_mail);
        zuji = (ImageView) findViewById(R.id.zuji);
        tv_zuji = (TextView) findViewById(R.id.tv_zuji);
        tv_input_zuji = (TextView) findViewById(R.id.tv_input_zuji);
        medal = (TextView) findViewById(R.id.medal);
        tv_input_medal = (TextView) findViewById(R.id.tv_input_medal);
    }

    private void initData() {
        if (DbUtils.getLiteOrm() == null) {
            DbUtils.createDb(this, "body");
        }
        List<BodyData> bodyDatas = DbUtils.getQueryAll(BodyData.class);
        sex = bodyDatas.get(0).getSex();
        height = bodyDatas.get(0).getHeight();
        weight = bodyDatas.get(0).getWeight();
        birthday = bodyDatas.get(0).getBirthday();
        tv_data.setText("身高:" + height + " " + "体重" + weight + " " + "性别"+sex+ "生日" + birthday);
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
