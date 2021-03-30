package com.cookandroid.beer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class RecommendBeer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_beer);

        ImageView beerImage = findViewById(R.id.beerImage);
        TextView beerTitleText = findViewById(R.id.beerTitleText);

        new Thread(new Runnable() {
            @Override
            public void run() {
                final BeerProduct product = new BeerProduct();
                try{
                    Document doc = Jsoup.connect("https://www.wine21.com/13_search/beer_view.html?Idx=501616").timeout(6000).get();
                    Elements image = doc.select(".column_detail1").select("div.thumb");
                    Elements name = doc.select(".column_detail2").select(".cnt").select("h4");
                    String url = image.select("img").attr("src");
                    product.setBeerTitle(name.text());
                    product.setImageUrl(url);
                    //System.out.println(url);
                    //System.out.println(name.text());
                }catch (Exception ex){}
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        beerTitleText.setText(product.getBeerTitle());
                        Picasso.get()
                                .load(product.getImageUrl())
                                .into(beerImage);
                    }
                });

            }
        }).start();

    }
}
