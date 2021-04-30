package com.cookandroid.beer;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainbeerAdapter extends RecyclerView.Adapter<MainbeerAdapter.ViewHolder> {

    Context context;

    ArrayList<MainbeerItem> items = new ArrayList<MainbeerItem>();

    public MainbeerAdapter(Context context){
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.item_mainbeer,parent,false);

        return new  ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MainbeerItem item = items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(MainbeerItem item){
        items.add(item);
    }

    public void addItems(ArrayList<MainbeerItem> items){
        this.items = items;
    }

    public MainbeerItem getItem(int position){
        return items.get(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView mbCompany;
        TextView mbName;
        TextView mbRating;
        ImageView mbImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mbCompany = (TextView)itemView.findViewById(R.id.mbCompany);
            mbName = (TextView)itemView.findViewById(R.id.mbName);
            mbRating = (TextView)itemView.findViewById(R.id.mbRating);
            mbImage = (ImageView)itemView.findViewById(R.id.mbImage);
        }
        public void setItem(MainbeerItem item){
            mbCompany.setText(item.getMbCompany());
            mbName.setText(item.getMbName());
            mbRating.setText(item.getMbRating());
            mbImage.setImageResource(item.getMbImage());
        }
    }
}
