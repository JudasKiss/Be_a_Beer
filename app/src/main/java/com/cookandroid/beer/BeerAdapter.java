package com.cookandroid.beer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BeerAdapter extends RecyclerView.Adapter<BeerAdapter.BeerViewfolder> {
    private ArrayList<Beer> arrayList;
    private DatabaseReference rDatabase;
    String barcode;

    public BeerAdapter(ArrayList<Beer> arrayList) {
        this.arrayList=arrayList;
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
        folder.beer_style.setText(arrayList.get(position).getStyle());
        folder.itemView.setTag(position);
        folder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = folder.beer_name.getText().toString();
                barcode = arrayList.get(position).getCode();
                Query query = FirebaseDatabase.getInstance().getReference("Beer")
                        .orderByChild("beerCountry")
                        .equalTo(name);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Intent intent = new Intent(folder.itemView.getContext(), RecommendBeer.class);
                        intent.putExtra("barcode", barcode);
                        folder.itemView.getContext().startActivity(intent);
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
        return (arrayList !=null ? arrayList.size() : 0);
    }

    public class BeerViewfolder extends RecyclerView.ViewHolder {
        TextView beer_name;
        TextView beer_country;
        TextView beer_style;
        public BeerViewfolder(@NonNull View itemView) {
            super(itemView);
            this.beer_name=itemView.findViewById(R.id.beer_name);
            this.beer_country=itemView.findViewById(R.id.beer_country);
            this.beer_style=itemView.findViewById(R.id.beer_style);

        }
    }

}
