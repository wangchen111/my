package cn.bluemobi.dylan.step.step.utils;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by YZZHI on 2016/12/2.
 */

public class ConnectionsUtils {

    private static Handler mDelivery;
    private static final long DEFAULT_TIMEOUT = 70;
    private static final long DEFAULT_READ_TIMEOUT = 35;
    private static final long DEFAULT_WRITE_TIMEOUT = 35;
    //    private static String KEY_STORE_CLIENT_PATH = "mycert.p12";
    private static String KEY_STORE_PASSWORD;
    private static OkHttpClient httpClient = null;
    private static OkHttpClient.Builder builder;
    private static InputStream fis;

    public static Handler getInstance() {
        mDelivery = new Handler(Looper.getMainLooper());
        return mDelivery;
    }

    public static void initAppID() {
        builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(DEFAULT_WRITE_TIMEOUT, TimeUnit.SECONDS);
        httpClient = builder.build();
    }


    public static void request(String method, String json,
                               String url, YsCallback ysCallback) {
        Log.e("httpRequest--", url + "---" + json);
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        if (method.toUpperCase().equals("POST")) {
            RequestBody body = RequestBody.create(JSON,json);
            Request.Builder builder = new Request.Builder().url(url).post(body);
            Request request = builder.build();
            execute(url, request, ysCallback);
        } else if (method.toUpperCase().equals("GET")) {
            Request request = new Request.Builder().url(url).build();
            execute(url, request, ysCallback);
        }
    }

    private static void execute(final String servcePath, final Request request, final YsCallback ysCallback) {
        if (httpClient == null) {
            httpClient = builder.build();
        }
        //HBLogUtil.e("httpClient", httpClient.toString());
        //okhttp的异步通信方法enqueue
        httpClient.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                //调用发送失败的方法
                sendFailResultCallback(request, e, ysCallback);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() >= 400 && response.code() <= 599) {
                    try {
                        //发送失败的方法
                        sendFailResultCallback(request, new RuntimeException(response.body().string()), ysCallback);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }
                try {

                    String body = response.body().string();
                    Log.e("httpResponse--", servcePath  + "---body" + body);
                    //发送成功的方法
                    sendSuccessResultCallback(body, ysCallback);
                } catch (Exception e) {
                    e.printStackTrace();
                    sendFailResultCallback(response.request(), e, ysCallback);
                }
            }
        });
    }

    //发送失败结果
    private static void sendFailResultCallback(final Request request, final Exception e, final YsCallback ysCallback) {
        if (ysCallback == null) return;
        //Handler的对象mDelivery，从非UI线程传递消息回UI线程
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                ysCallback.onFailure(request, e);
            }
        });
    }

    //发送成功结果
    private static void sendSuccessResultCallback(final String body, final YsCallback ysCallback) {
        if (ysCallback == null) return;
        //Handler的对象mDelivery，从非UI线程传递消息回UI线程
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                ysCallback.onResponse(body);
            }
        });
    }


    public static boolean isNull(String msg) {
        if (msg == null || TextUtils.isEmpty(msg)) {
            return true;
        }
        return false;
    }

    private static Map returnError(Map resultMap, String errorMsg) {
        resultMap.put("returnCode", "999999");
        resultMap.put("returnMsg", errorMsg + "为空");
        resultMap.put("errorCode", "999999");
        resultMap.put("errorMsg", errorMsg + "为空");
        return resultMap;
    }

    public static boolean isInternet(Activity activity) {
        ConnectivityManager con = (ConnectivityManager) activity.getSystemService(Activity.CONNECTIVITY_SERVICE);
        boolean wifi = con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
        boolean internet = con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        return wifi | internet;
    }

    //产品接口请求头更改
    private static String httpUrl = "";

    private static Boolean distinguishUrl(String servicePath) {
        Log.i("ttttttt", servicePath);

        if (servicePath.contains("/sdk/product/getProductList")) {
            return true;
        } else if (servicePath.contains("/sdk/product/getHoldingProduct")) {
            return true;
        } else if (servicePath.contains("/api/fingerprintAcq_Android")) {
            Log.i("ttttttt", "application/json");
            return true;
        } else if (servicePath.contains("//api/HbOpenIIAcct")) {
            Log.i("ttttttt", "application/json");
            return true;
        } else if (servicePath.contains("/api/HbOpenCustomer")) {
            return true;
        } else if (servicePath.contains("/api/auth4BankCard")) {
            Log.i("ttttttt", "application/json");
            return true;
        } else if (servicePath.contains("/api/auth3BankCard")) {
            return true;
        } else if (servicePath.contains("/devportal/ws/createSign/getRsaSign")) {
            Log.i("hello", "/devportal/ws/createSign/getRsaSign");
            return true;
        }
        return false;
    }
}
