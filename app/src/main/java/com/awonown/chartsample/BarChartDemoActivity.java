package com.awonown.chartsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GTW on 2015/11/2.
 */
public class BarChartDemoActivity extends AppCompatActivity {
    BarChart mChart;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barchart);

        mChart = (BarChart) findViewById(R.id.chart);

        /* 使用 setData 的方法設定 LineData 物件 */
        mChart.setData(getBarData());

        // 圖表基本設定
        mChart.setDescription("");
        mChart.setBorderColor(ColorTemplate.PASTEL_COLORS[0]);
        mChart.setDrawGridBackground(false);


        // 設定其他樣式
        setupXAxis();
        setupYAxis();
        setupLegend();
        setupAnimation();

        // 設定 maker view
                MyMakerView mv = new MyMakerView(this, R.layout.item_marker_view);
        mChart.setMarkerView(mv);

        mChart.invalidate();
        mChart.notifyDataSetChanged();
    }

    private void setupXAxis() {
        XAxis mXAxis = mChart.getXAxis();
        mXAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

    }

    private void setupYAxis() {
        YAxis mLeftYAxis = mChart.getAxis(YAxis.AxisDependency.LEFT);
        YAxis mRightYAxis = mChart.getAxis(YAxis.AxisDependency.RIGHT);

        mLeftYAxis.setShowOnlyMinMax(true);
        mRightYAxis.setShowOnlyMinMax(true);

    }

    private void setupAnimation(){
        // 設定動畫
        mChart.animateY(2000, Easing.EasingOption.Linear);
        mChart.animateX(2000, Easing.EasingOption.Linear);
    }

    private void setupLegend(){
        Legend lg = mChart.getLegend();
        lg.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        lg.setXEntrySpace(20f);
    }

    /* 將 DataSet 資料整理好後，回傳 LineData */
    private BarData getBarData() {
        final int DATA_COUNT = 5;

        // LineDataSet(List<Entry> 資料點集合, String 類別名稱)
        BarDataSet dataSetA = new BarDataSet(getChartData(DATA_COUNT, 1), "Apple");
        BarDataSet dataSetB = new BarDataSet(getChartData(DATA_COUNT, 2), "Banana");
        BarDataSet dataSetC = new BarDataSet(getChartData(DATA_COUNT, 3), "CCLemon");

        // 設定 dataset 的顏色
        dataSetA.setColor(ColorTemplate.JOYFUL_COLORS[0]);
        dataSetB.setColor(ColorTemplate.JOYFUL_COLORS[1]);
        dataSetC.setColor(ColorTemplate.JOYFUL_COLORS[3]);

        // 設定 valueFormatter
        dataSetA.setValueFormatter(new MyValueFormatter());
        dataSetB.setValueFormatter(new MyValueFormatter());
        dataSetC.setValueFormatter(new MyValueFormatter());

        List<BarDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSetA);
        dataSets.add(dataSetB);
        dataSets.add(dataSetC);


        // LineData(List<String> Xvals座標標籤, List<Dataset> 資料集)
        BarData data = new BarData(getLabels(DATA_COUNT), dataSets);

        return data;

    }

    /* 取得 List<Entry> 的資料給 DataSet */
    private List<BarEntry> getChartData(int count, int ratio) {

        List<BarEntry> chartData = new ArrayList<>();
        for (int i = 0; i < count; i++) {

            // Entry(value 數值, index 位置)
            chartData.add(new BarEntry((i + 1) * ratio, i));
        }

        return chartData;
    }

    /* 取得 XVals Labels 給 LineData */
    private List<String> getLabels(int count) {

        List<String> chartLabels = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            chartLabels.add("S" + i);
        }

        return chartLabels;
    }
}
