package cn.bluemobi.dylan.step.fragment;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.bluemobi.dylan.step.R;
import cn.bluemobi.dylan.step.activity.HistoryActivity;
import cn.bluemobi.dylan.step.constant.Constants;
import cn.bluemobi.dylan.step.step.config.Constant;
import cn.bluemobi.dylan.step.step.service.StepService;
import cn.bluemobi.dylan.step.step.utils.SharedPreferencesUtils;
import cn.bluemobi.dylan.step.step.utils.StepCountModeDispatcher;
import cn.bluemobi.dylan.step.step.utils.mathUtil;
import cn.bluemobi.dylan.step.view.StepArcView;

/**
 * 创建日期：2017/01/19
 *
 * @author wangchen
 * @version 1.0
 * 文件名称：HomeFragment
 * 类说明：主页fragment
 */

public class HomeFragment extends Fragment implements Handler.Callback, View.OnClickListener {
    private StepArcView cc;
    private TextView tv_isSupport;
    private TextView tv_data;
    private TextView tv_set;
    private TextView textView4;
    private TextView textView6;
    private Handler delayHandler;
    private SharedPreferencesUtils sp;
    private boolean isBind = false;
    private Messenger mGetReplyMessenger = new Messenger(new Handler(this));
    private Messenger messenger;
    /**
     * 用于查询应用服务（application Service）的状态的一种interface，
     * 更详细的信息可以参考Service 和 context.bindService()中的描述，
     * 和许多来自系统的回调方式一样，ServiceConnection的方法都是进程的主线程中调用的。
     */
    ServiceConnection conn = new ServiceConnection() {
        /**
         * 在建立起于Service的连接时会调用该方法，目前Android是通过IBind机制实现与服务的连接。
         * @param name 实际所连接到的Service组件名称
         * @param service 服务的通信信道的IBind，可以通过Service访问对应服务
         */
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            try {
                messenger = new Messenger(service);
                Message msg = Message.obtain(null, Constant.MSG_FROM_CLIENT);
                msg.replyTo = mGetReplyMessenger;
                messenger.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        /**
         * 当与Service之间的连接丢失的时候会调用该方法，
         * 这种情况经常发生在Service所在的进程崩溃或者被Kill的时候调用，
         * 此方法不会移除与Service的连接，当服务重新启动的时候仍然会调用 onServiceConnected()。
         * @param name 丢失连接的组件名称
         */
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    //定义心跳广播
    private BroadcastReceiver udpHeartReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //因为只有连接失败才会发送广播，所以 getStringExtra 一定有值，所以不需要去 getStringExtra == null去做判断
            int step = intent.getIntExtra("step", 0);
            String planWalk_QTY = (String) sp.getParam("planWalk_QTY", "7000");
            cc.setCurrentCount(Integer.parseInt(planWalk_QTY), step);
        }
    };

    public static HomeFragment newInstance(String s) {
        HomeFragment homeFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ARGS, s);
        homeFragment.setArguments(bundle);
        return homeFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
//        tv_data = (TextView) getView().findViewById(R.id.tv_data);
//        tv_data.setOnClickListener(this);
        textView4 = (TextView) getView().findViewById(R.id.textView4);
        textView6 = (TextView) getView().findViewById(R.id.textView6);
//        tv_set = (TextView) getView().findViewById(R.id.tv_set);
//        tv_set.setOnClickListener(this);
        cc = (StepArcView) getView().findViewById(R.id.cc);
        tv_isSupport = (TextView) getView().findViewById(R.id.tv_isSupport);
        IntentFilter intentFilter = new IntentFilter("com.set_step.activity");
        //注册广播
        getActivity().registerReceiver(udpHeartReceiver, intentFilter);
        sp = new SharedPreferencesUtils(getActivity());
        initData();

    }


    private void initData() {
        SharedPreferencesUtils sp = new SharedPreferencesUtils(getActivity());
        String planWalk_QTY = (String) sp.getParam("planWalk_QTY", "7000");
        cc.setCurrentCount(Integer.parseInt(planWalk_QTY), 1000);
        if (StepCountModeDispatcher.isSupportStepCountSensor(getActivity())) {
            tv_isSupport.setText("计步中...");
            delayHandler = new Handler(this);
            setupService();
        } else {
            tv_isSupport.setText("该设备不支持计步");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.tv_set:
//                startActivity(new Intent(getActivity(), SetPlanActivity.class));
//                break;
            case R.id.tv_data:
                startActivity(new Intent(getActivity(), HistoryActivity.class));
                break;
        }
    }

    /**
     * 开启计步服务
     */
    private void setupService() {
        Intent intent = new Intent(getActivity(), StepService.class);
        isBind = getActivity().bindService(intent, conn, Context.BIND_AUTO_CREATE);
        getActivity().startService(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isBind) {
            getActivity().unbindService(conn);
        }
        getActivity().unregisterReceiver(udpHeartReceiver);
    }

    /**
     * 从service服务中拿到步数
     *
     * @param msg
     * @return
     */
    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case Constant.MSG_FROM_SERVER:
                String planWalk_QTY = (String) sp.getParam("planWalk_QTY", "7000");
                cc.setCurrentCount(Integer.parseInt(planWalk_QTY), msg.getData().getInt("step"));
                textView4.setText(mathUtil.getMails(msg.getData().getInt("step")));
                Constants.mail = mathUtil.getMails(msg.getData().getInt("step"));
                textView6.setText(mathUtil.getCalories(msg.getData().getInt("step")));
                Constants.calories = mathUtil.getCalories(msg.getData().getInt("step"));
                break;
        }
        return false;
    }
}
