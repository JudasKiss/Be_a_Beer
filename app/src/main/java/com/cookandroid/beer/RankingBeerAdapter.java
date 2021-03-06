package com.cookandroid.beer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RankingBeerAdapter extends RecyclerView.Adapter<RankingBeerAdapter.RankingBeerViewHolder> {

    private ArrayList<RankingBeer> arrayList;
    private Context context;
    String barcode;

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
        Glide.with(holder.itemView.getContext())
                .load(arrayList.get(position).getUrl())
                .into(holder.rImage);
        holder.rName.setText(arrayList.get(position).getBeerName());
        holder.rCountry.setText(arrayList.get(position).getBeerCountry());
        holder.rStyle.setText(arrayList.get(position).getStyle());
        holder.rRating.setText(String.valueOf(arrayList.get(position).getRating()));
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = holder.rName.getText().toString();
                barcode = arrayList.get(position).getCode();
                Query query = FirebaseDatabase.getInstance().getReference("Beer")
                        .orderByChild("beerName")
                        .equalTo(name);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Intent intent = new Intent(holder.itemView.getContext(), RecommendBeer.class);
                        intent.putExtra("barcode", barcode);
                        holder.itemView.getContext().startActivity(intent);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class RankingBeerViewHolder extends RecyclerView.ViewHolder {
        ImageView rImage;
        TextView rName;
        TextView rCountry;
        TextView rStyle;
        TextView rRating;
        public RankingBeerViewHolder(@NonNull View itemView) {
            super(itemView);
            this.rImage = itemView.findViewById(R.id.rImage);
            this.rName = itemView.findViewById(R.id.rName);
            this.rCountry = itemView.findViewById(R.id.rCountry);
            this.rStyle = itemView.findViewById(R.id.rStyle);
            this.rRating = itemView.findViewById(R.id.rRating);
        }
    }
}
