package cn.bluemobi.dylan.step.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.bluemobi.dylan.step.R;

/**
 * Created by wangchen on 2017/3/3.
 */

public class PersonActivity extends Activity implements View.OnClickListener{
    private ImageView iv_left;
    private LinearLayout layout_titlebar;
    private ImageView date;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) and run LayoutCreator again
        setContentView(R.layout.activity_person);
        initView();
    }

    private void initView() {
        iv_left = (ImageView)findViewById(R.id.iv_left);
        iv_left.setOnClickListener(this);
        layout_titlebar = (LinearLayout)findViewById(R.id.layout_titlebar);
        date = (ImageView)findViewById(R.id.date);
        tv_date = (TextView)findViewById(R.id.tv_date);
        tv_input_date = (TextView)findViewById(R.id.tv_input_date);
        cup = (ImageView)findViewById(R.id.cup);
        tv_cup = (TextView)findViewById(R.id.tv_cup);
        tv_input_cup = (TextView)findViewById(R.id.tv_input_cup);
        tv_input_mail = (TextView)findViewById(R.id.tv_input_mail);
        zuji = (ImageView)findViewById(R.id.zuji);
        tv_zuji = (TextView)findViewById(R.id.tv_zuji);
        tv_input_zuji = (TextView)findViewById(R.id.tv_input_zuji);
        medal = (TextView)findViewById(R.id.medal);
        tv_input_medal = (TextView)findViewById(R.id.tv_input_medal);
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
