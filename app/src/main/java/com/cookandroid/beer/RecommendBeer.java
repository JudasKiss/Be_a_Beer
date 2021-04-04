package com.cookandroid.beer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class RecommendBeer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_beer);

        Intent intent = getIntent();
        String barcode = intent.getStringExtra("barcode");

        ImageView beerImage = findViewById(R.id.beerImage);
        TextView beerTitleText = findViewById(R.id.beerTitleText);
        TextView beerCompanyText = findViewById(R.id.beerCompanyText);
        TextView beerABVText = findViewById(R.id.beerABVText);
        TextView beerIBUText = findViewById(R.id.beerIBUText);
        TextView beerInfoText = findViewById(R.id.beerInfoText);
        TextView beerStyleText = findViewById(R.id.beerStyleText);

        new Thread(new Runnable() {
            @Override
            public void run() {
                final BeerProduct product = new BeerProduct();
                try{
                    Document doc = Jsoup.connect("https://www.wine21.com/13_search/beer_view.html?Idx=501616").timeout(6000).get();
                    Elements image = doc.select(".column_detail1").select("div.thumb");
                    Elements name = doc.select(".column_detail2").select(".cnt").select("h4");
                    Element company  = doc.select(".column_detail2").select(".wine_info").select(".winery").select("span").get(0);
                    Element ABV = doc.select(".column_detail2").select(".wine_info").select("dd").get(3);
                    Element IBU = doc.select(".column_detail2").select(".wine_info").select("dd").get(7);
                    Element Style = doc.select(".column_detail2").select(".wine_info").select("dd").get(2);
                    Elements Info = doc.select(".column_detail3").select(".item").select("#MakerNote_wrap");
                    String url = image.select("img").attr("src");
                    product.setBeerTitle(name.text());
                    product.setBeerCompany(company.text());
                    product.setBeerABV(ABV.text());
                    product.setBeerIBU(IBU.text());
                    product.setBeerInfo(Info.text());
                    product.setBeerStyle(Style.text());
                    product.setImageUrl(url);
                    //System.out.println(url);
                    //System.out.println(name.text());
                }catch (Exception ex){}
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        beerTitleText.setText(product.getBeerTitle());
                        beerCompanyText.setText(product.getBeerCompany());
                        beerABVText.setText(product.getBeerABV());
                        beerIBUText.setText(product.getBeerIBU());
                        beerInfoText.setText(product.getBeerInfo());
                        beerStyleText.setText(product.getBeerStyle());
                        Picasso.get()
                                .load(product.getImageUrl())
                                .into(beerImage);
                    }

                });

            }
        }).start();

    }
}
