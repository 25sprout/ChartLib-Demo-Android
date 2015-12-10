package com.awonown.chartsample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by GTW on 2015/11/2.
 */
public class RootActivity extends AppCompatActivity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Class enter = LineChartDemoActivity.class;
        startActivity(new Intent(this, enter));
        finish();
    }

}
