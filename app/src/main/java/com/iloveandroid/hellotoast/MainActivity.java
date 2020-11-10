package com.iloveandroid.hellotoast;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView showCount;
    int mCount=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        showCount = findViewById(R.id.showCount);
    }

    public void Toast(View view) {
        Toast.makeText( this, "Hello Toast", Toast.LENGTH_SHORT ).show();
    }

    public void showCount(View view) {
        ++mCount;
        if (showCount !=null){
            showCount.setText(Integer.toString(mCount));
        }
    }
}