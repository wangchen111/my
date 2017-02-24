package cn.bluemobi.dylan.step.fragment;


import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;

import cn.bluemobi.dylan.step.R;
import cn.bluemobi.dylan.step.constant.Constants;
import path.recordpath3d.AnimationActivity;
import path.recordpath3d.PathActivity;

/**
 * Created by wangchen on 2017/2/15.
 */

public class LocationFragment extends Fragment implements View.OnClickListener {

    private TextView tv_data;
    private TextView textView5;
    private TextView textView8;
    private TextView textView7;
    private TextView tv_set;
    private TextView textView6;
    private TextView textView4;
    private TextView textView;
    private Button loginbutton;

    public static LocationFragment newInstance(String s) {
        LocationFragment homeFragment = new LocationFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ARGS, s);
        homeFragment.setArguments(bundle);
        return homeFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location2, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView(getView());

    }

    private void initView(View view) {
        tv_data = (TextView) view.findViewById(R.id.tv_data);
        textView8 = (TextView) view.findViewById(R.id.textView8);
        textView7 = (TextView) view.findViewById(R.id.textView7);
//        tv_set = (TextView) view.findViewById(R.id.tv_set);
        textView6 = (TextView) view.findViewById(R.id.textView6);
        textView4 = (TextView) view.findViewById(R.id.textView4);
        loginbutton = (Button) view.findViewById(R.id.loginbutton);

        loginbutton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loginbutton:
                Intent intent = new Intent(getActivity(), AnimationActivity.class);
                intent.setFlags(intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
        }

    }
}
