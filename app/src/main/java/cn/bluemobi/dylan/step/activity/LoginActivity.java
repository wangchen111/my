package cn.bluemobi.dylan.step.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.litesuits.orm.db.annotation.Ignore;

import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bluemobi.dylan.step.R;
import cn.bluemobi.dylan.step.constant.Constants;
import cn.bluemobi.dylan.step.step.config.Constant;

import static org.xutils.x.http;

/**
 * 创建日期：2017 02 13
 *
 * @author wangchen
 * @version 1.0
 *          文件名称：LoginActivity
 *          类说明：登录模块
 */

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private static final String LoginUrl = "http://www.iwechat.top/tp5wx/public/index.php/api/login";
//    private static final String LoginUrl = "http://www.iwechat.top/h5/mychat/index.php/Home/User/login";

    @BindView(R.id.input_mobile)
    EditText _mobileText;
    @BindView(R.id.input_password)
    EditText _passwordText;
    @BindView(R.id.btn_login)
    Button _loginButton;
    @BindView(R.id.link_signup)
    TextView _signupLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("登录");
        builder.setNegativeButton("确定", null);

        progressDialog.setMessage("请稍候...");
        progressDialog.show();
        RequestParams params = new RequestParams(LoginUrl);
        params.addBodyParameter("phone", _mobileText.getText().toString());
        params.addParameter("cipher", _passwordText.getText().toString());
        http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    Log.e("response", result);
                    JSONObject object = null;
                    object = new JSONObject(result);
                    String status = object.getString("status");
                    String msg = object.getString("msg");
                    String info = object.getString("info");
                    JSONObject Info = new JSONObject(info);
                    Constants.sex = Info.getString("sex");
                    Constants.height = Info.getInt("height");
                    Constants.weight = Info.getInt("weight");
                    Constants.birthday = Info.getString("birthday");
                    if (status.equals("1")) {
                        progressDialog.cancel();
                        builder.setMessage(msg);
                        builder.show();
                    } else{
                        progressDialog.cancel();
                        builder.setMessage(msg);
                        builder.show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        finish();
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "请检查输入是否符合规则", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

}
