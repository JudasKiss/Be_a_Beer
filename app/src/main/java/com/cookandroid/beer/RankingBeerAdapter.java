package com.cookandroid.beer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RankingBeerAdapter extends RecyclerView.Adapter<RankingBeerAdapter.RankingBeerViewHolder> {

    private ArrayList<RankingBeer> arrayList;
    private Context context;

    public RankingBeerAdapter(ArrayList<RankingBeer> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public RankingBeerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_rankingbeer,parent,false);
        RankingBeerViewHolder holder = new RankingBeerViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RankingBeerViewHolder holder, int position) {
        /*Glide.with(holder.itemView)
                .load(arrayList.get(position).getProfile())
                .into(holder.rImage);*/     //여기는 맥주 사진
        holder.rName.setText(arrayList.get(position).getName());
        holder.rCountry.setText(arrayList.get(position).getCountry());
        holder.rStyle.setText(arrayList.get(position).getStyle());
        holder.rRating.setText(String.valueOf(arrayList.get(position).getRating()));
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class RankingBeerViewHolder extends RecyclerView.ViewHolder {
        //ImageView rImage;
        TextView rName;
        TextView rCountry;
        TextView rStyle;
        TextView rRating;
        public RankingBeerViewHolder(@NonNull View itemView) {
            super(itemView);
            //this.rImage = itemView.findViewById(R.id.rImage);
            this.rName = itemView.findViewById(R.id.rName);
            this.rCountry = itemView.findViewById(R.id.rCountry);
            this.rStyle = itemView.findViewById(R.id.rStyle);
            this.rRating = itemView.findViewById(R.id.rRating);
        }
    }
}
