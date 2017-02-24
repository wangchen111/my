package cn.bluemobi.dylan.step.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bluemobi.dylan.step.R;
import cn.bluemobi.dylan.step.step.pojo.StepData;
import cn.bluemobi.dylan.step.step.utils.DbUtils;
import lecho.lib.hellocharts.listener.ColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.util.FloatUtils;
import lecho.lib.hellocharts.view.ColumnChartView;

/**
 * @author wangchen
 * @revision xiarui 2016.12.23
 * @description 柱状图 Column Chart 的使用
 */
public class HistoryActivity extends Activity implements View.OnClickListener{

    /*========== 控件相关 ==========*/
    private ColumnChartView mColumnCharView;                //柱形图控件
    private ImageView iv_left;
    private LinearLayout layout_titlebar;
    private LinearLayout allmail;
    private TextView allmail_tv;
    private LinearLayout allstep;
    private TextView allstep_tv;

    /*========== 状态相关 ==========*/
    private boolean isHasAxes = true;                       //是否显示坐标轴
    private boolean isHasAxesNames = true;                  //是否显示坐标轴
    private boolean isHasColumnLabels = false;              //是否显示列标签
    private boolean isColumnsHasSelected = true;           //设置列点击后效果(消失/显示标签)

    /*========== 标志位相关 ==========*/
    private static final int DEFAULT_DATA = 0;              //默认数据标志位
    private static final int SUBCOLUMNS_DATA = 1;           //多子列数据标志位
    private static final int NEGATIVE_SUBCOLUMNS_DATA = 2;  //反向多子列标志位
    private static final int STACKED_DATA = 3;              //堆放数据标志位
    private static final int NEGATIVE_STACKED_DATA = 4;     //反向堆放数据标志位
    private static boolean IS_NEGATIVE = false;             //是否需要反向标志位

    /*========== 数据相关 ==========*/
    String[] date;
    String[] score;
    private static  int numColumns;                    //列数
    private ColumnChartData mColumnChartData;               //柱状图数据
    private List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();

    public int getLayoutId() {
        return R.layout.activity_history;
    }

    public void initView() {
        mColumnCharView = (ColumnChartView) findViewById(R.id.ccv_main);
        layout_titlebar = (LinearLayout) findViewById(R.id.layout_titlebar);
        iv_left = (ImageView) findViewById(R.id.iv_left);
        allstep = (LinearLayout)findViewById(R.id.allstep);
        allmail = (LinearLayout)findViewById(R.id.allmail);
        allmail_tv = (TextView)findViewById(R.id.allmail_tv);
        allstep_tv = (TextView)findViewById(R.id.allstep_tv);
    }

    public void initData() {
        if (DbUtils.getLiteOrm() == null) {
            DbUtils.createDb(this, "jingzhi");
        }
        List<StepData> stepDatas = DbUtils.getQueryAll(StepData.class);
        date = new String[stepDatas.size()];
        score = new String[stepDatas.size()];
        for (int i = 0; i < stepDatas.size(); i++) {
            date[i] = stepDatas.get(i).getToday();
            score[i] = stepDatas.get(i).getStep();
        }
        numColumns = date.length;
        setColumnDatas(numColumns);        //根据状态设置相关数据
    }

    public void initListener() {
        //子列触摸监听
        mColumnCharView.setOnValueTouchListener(new ValueTouchListener());
        iv_left.setOnClickListener(this);
    }

    private void DataHandle() {
        int allstep = 0;//总步数
        double allmail = 0;//总里程
        for(int i=0;i<score.length;i++){
            allstep = allstep+Integer.parseInt(score[i]);
        }
        allstep_tv.setText(allstep+"步");
        allmail = allstep*0.6;
        allmail_tv.setText(allmail+"米");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //得到布局文件
        setContentView(getLayoutId());

        //初始化View
        initView();

        //初始化界面数据
        initData();

        //绑定监听器与适配器
        initListener();
        getAxisXLables();//获取x轴的标注
        //设置统计
        DataHandle();
    }

    /**
     * 根据不同的数据类型 绘制不同的柱状图
     */
    private void setColumnDatas(int numColumns) {
                IS_NEGATIVE = false;                                            //设置反向标志位：不反向
                setColumnDatasByParams(1, numColumns, false, IS_NEGATIVE);               //设置数据：单子列 总八列 不堆叠 不反向
        }
    /**
     * X 轴的显示
     */
    private void getAxisXLables() {
        for (int i = 0; i < date.length; i++) {
            mAxisXValues.add(new AxisValue(i).setLabel(date[i]));
        }
    }
    /**
     * 根据不同的参数 决定绘制什么样的柱状图
     *
     * @param numSubcolumns 子列数
     * @param numColumns    总列数
     * @param isStack       是否堆叠
     * @param isNegative    是否反向
     */
    private void setColumnDatasByParams(int numSubcolumns, int numColumns, boolean isStack, boolean isNegative) {
        List<Column> columns = new ArrayList<>();
        List<SubcolumnValue> values;
        //双重for循环给每个子列设置随机的值和随机的颜色
        for (int i = 0; i < numColumns; ++i) {
            values = new ArrayList<>();
            for (int j = 0; j < numSubcolumns; ++j) {
                //确定是否反向
                int negativeSign = getNegativeSign(isNegative);
                //根据反向值 设置列的值
                values.add(new SubcolumnValue(Float.parseFloat(score[i]), Color.parseColor("#7bbfea")));
            }

            /*===== 柱状图相关设置 =====*/
            Column column = new Column(values);
            column.setHasLabels(isHasColumnLabels);                    //没有标签
            column.setHasLabelsOnlyForSelected(isColumnsHasSelected);  //点击只放大
            columns.add(column);
        }
        mColumnChartData = new ColumnChartData(columns);               //设置数据
        mColumnChartData.setStacked(isStack);                          //设置是否堆叠
        /*===== 坐标轴相关设置 类似于Line Charts =====*/
        if (isHasAxes) {
            Axis axisX = new Axis();
            axisX.setValues(mAxisXValues);  //填充X轴的坐标名称
            Axis axisY = new Axis().setHasLines(true);
            if (isHasAxesNames) {
                axisX.setName("日期");
                axisY.setName("步数");
            }
            mColumnChartData.setAxisXBottom(axisX);
            mColumnChartData.setAxisYLeft(axisY);
        } else {
            mColumnChartData.setAxisXBottom(null);
            mColumnChartData.setAxisYLeft(null);
        }
        mColumnCharView.setColumnChartData(mColumnChartData);
    }

    /**
     * 根据是否反向标记返回对应的反向标志
     *
     * @param isNegative 是否反向
     * @return 反向标志 -1：反向 1：正向
     */
    private int getNegativeSign(boolean isNegative) {
        if (isNegative) {
            int[] sign = new int[]{-1, 1};                      //-1：反向 1：正向
            return sign[Math.round((float) Math.random())];     //随机确定子列正反
        }
        return 1;                                               //默认全为正向
    }

    /**
     * 显示或者隐藏节点标签
     */
    private void showOrHideColumnsLabels() {
        isHasColumnLabels = !isHasColumnLabels;     //取反即可
        setColumnDatas(numColumns);                           //重新设置
    }

    /**
     * 显示或者隐藏坐标轴
     */
    private void showOrHideAxes() {
        isHasAxes = !isHasAxes;                   //取反即可
        setColumnDatas(numColumns);                       //重新设置
    }

    /**
     * 显示或者隐藏坐标轴名称
     */
    private void showOrHideAxesName() {
        isHasAxesNames = !isHasAxesNames;         //取反即可
        setColumnDatas(numColumns);                          //重新设置
    }

    /**
     * 柱形图改变时的动画(需要判断当前是否是反向)
     *
     * @param isNegative 反向标记位
     */
    private void changeColumnsAnimate(boolean isNegative) {
        //增强for循环改变列数据
        for (Column column : mColumnChartData.getColumns()) {
            for (SubcolumnValue value : column.getValues()) {
                //根据当前反向标志位来重新绘制
                value.setTarget((float) Math.random() * 100 * getNegativeSign(isNegative));
            }
        }
        mColumnCharView.startDataAnimation();               //开始动画
    }

    /**
     * 点击显示列标签
     */
    private void showOrHideLablesByColumnsSelected() {
        isColumnsHasSelected = !isColumnsHasSelected;                     //取反即可
        mColumnCharView.setValueSelectionEnabled(isColumnsHasSelected);   //设置选中状态
        if (isColumnsHasSelected) {
            isHasColumnLabels = false;                                    //如果点击才显示标签 则标签开始不可见
        }
        setColumnDatas(numColumns);                                                 //重新设置
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left:
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * 子列触摸监听
     */
    private class ValueTouchListener implements ColumnChartOnValueSelectListener {

        @Override
        public void onValueSelected(int columnIndex, int subcolumnIndex, SubcolumnValue value) {
            Toast.makeText(HistoryActivity.this, "您大约走了 " + (int) value.getValue()+"步", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onValueDeselected() {

        }
    }
}
