package com.awonown.chartsample;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;

/**
 * Created by GTW on 2015/10/26.
 */
public class MyValueFormatter implements ValueFormatter {


    private DecimalFormat mFormat;

    public MyValueFormatter() {
        mFormat = new DecimalFormat("$###,###,##0.0"); // use one
        /* NumberFormat 是以每三位數加上逗號的格式化
         * DecimalFormat 是可以自定義顯示的數字格式
         * # : digit不顯示零
         * 0 : digit會補零
         * @ : 顯示幾位, ex. 12345[@@@]=12300
         * format 用法可以參考以下
         * http://developer.android.com/intl/zh-tw/reference/java/text/DecimalFormat.html */
    }

    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        /* ValueFormatter 一定要實作此方法
        * 會把 value 跟 entry 都傳回來，可以自己作一些邏輯上的處理
        * 主要回傳的是顯示 format 後的數值字串 */
        return mFormat.format(value) ;//+ " $";
    }
}
