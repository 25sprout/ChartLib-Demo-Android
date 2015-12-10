package com.awonown.chartsample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GTW on 2015/11/2.
 */
public class LineChartDemoActivity extends AppCompatActivity {
    LineChart mChart;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linechart);

        mChart = (LineChart) findViewById(R.id.chart);

        /* 使用 setData 的方法設定 LineData 物件 */
        mChart.setData(getLineData());

        // 圖表基本設定
        mChart.setDescription("");

        // 設定其他樣式
        setupXAxis();
        setupYAxis();
        setupLegend();
        setupViewPort();

        // 設定 maker view
        MyMakerView mv = new MyMakerView(this, R.layout.item_marker_view);
        mChart.setMarkerView(mv);

        mChart.invalidate();
        mChart.notifyDataSetChanged();
    }

    private void setupXAxis() {
        XAxis mXAxis = mChart.getXAxis();
        mXAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
        mXAxis.setGridColor(ColorTemplate.PASTEL_COLORS[0]);
        mXAxis.setAxisLineColor(ColorTemplate.PASTEL_COLORS[0]);
    }

    private void setupYAxis() {
        YAxis mLeftYAxis = mChart.getAxis(YAxis.AxisDependency.LEFT);
        YAxis mRightYAxis = mChart.getAxis(YAxis.AxisDependency.RIGHT);

        mLeftYAxis.setGridColor(ColorTemplate.PASTEL_COLORS[0]);
        mRightYAxis.setGridColor(ColorTemplate.PASTEL_COLORS[0]);
        mLeftYAxis.setAxisLineColor(ColorTemplate.PASTEL_COLORS[0]);
        mRightYAxis.setAxisLineColor(ColorTemplate.PASTEL_COLORS[0]);

        mLeftYAxis.setTextColor(ColorTemplate.PASTEL_COLORS[0]);
        mRightYAxis.setTextColor(ColorTemplate.PASTEL_COLORS[0]);

    }

    private void setupViewPort(){

    }

    private void setupLegend(){
        Legend lg = mChart.getLegend();
        lg.setXEntrySpace(20f);
    }

    /* 將 DataSet 資料整理好後，回傳 LineData */
    private LineData getLineData() {
        final int DATA_COUNT = 5;

        // LineDataSet(List<Entry> 資料點集合, String 類別名稱)
        LineDataSet dataSetA = new LineDataSet(getChartData(DATA_COUNT, 1), "Apple");
        LineDataSet dataSetB = new LineDataSet(getChartData(DATA_COUNT, 3), "Banana");

        // 設定 datasetA 的 data point 樣式
        dataSetA.setColor(ColorTemplate.JOYFUL_COLORS[0]);
        dataSetA.setCircleColor(ColorTemplate.JOYFUL_COLORS[0]);
        dataSetA.setDrawCircleHole(true);
        dataSetA.setCircleColorHole(Color.WHITE);
        dataSetA.setCircleSize(4f);
        dataSetA.setDrawCubic(true);

        // 設定 datasetB 的 data point 樣式
        dataSetB.setColor(ColorTemplate.JOYFUL_COLORS[1]);
        dataSetB.setCircleColor(ColorTemplate.JOYFUL_COLORS[1]);
        dataSetB.setDrawCircleHole(true);
        dataSetB.setCircleColorHole(Color.WHITE);
        dataSetB.setCircleSize(4f);
        dataSetB.setDrawCubic(true);

        List<LineDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSetA);
        dataSets.add(dataSetB);

        // LineData(List<String> Xvals座標標籤, List<Dataset> 資料集)
        LineData data = new LineData(getLabels(DATA_COUNT), dataSets);

        return data;

    }

    /* 取得 List<Entry> 的資料給 DataSet */
    private List<Entry> getChartData(int count, int ratio) {

        List<Entry> chartData = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            int _ratio = (i % 2 == 0) ? ratio : -ratio;

            // Entry(value 數值, index 位置)
            chartData.add(new Entry(i * _ratio, i));
        }
        return chartData;
    }

    /* 取得 XVals Labels 給 LineData */
    private List<String> getLabels(int count) {

        List<String> chartLabels = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            chartLabels.add("X" + i);
        }
        return chartLabels;
    }
}
