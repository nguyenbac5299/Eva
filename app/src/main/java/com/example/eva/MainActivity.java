package com.example.eva;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.eva.fragment.SetupPeriodFragment;

public class MainActivity extends AppCompatActivity  {
    TextView textView;
    CyclePeriod mCyclePeriod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=findViewById(R.id.text_demo);
        Intent intent=getIntent();
        mCyclePeriod= (CyclePeriod) intent.getSerializableExtra("CYCLEPERIOD");
        textView.setText(mCyclePeriod.getPeriod()+"\n"+mCyclePeriod.getCycle()+"\n"+mCyclePeriod.getBeginDay());
    }


}