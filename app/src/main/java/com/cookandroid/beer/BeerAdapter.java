package com.cookandroid.beer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class BeerAdapter extends RecyclerView.Adapter<BeerAdapter.BeerViewfolder> {
    private ArrayList<Beer> arrayList;
    private Context context;

    public BeerAdapter(ArrayList<Beer> arrayList,Context context) {
        this.arrayList=arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public BeerViewfolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        BeerViewfolder folder=new BeerViewfolder(view);

        return folder;

    }

    @Override
    public void onBindViewHolder(@NonNull BeerViewfolder folder, int position) {
        folder.beer_name.setText(arrayList.get(position).getBeerName());
        folder.beer_country.setText(arrayList.get(position).getBeerCountry());

    }

    @Override
    public int getItemCount() {
        return (arrayList !=null ? arrayList.size() : 0);
    }

    public class BeerViewfolder extends RecyclerView.ViewHolder {
        TextView beer_name;
        TextView beer_country;
        public BeerViewfolder(@NonNull View itemView) {
            super(itemView);
            this.beer_name=itemView.findViewById(R.id.beer_name);
            this.beer_country=itemView.findViewById(R.id.beer_country);

        }
    }
}
