package cn.bluemobi.dylan.step.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.bluemobi.dylan.step.R;
import cn.bluemobi.dylan.step.step.pojo.BodyData;
import cn.bluemobi.dylan.step.step.pojo.StepData;
import cn.bluemobi.dylan.step.step.utils.DbUtils;
import cn.bluemobi.dylan.step.step.utils.mathUtil;

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
    private TextView begin;
    private TextView textView;
    /*========== 数据相关 ==========*/
    int height;
    int weight;
    String birthday;
    String sex;
    private TextView tv_cal;


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
        begin = (TextView) findViewById(R.id.begin);
        textView = (TextView) findViewById(R.id.textView);
        tv_cal = (TextView) findViewById(R.id.tv_cal);
    }

    private void initData() {
        if (DbUtils.getLiteOrm() == null) {
            DbUtils.createDb(this, "body");
        }
        List<BodyData> bodyDatas = DbUtils.getQueryAll(BodyData.class);
        if (bodyDatas.size() == 0 || bodyDatas.isEmpty()) {
            sex = "--";
            height = 0;
            weight = 0;
            birthday = "--";
        } else if (bodyDatas.size() == 1) {

            sex = bodyDatas.get(0).getSex();
            height = bodyDatas.get(0).getHeight();
            weight = bodyDatas.get(0).getWeight();
            birthday = bodyDatas.get(0).getBirthday();
        }
        tv_data.setText("身高:" + height + "cm " + "体重" + weight + "kg " + "性别" + sex + " " + "生日" + birthday);

        List<StepData> stepDatas = DbUtils.getQueryAll(StepData.class);
        if (stepDatas.size() != 0) {
            //获取第一次使用的日期
            begin.setText(stepDatas.get(0).getToday());
            int a = Integer.valueOf(stepDatas.get(0).getStep());
            int b = 0;
            //计算单日最大步数和历史总步数
            for (int i = 0; i < stepDatas.size(); i++) {
                if (a < Integer.valueOf(stepDatas.get(i).getStep())) {
                    a = Integer.valueOf(stepDatas.get(i).getStep());
                }
                b = b + Integer.valueOf(stepDatas.get(i).getStep());
            }
            tv_input_medal.setText(a + "步");
            tv_input_zuji.setText(b + "步");
            //计算累计消耗的热量
            tv_cal.setText(mathUtil.getCalories(b) + "千卡");
            //计算累计使用天数
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy/MM/dd");//输入日期的格式
            Date curDate = new Date(System.currentTimeMillis());//获取当前时间
            String endtime = simpleDateFormat.format(curDate);
            String begindate = stepDatas.get(0).getToday();
            tv_input_date.setText(mathUtil.getDay(begindate, endtime) + "天");
            //计算累计公里
            tv_input_mail.setText(mathUtil.getMails(b) + "公里");
        }
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
