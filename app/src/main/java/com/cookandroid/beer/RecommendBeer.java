package com.cookandroid.beer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Text;

public class RecommendBeer extends AppCompatActivity {
    Button likeButton;
    Button button;
    Button button2;
    private DatabaseReference rDatabase;
    String beerUrltest = "https://www.wine21.com/13_search/beer_view.html?Idx=";
    String data;
    String beerUrl;
    boolean likeState = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_beer);

        button=(Button) findViewById(R.id.button);
        button2=(Button) findViewById(R.id.button2);
        Intent intent = getIntent();
        String barcode = intent.getStringExtra("barcode");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNationRecommendActivity(barcode);
            }
        });


        button2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                startKindsRecommendActivity(barcode);
            }
        });


        ImageView beerImage = findViewById(R.id.beerImage);
        TextView beerTitleText = findViewById(R.id.beerTitleText);
        TextView beerCompanyText = findViewById(R.id.beerCompanyText);
        TextView beerABVText = findViewById(R.id.beerABVText);
        TextView beerIBUText = findViewById(R.id.beerIBUText);
        TextView beerInfoText = findViewById(R.id.beerInfoText);
        TextView beerStyleText = findViewById(R.id.beerStyleText);
        BeerProduct product = new BeerProduct();
        rDatabase = FirebaseDatabase.getInstance().getReference().child("Beer");
        rDatabase.child(barcode).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    data = String.valueOf(snapshot.child("Code").getValue());
                    beerUrl = beerUrltest.concat(data);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                Document doc = Jsoup.connect(beerUrl).timeout(6000).get();
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
                else{
                    //바코드 없을 때 오류메세지
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        RatingBar ratingBar = (RatingBar)findViewById(R.id.ratingBar);
        TextView ratingTextView= (TextView)findViewById(R.id.ratingTextView);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingTextView.setText(""+rating);
            }
        });
        likeButton = (Button)findViewById(R.id.likeButton);
        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(likeState){
                    likeButton.setBackgroundResource(R.drawable.ic_thumb_up_selected);
                }else{
                    likeButton.setBackgroundResource(R.drawable.ic_thumb_up);
                }

                likeState=!likeState;
            }
        });

    }
    private void startNationRecommendActivity(String barcode){
        Intent intent = new Intent(this, NationRecommend.class);
        intent.putExtra("barcode", barcode);
        startActivity(intent);
    }
    private void startKindsRecommendActivity(String barcode){
        Intent intent = new Intent(this, KindsRecommend.class);
        intent.putExtra("barcode", barcode);
        startActivity(intent);
    }
}
