package com.awonown.chartsample;

import android.widget.TextView;
import android.content.Context;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;

public class MyMakerView extends MarkerView {

    private TextView tvContent;

    public MyMakerView (Context context, int layoutResource) {
        super(context, layoutResource);
        // this markerview only displays a textview
        tvContent = (TextView) findViewById(R.id.tvContent);
    }

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        tvContent.setText("" + e.getVal()); // set the entry-value as the display text

    }

    /*
    * offset 是以點到的那個點作為 (0,0) 然後往右下角畫出來
    * 所以如果要顯示在點的上方
    * X=寬度的一半，負數
    * Y=高度的負數
     */

    @Override
    public int getXOffset() {
        // this will center the marker-view horizontally
        return -(getWidth() / 2);
    }

    @Override
    public int getYOffset() {
        // this will cause the marker-view to be above the selected value
        return -getHeight();
    }
}