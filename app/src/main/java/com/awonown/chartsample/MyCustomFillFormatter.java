package com.awonown.chartsample;

import android.util.Log;

import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.FillFormatter;
import com.github.mikephil.charting.interfaces.LineDataProvider;

public class MyCustomFillFormatter implements FillFormatter {

    @Override
    public float getFillLinePosition(LineDataSet dataSet, LineDataProvider dataProvider) {

        float myDesiredFillPosition = 100000;
        Log.e("getFillLinePosition", ""+dataProvider.getMaxVisibleCount());

        return myDesiredFillPosition;
    }
}