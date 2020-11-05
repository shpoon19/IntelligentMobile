package com.iloveandroid.cardsandcolors;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.TypedArray;
import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private SportsAdapter mAdapter;
    private RecyclerView mRecyclerview;
    ArrayList<Sports> mSportData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        mRecyclerview = findViewById(R.id.recyclerview);
        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));

        mSportData = new ArrayList<>();
        mAdapter = new SportsAdapter(this, mSportData);
        mRecyclerview.setAdapter(mAdapter);
        initialize();

    }

    public void initialize(){

        String[] news = getResources().getStringArray(R.array.sports_titles);
        String[] sportList = getResources().getStringArray(R.array.news);
        String[] sportInfo = getResources().getStringArray(R.array.sports_info);
        TypedArray sportImageResources =getResources().obtainTypedArray(R.array.sports_images);

        mSportData.clear();

        for (int i = 0; i < sportInfo.length; i++) {
            mSportData.add(new Sports(news[i], sportList[i], sportInfo[i],
                    sportImageResources.getResourceId(i, 0)));
        }

        sportImageResources.recycle();
        mAdapter.notifyDataSetChanged();
    }
}