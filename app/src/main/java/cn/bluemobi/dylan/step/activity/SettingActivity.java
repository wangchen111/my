package cn.bluemobi.dylan.step.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.bluemobi.dylan.step.R;
import cn.bluemobi.dylan.step.constant.Constants;
import cn.bluemobi.dylan.step.step.pojo.BodyData;
import cn.bluemobi.dylan.step.step.utils.DbUtils;

/**
 * Created by wangchen on 2017/3/31.
 */

public class SettingActivity extends Activity {
    public String logoutUrl = "http://www.iwechat.top/tp5wx/public/index.php/api/signOut";
    private ImageView iv_left;
    private LinearLayout layout_titlebar;
    private LinearLayout userid;
    private ImageView iv_edit;
    private LinearLayout edit;
    private Button logout;
    private TextView tv_userid;
    private View view1;
    private TextView tv_height;
    private View view2;
    private TextView tv_weight;
    private View view3;
    private TextView tv_bir;
    private LinearLayout bir;
    private View view4;
    private int height;
    private int weight;
    private String birthday;
    private String sex;
    private TextView tv_sex;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        //初始化控件
        initView();
        //初始化数据
        initData();
    }

    private void initView() {
        iv_left = (ImageView) findViewById(R.id.iv_left);
        iv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        layout_titlebar = (LinearLayout) findViewById(R.id.layout_titlebar);
        userid = (LinearLayout) findViewById(R.id.userid);
        iv_edit = (ImageView) findViewById(R.id.iv_edit);
        iv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, SexActivity.class));
            }
        });
        edit = (LinearLayout) findViewById(R.id.edit);
        logout = (Button) findViewById(R.id.logout);
        final ProgressDialog progressDialog = new ProgressDialog(SettingActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Constants.flag) {
                    Constants.flag = false;
                    Toast.makeText(getApplicationContext(), "退出成功", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "非登陆状态不可退出", Toast.LENGTH_LONG).show();
                }
            }
        });
        tv_userid = (TextView) findViewById(R.id.tv_userid);
        tv_height = (TextView) findViewById(R.id.tv_height);
        tv_weight = (TextView) findViewById(R.id.tv_weight);
        tv_bir = (TextView) findViewById(R.id.tv_bir);
        tv_sex = (TextView) findViewById(R.id.tv_sex);
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
        if (Constants.flag) {
            tv_userid.setText(Constants.id + "");
            if (Constants.sex.equals("1")) {
                tv_sex.setText("男");
            } else if (Constants.sex.equals("2")) {
                tv_sex.setText("女");
            } else {
                tv_sex.setText("--");
            }
            tv_height.setText(Constants.height + "cm");
            tv_weight.setText(Constants.weight + "kg");
            tv_bir.setText(Constants.birthday + "");
        } else {
            tv_userid.setText("00");
            if (sex.equals("1")) {
                tv_sex.setText("男");
            } else if (sex.equals("2")) {
                tv_sex.setText("女");
            } else {
                tv_sex.setText("--");
            }
            tv_height.setText(height + "cm");
            tv_weight.setText(weight + "kg");
            tv_bir.setText(birthday + "");
        }
    }
}

