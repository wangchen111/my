package cn.bluemobi.dylan.step.step.utils;

import okhttp3.Request;

/**
 * Created by yzzhi on 2016/12/2.
 */
public interface YsCallback {
    void onFailure(Request request, Exception e);
    void onResponse(String response);
}
