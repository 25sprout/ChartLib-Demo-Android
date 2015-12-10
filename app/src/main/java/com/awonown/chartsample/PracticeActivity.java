package com.awonown.chartsample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.List;


public class PracticeActivity extends AppCompatActivity {
    LineChart chart;
    LineDataSet dataSetA, dataSetB, dataSetC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linechart);

        setupChartBasic();
//        chart.setHardwareAccelerationEnabled(true);
        chart.setData(getLineData());
        setupChartStyle();
        setupViewPort();
        setupLegend();
//        chart.setEnabled(false);
        chart.setDrawGridBackground(false);

        // set 點擊時的 pop up view
        MyMakerView mv = new MyMakerView(this, R.layout.item_marker_view);
        // set the marker to the chart
        chart.setMarkerView(mv);

        setupAnimation();

        ViewPortHandler handler = chart.getViewPortHandler();


    }

    private void setupChartBasic(){
        chart = (LineChart) findViewById(R.id.chart);

        chart.invalidate(); // refresh view (redraw)
        chart.notifyDataSetChanged(); // refresh data
        chart.setLogEnabled(true); //可以將 chart 的 logcat 印出來, 但非必要時不要開, 對效能有影響
    }

    private void setupChartStyle(){
        // Background
        chart.setBackgroundColor(getResources().getColor(android.R.color.white));
        chart.setDrawGridBackground(false); //沒影響
//        chart.setGridBackgroundColor(getResources().getColor(R.color.second_accent)); //沒影響

        // Description
        chart.setDescription("這是神作 <3");
        chart.setDescriptionColor(getResources().getColor(R.color.second_accent));
//        chart.setDescriptionPosition(float x, float y);
//        chart.setDescriptionTypeface(Typeface t);
        chart.setDescriptionTextSize(20); //float size

        chart.setNoDataTextDescription("這裡沒有人");

        // 圖表外框 Border
        chart.setDrawBorders(true);
        chart.setBorderColor(getResources().getColor(R.color.yellow));
        chart.setBorderWidth(5);

        setupAxisStyle();
        setupYAxisStyle();
    }

    private void setupAxisStyle(){
        //XAxis 是影響上到下的線，x軸的 Label
        XAxis mXAxis = chart.getXAxis();

        //YAxis 是影響左右的線，y軸的label
        //chart.getAxisLeft 是影響由左至右的橫線，左邊的 Label
        //chart.getAxisRight 是影響由右至左的橫線，右邊的 Label

        //或是可以用 chart.getAxis(AxisDependency.LEFT); 取得 leftAxis
        //YAxis leftAxis = chart.getAxis(AxisDependency.LEFT);


        //basic
        mXAxis.setEnabled(true); //把有關於 X 軸的所有東西都顯示/隱藏，如 grid line, label text;
        mXAxis.setDrawAxisLine(true); // 顯示/隱藏底部的 x 軸，可是 BarChart 顯示不出來
//        mXAxis.setDrawGridLines(false); // 顯示/隱藏 grid line
//        mXAxis.setDrawLabels(false); // 顯示/隱藏 label
//        chart.getAxisLeft().setEnabled(false); //把左邊的 label text 顯示/隱藏


        // label text
        mXAxis.setTextColor(getResources().getColor(R.color.accent));
        mXAxis.setTextSize(12);
//        mXAxis.setTypeface(Typeface tf)
//        mXAxis.setLabelsToSkip(1); // 要間隔要幾個，要掠過幾個
//        mXAxis.resetLabelsToSkip();//reset skip label setting
//        mXAxis.setSpaceBetweenLabels(20); // default=4 每個欄位的間距
        mXAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // TOP, BOTTOM, BOTH_SIDED, TOP_INSIDE or BOTTOM_INSIDE.

        //表中的 grid line
        mXAxis.setGridColor(getResources().getColor(R.color.green));
        mXAxis.setGridLineWidth(1);

        chart.setHardwareAccelerationEnabled(true); // 如果隔線要變成虛線，這個要先打開
        mXAxis.enableGridDashedLine(10f, 5f, 0f); //phase 好像沒什麼影響QQ
        chart.getAxisLeft().enableGridDashedLine(10f, 10f, 0f); //要左右設定才不會有虛線跟實線一起出現的問題
        chart.getAxisRight().enableGridDashedLine(10f, 10f, 0f);


        LimitLine ll = new LimitLine(3f, "Critical Blood Pressure");
        ll.setLineColor(getResources().getColor(R.color.accent));
        ll.setLineWidth(10f);
        ll.setTextColor(getResources().getColor(R.color.accent));
        ll.setTextSize(12f);

        mXAxis.addLimitLine(ll);

        ll = new LimitLine(1200f, "Basic");
        ll.setLineColor(getResources().getColor(R.color.blue_gray));
        ll.setLineWidth(7f);
        ll.setTextColor(getResources().getColor(R.color.blue_gray));
        ll.setTextSize(12f);
        chart.getAxisLeft().addLimitLine(ll);

        // 底線
        mXAxis.setAxisLineColor(getResources().getColor(R.color.accent));
        mXAxis.setAxisLineWidth(1);
//        chart.enableGridDashedLine(float lineLength, float spaceLength, float phase)

    }

    private void setupYAxisStyle(){
        /* 在自訂設定 y 軸 range 時，一定要先把 data 放進去 */

        YAxis mLeftYAxis = chart.getAxisLeft();
        YAxis mRightYAxis = chart.getAxisRight();


        //Max & Min Value
//        mLeftYAxis.setStartAtZero(false); //有bug, 需要先把atZero設定為false才可以設定min/max
//        mLeftYAxis.setAxisMaxValue(8);
//        mRightYAxis.setAxisMaxValue(5);
        mLeftYAxis.setAxisMinValue(-200f); // 沒有用QQ
        mRightYAxis.setAxisMinValue(-200f); // 沒有用QQ
//        mLeftYAxis.resetAxisMaxValue();

//        mLeftYAxis.setInverted(true); // ASC 或 DESC 數字大小排序

        // top/bottom border 跟 max/min value 增加的 padding percent，可以只設定 Left or Right 軸，但建議還是一起設定，不然會怪怪的
        mLeftYAxis.setSpaceTop(100f);
        mRightYAxis.setSpaceTop(100f);
        mLeftYAxis.setSpaceBottom(100f); //沒有用QQ
        mRightYAxis.setSpaceBottom(100f); //好像沒有用QQ

        //設定 label 顯示
//        mLeftYAxis.setShowOnlyMinMax(true); //只顯示 min 跟 max 的 label 標示
        // 後面的選項為force
        // ture的話代表會精確的平均label的分配(top border 的 max value除以count-1)
        // false的話(top border 除以 count-1 算出 interval，在從 min value 往上加 interval，小數四捨五入，不一定會顯示max value)
        mLeftYAxis.setLabelCount(7, true);

        mRightYAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART); //看 label 要顯示在 chart 內還是外，OUTSIDE_CHART/INSIDE_CHART
    }

    private void setupLegend(){
        Legend l = chart.getLegend();
        l.setFormSize(10f); // 圖例尺寸
        l.setForm(Legend.LegendForm.CIRCLE); // 圖例長相，有 square. line, circle
        l.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER); // 圖例位置
        l.setTextSize(12f);
        l.setTextColor(Color.BLACK);

        l.setXEntrySpace(5f); //圖例的間距
        l.setYEntrySpace(200f); // 圖例的行距
        l.setFormToTextSpace(20f); //圖例跟文字中間的距離
        l.setWordWrapEnabled(true); //圖例超過 chart 的長寬時，是否保留或裁切超過的部分

        // 設定目前圖例自定義的顏色跟數量(像是 replace ，沒有的話，就照 linedata 原本設置的顏色) ※記得數量跟文字要一樣
        l.setCustom(ColorTemplate.VORDIPLOM_COLORS, new String[] { "Set1", "Set2", "Set3", "Set4", "Set5" });
        // 要重新 reset 上面語句的 custom 的話，可以呼叫 resetCustom()
        l.resetCustom();
        // 設定 append 的圖例與顏色
        l.setExtra(ColorTemplate.VORDIPLOM_COLORS, new String[]{"Set1", "Set2", "Set3", "Set4", "Set5"});

    }

    private LineData getLineData(){
        int DATA_COUNT = 5;
        dataSetA = new LineDataSet(getChartData(DATA_COUNT), "LabelA");
        dataSetA.setColor(getResources().getColor(R.color.green));
        dataSetA.setLineWidth(5f);
        //HighLight
        dataSetA.setHighLightColor(Color.CYAN); //調整 hightlight 線 color
        dataSetA.setHighlightLineWidth(3f);

//        dataSetA.setFillColor(getResources().getColor(R.color.yellow)); //記得要設置 drawfilled = true
//        dataSetA.setFillAlpha(255);
//        dataSetA.setDrawFilled(true);

        DATA_COUNT = 6;
        dataSetB = new LineDataSet(getChartData(DATA_COUNT), "LabelB");
        dataSetB.setColor(getResources().getColor(R.color.primary_color));

        //HighLight
        dataSetB.setDrawHighlightIndicators(false); //關閉 highlight 線, 但 popup view 還在
        dataSetB.setDrawVerticalHighlightIndicator(true);

        dataSetB.setFillColor(getResources().getColor(R.color.second_text_color)); //記得要設置 drawfilled = true
        dataSetB.setFillAlpha(85); //最後加入的dataset的z-index最大，alpha預設85，0-255
        dataSetB.setDrawFilled(true);

        DATA_COUNT = 6;
        dataSetC = new LineDataSet(getChartData(DATA_COUNT), "LabelC");
        dataSetC.setColor(getResources().getColor(R.color.second_accent));
        dataSetC.setCircleSize(10f);
//        dataSetC.setDrawCircles(true); LineChart預設為true
//        dataSetC.setCubicIntensity(20f);//曲線強度
        dataSetC.setDrawCubic(true); //平滑曲線
        dataSetC.setCircleColorHole(getResources().getColor(R.color.primary_text_color));
        dataSetC.setCircleColor(getResources().getColor(R.color.second_accent));
        dataSetC.setCircleColors(ColorTemplate.COLORFUL_COLORS);
//        dataSetC.setDrawCircleHole(false); false=實心, true=可以設定setCircleColorHole顏色
        dataSetC.enableDashedLine(10f, 10f, 0f); //設定虛線
        dataSetC.setFillFormatter(new MyCustomFillFormatter());


        /* value format 可以設定給 dataset(單獨) 或 linedata(所有data)
        * 除了自定義自己的 ValueFormatter 外
        * 作者也提供兩個已經設定好的 ValueFormatter
        * 如：LargeValueFormatter -> 超過1000會變1k
        * PercentFormatter -> 顯示成百分比，常用於 PieChart(不過百分比的數字要自己算好)
         * XAxis 跟 YAxis 也可以設定 ValueFormatter，需要實作 YAxisValueFormatter 或 XAxisValueFormatter*/
        dataSetA.setValueFormatter(new MyValueFormatter());
        dataSetB.setValueFormatter(new PercentFormatter());

        List<LineDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSetA); // add the datasets
        dataSets.add(dataSetB);
        dataSets.add(dataSetC);

        LineData mLineData = new LineData(getLabels(DATA_COUNT), dataSets);
//        mLineData.setHighlightEnabled(true); // enbale 當點擊時會有對應xy軸線跟數值pop up view
        mLineData.setDrawValues(true);
        mLineData.setValueTextColor(getResources().getColor(R.color.second_text_color));

        //mLineData.contains(dataSet/entry)
        //mLineData.clearValues()

        return mLineData;
    }

    private List<Entry> getChartData(int count){

        List<Entry> chartData = new ArrayList<>();
        for(int i=0;i<count;i++){
            chartData.add(new Entry((int)(i*1000*Math.random()), i));
        }
        return chartData;
    }

    private List<String> getLabels(int count){

        List<String> chartLabels = new ArrayList<>();
        for(int i=0;i<count;i++) {
            chartLabels.add("X" + i);
        }
        return chartLabels;
    }

    private void setupAnimation(){
        chart.animateY(2000, Easing.EasingOption.Linear);
        chart.animateX(2000, Easing.EasingOption.Linear);
        chart.invalidate();
    }

    private void setupViewPort(){
        // 顯示entry數值的數量，沒啥用，要超過圖上顯示的點才會出現value值
//        chart.setMaxVisibleValueCount(17);

        //設定x軸顯示幾個 label
        chart.setVisibleXRangeMaximum(3); //最大的時候會顯示幾個 x label (不含X0)
        chart.setVisibleXRangeMinimum(2); //放大的時候會顯示幾個 x label

//        chart.setViewPortOffsets(100, 100, 100, 100); //chart 的 padding, 取消自動計算, 自己設定 padding 給他
//        chart.resetViewPortOffsets();
//        chart.setExtraOffsets(100, 100, 100, 100); //一樣是 chart 的 padding, 可是仍然自動計算padding, 並在 append padding

        //move view 會自動 invalidate
        chart.moveViewToX(2f);
        chart.moveViewToY(2000, YAxis.AxisDependency.LEFT);
        chart.zoomIn();
        chart.zoomOut();
//        chart.zoom(float scaleX, float scaleY, float x, float y)
        chart.zoom(3f, 3f, 500, 500);
        chart.fitScreen();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }else if(id == R.id.add_entry){
            dataSetA.addEntry(new Entry(5, 5));
        }else if(id == R.id.remove_last_entry){
            dataSetB.removeLast();
        }

        chart.notifyDataSetChanged();
        chart.invalidate();

        return super.onOptionsItemSelected(item);
    }
}
