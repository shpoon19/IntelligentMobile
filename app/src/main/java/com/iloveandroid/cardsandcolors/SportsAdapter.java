package com.iloveandroid.cardsandcolors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class SportsAdapter extends RecyclerView.Adapter<SportsAdapter.SportViewHolder> {
    private Context mContext;
    private ArrayList<Sports> mSportData;

    public SportsAdapter(Context mContext, ArrayList<Sports> mSportData) {
        this.mContext = mContext;
        this.mSportData = mSportData;
    }

    @NonNull
    @Override
    public SportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_list, parent, false);
        return new SportViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SportViewHolder holder, int position) {
        Sports sports = mSportData.get(position);
        holder.bindTo(sports);
    }

    @Override
    public int getItemCount() {
        return mSportData.size();
    }

    public  class SportViewHolder extends RecyclerView.ViewHolder {
        TextView mTextTitle, mTextInfo, mNews;
        private ImageView mSportsImage;

        public SportViewHolder(@NonNull View itemView) {
            super( itemView );
            mNews = itemView.findViewById(R.id.title);
            mTextTitle = itemView.findViewById(R.id.newsTitle);
            mTextInfo = itemView.findViewById(R.id.subTitle);
            mSportsImage = itemView.findViewById(R.id.sportImage);
        }

        void bindTo(Sports currentSport){
            mNews.setText(currentSport.getNews());
            mTextInfo.setText(currentSport.getInfo());
            mTextTitle.setText(currentSport.getTitle());
            Glide.with(mContext).load(currentSport.getImageResource()).into(mSportsImage);
        }
    }
}