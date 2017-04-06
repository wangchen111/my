package cn.bluemobi.dylan.step.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
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

import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bluemobi.dylan.step.R;
import cn.bluemobi.dylan.step.step.utils.CountDownTimer;

import static org.xutils.x.http;

/**
 * 创建日期：2017 02 13
 *
 * @author wangchen
 * @version 1.0
 *          文件名称：SignupActivity
 *          类说明：注册模块
 */
public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    private static String SignupUrl = "http://www.iwechat.top/tp5wx/public/index.php/api/register";
    //    private static String SignupUrl = "http://www.iwechat.top/h5/mychat/index.php/Home/User/reg";
    private static String getCodeUrl = "http://www.iwechat.top/tp5wx/public/index.php/api/sendRegisterSms";
//    private static String getCodeUrl = "http://www.iwechat.top/h5/mychat/index.php/Home/User/getRandom";

    private TimeCount time;

    @BindView(R.id.input_mobile)
    EditText _mobileText;
    @BindView(R.id.input_password)
    EditText _passwordText;
    @BindView(R.id.input_reEnterPassword)
    EditText _reEnterPasswordText;
    @BindView(R.id.input_identicode)
    EditText _identicodeText;
    @BindView(R.id.btn_signup)
    Button _signupButton;
    @BindView(R.id.link_login)
    TextView _loginLink;
    @BindView(R.id.btn_getcode)
    Button _getCodeButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        time = new TimeCount(60000, 1000);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("注册中...");

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("注册");

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validate()) {
                    onSignupFailed();
                    return;
                }
                progressDialog.setMessage("请稍候...");
                progressDialog.show();
                RequestParams params = new RequestParams(SignupUrl);
                params.addBodyParameter("phone", _mobileText.getText().toString());
                params.addParameter("cipher", _passwordText.getText().toString());
                params.addParameter("smsCode", _identicodeText.getText().toString());
//                params.addHeader("head","android"); //为当前请求添加一个头
                http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        try {
                            Log.e("response", result);
                            JSONObject object = null;
                            object = new JSONObject(result);
                            String status = object.getString("status");
                            String msg = object.getString("msg");
                            if (status.equals("1")) {
                                builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                                    }
                                });
                                progressDialog.cancel();
                                builder.setMessage(msg);
                                builder.show();
                            } else {
                                builder.setNegativeButton("确定",null);
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
        });
        _getCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("请稍候...");
                progressDialog.show();
                RequestParams params = new RequestParams(getCodeUrl);
                params.addParameter("phone", _mobileText.getText().toString());
                http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        try {
                            Log.e("response", result);
                            JSONObject object = null;
                            object = new JSONObject(result);
                            String status = object.getString("status");
                            String msg = object.getString("msg");
                            if(status.equals("1")) {
                                progressDialog.cancel();
                                builder.setMessage(msg);
                                builder.show();
                            }else {
                                progressDialog.cancel();
                                builder.setMessage(msg);
                                builder.show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    //请求异常后的回调方法
                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                    }

                    //主动调用取消请求的回调方法
                    @Override
                    public void onCancelled(CancelledException cex) {
                    }

                    @Override
                    public void onFinished() {
//                        progressDialog.cancel();
                    }
                });
                time.start();

            }
        });
        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "注册失败，请检查输入是否正确", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String mobile = _mobileText.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();

        if (mobile.isEmpty() || mobile.length() != 11) {
            _mobileText.setError("请输入合法的手机号");
            valid = false;
        } else {
            _mobileText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 18) {
            _passwordText.setError("请输入4到18位数字");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 18 || !(reEnterPassword.equals(password))) {
            _reEnterPasswordText.setError("两次密码不一致");
            valid = false;
        } else {
            _reEnterPasswordText.setError(null);
        }

        return valid;
    }

    /* 定义一个倒计时的内部类 */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {//计时完毕时触发
            _getCodeButton.setText("获取验证码");
            _getCodeButton.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示
            _getCodeButton.setClickable(false);
            _getCodeButton.setText(millisUntilFinished / 1000 + "秒后重新获取");
        }
    }
}