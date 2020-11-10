package com.iloveandroid.helloconstrainthomework;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView mShow;
    private int mCount;
    Button mZero, btnCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        mShow = findViewById(R.id.showCount);
        mZero = findViewById(R.id.reset);
    }

    public void showCount(View view) {
        mCount++;
        if (mShow !=null){
            mShow.setText(Integer.toString(mCount));
            mZero.setBackgroundColor(getResources().getColor(R.color.pink));
        }

    }

    public void Reset(View view) {
        mShow.setText(Integer.toString(mCount=0));
        mZero.setBackgroundColor( Color.GRAY);
    }

    public void showToast(View view) {
        Toast.makeText(this, "Hello Toast", Toast.LENGTH_LONG).show();
    }
}