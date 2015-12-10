package com.awonown.chartsample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by GTW on 2015/11/2.
 */
public class RootActivity extends AppCompatActivity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void onChooseClick(View v){
        Class enter = LineChartDemoActivity.class;;
        switch (v.getId()){
            case R.id.btn_linechart:
                enter = LineChartDemoActivity.class;
                break;
            case R.id.btn_barchart:
                enter = BarChartDemoActivity.class;
                break;
        }
        startActivity(new Intent(this, enter));
    }

}
