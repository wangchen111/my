package cn.bluemobi.dylan.step.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import java.util.ArrayList;
import java.util.List;

import cn.bluemobi.dylan.step.R;
import cn.bluemobi.dylan.step.fragment.HomeFragment;
import cn.bluemobi.dylan.step.fragment.LocationFragment;
import cn.bluemobi.dylan.step.step.utils.SharedPreferencesUtils;

/**
 * 创建日期：2016 12 20
 *
 * @author wangchen
 * @version 1.0
 *          文件名称：MainActivity
 *          类说明：
 */
public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener, View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    private SharedPreferencesUtils sp;
    private ArrayList<Fragment> fragments;

    private FrameLayout content_main;
    private ImageView iv4;
    private ImageView iv3;
    private ImageView iv2;
    private ImageView iv1;
    private List<ImageView> imageViews = new ArrayList<>();

    private final int radius1 = 400;

    private void assignViews() {
        content_main = (FrameLayout) findViewById(R.id.content_main);
        iv4 = (ImageView) findViewById(R.id.iv4);
        imageViews.add(iv4);
        iv3 = (ImageView) findViewById(R.id.iv3);
        imageViews.add(iv3);
        iv2 = (ImageView) findViewById(R.id.iv2);
        imageViews.add(iv2);
        iv1 = (ImageView) findViewById(R.id.iv1);
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        assignViews();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("yourstep");
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        toolbar.setNavigationIcon(R.drawable.menu);
        BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_SHIFTING);
        bottomNavigationBar
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC
                );
        bottomNavigationBar
                .setActiveColor(R.color.colorPrimary)
                .setInActiveColor("#FFFFF0")
                .setBarBackgroundColor("#FFFFF0");
        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.home_fill, getString(R.string.item_home)).setInactiveIconResource(R.drawable.home).setActiveColorResource(R.color.blue).setInActiveColorResource(R.color.black_1))
                .addItem(new BottomNavigationItem(R.drawable.walk_fill, getString(R.string.item_location)).setInactiveIconResource(R.drawable.mywalk).setActiveColorResource(R.color.blue).setInActiveColorResource(R.color.black_1))
                .setFirstSelectedPosition(0)
                .initialise();

        fragments = getFragments();
        setDefaultFragment();
        bottomNavigationBar.setTabSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * 设置默认的fragment
     */
    private void setDefaultFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(R.id.layFrame, HomeFragment.newInstance("Home"));
        transaction.commit();
    }

    private ArrayList<Fragment> getFragments() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(HomeFragment.newInstance("Home"));
        fragments.add(LocationFragment.newInstance("Location"));
        return fragments;
    }

    @Override
    public void onTabSelected(int position) {
        if (fragments != null) {
            if (position < fragments.size()) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment fragment = fragments.get(position);
                if (fragment.isAdded()) {
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    ft.replace(R.id.layFrame, fragment);
                } else {
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    ft.add(R.id.layFrame, fragment);
                }
                ft.commitAllowingStateLoss();
            }
        }
    }

    @Override
    public void onTabUnselected(int position) {
        if (fragments != null) {
            if (position < fragments.size()) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment fragment = fragments.get(position);
                ft.remove(fragment);
                ft.commitAllowingStateLoss();
            }
        }
    }

    @Override
    public void onTabReselected(int position) {

    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onClick(View v) {
        if (v.getId() == iv1.getId()) {
            Boolean isShowing = (Boolean) iv1.getTag();
            if (null == isShowing || isShowing == false) {
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(iv1, "rotation", 0, 225);
                objectAnimator.setDuration(500);
                objectAnimator.start();
                iv1.setTag(true);
                showSectorMenu();
            } else {
                iv1.setTag(false);
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(iv1, "rotation", 225, 0);
                objectAnimator.setDuration(500);
                objectAnimator.start();
                closeSectorMenu();
            }
        } else {
            if (v.getId() == iv2.getId()) {
                startActivity(new Intent(this, SetPlanActivity.class));
            } else if (v.getId() == iv3.getId()) {
                startActivity(new Intent(this, PersonActivity.class));
            } else if (v.getId() == iv4.getId()) {
                startActivity(new Intent(this, HistoryActivity.class));
            }
        }
    }

    /**
     * 显示扇形菜单
     */
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    private void showSectorMenu() {
        /***第一步，遍历所要展示的菜单ImageView*/
        for (int i = 0; i < imageViews.size(); i++) {
            PointF point = new PointF();
            /***第二步，根据菜单个数计算每个菜单之间的间隔角度*/
            int avgAngle = (90 / (imageViews.size() - 1));
            /**第三步，根据间隔角度计算出每个菜单相对于水平线起始位置的真实角度**/
            int angle = avgAngle * i;
            /**
             * ﻿﻿
             * 圆点坐标：(x0,y0)
             * 半径：r
             * 角度：a0
             * 则圆上任一点为：（x1,y1）
             * x1   =   x0   +   r   *   cos(ao   *   3.14   /180   )
             * y1   =   y0   +   r   *   sin(ao   *   3.14   /180   )
             */
            /**第四步，根据每个菜单真实角度计算其坐标值**/
            point.x = (float) Math.cos(angle * (Math.PI / 180)) * radius1;
            point.y = (float) -Math.sin(angle * (Math.PI / 180)) * radius1;

            /**第五步，根据坐标执行位移动画**/
            /**
             * 第一个参数代表要操作的对象
             * 第二个参数代表要操作的对象的属性
             * 第三个参数代表要操作的对象的属性的起始值
             * 第四个参数代表要操作的对象的属性的终止值
             */
            ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(imageViews.get(i), "translationX", 0, point.x);
            ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(imageViews.get(i), "translationY", 0, point.y);
            /**动画集合，用来编排动画**/
            AnimatorSet animatorSet = new AnimatorSet();
            /**设置动画时长**/
            animatorSet.setDuration(400);
            /**设置同时播放x方向的位移动画和y方向的位移动画**/
            animatorSet.play(objectAnimatorX).with(objectAnimatorY);
            /**开始播放动画**/
            animatorSet.start();
        }
    }


    /**
     * 关闭扇形菜单
     */
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    private void closeSectorMenu() {
        for (int i = 0; i < imageViews.size(); i++) {
            PointF point = new PointF();
            int avgAngle = (90 / (imageViews.size() - 1));
            int angle = avgAngle * i;
            point.x = (float) Math.cos(angle * (Math.PI / 180)) * radius1;
            point.y = (float) -Math.sin(angle * (Math.PI / 180)) * radius1;

            ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(imageViews.get(i), "translationX", point.x, 0);
            ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(imageViews.get(i), "translationY", point.y, 0);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setDuration(500);
            animatorSet.play(objectAnimatorX).with(objectAnimatorY);
            animatorSet.start();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_login) {
            startActivity(new Intent(this, LoginActivity.class));
        } else if (id == R.id.nac_person) {
            startActivity(new Intent(this, SexActivity.class));
        } else if (id == R.id.nav_setting) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.nav_about) {
            startActivity(new Intent(this, AboutActivity.class), ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
