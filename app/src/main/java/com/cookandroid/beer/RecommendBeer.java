package com.cookandroid.beer;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class RecommendBeer extends AppCompatActivity {
    Button likeButton;
    Button button;
    Button button2;
    private DatabaseReference rDatabase;
    String beerUrltest = "https://www.wine21.com/13_search/beer_view.html?Idx=";
    String data;
    String beerUrl;
    boolean likeState = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_beer);

        //Initialize And Assign Variable
        BottomNavigationView bottomNabvigationView = findViewById(R.id.bottom_navigation);
        bottomNabvigationView.setSelectedItemId(R.id.myinfo);

        bottomNabvigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.mainbeer:
                        startActivity(new Intent(getApplicationContext(), MainbeerActivity.class));
                        overridePendingTransition(0,0);
                        Toast.makeText(getApplicationContext(),"메인 탭 선택됨",Toast.LENGTH_LONG).show();
                        return true;
                    case R.id.ranking:
                        startActivity(new Intent(getApplicationContext(), RankingActivity.class));
                        overridePendingTransition(0,0);
                        Toast.makeText(getApplicationContext(),"랭킹 탭 선택됨",Toast.LENGTH_LONG).show();
                        return true;
                    case R.id.myinfo:
                        startActivity(new Intent(getApplicationContext(), MyInfo.class));
                        overridePendingTransition(0,0);
                        Toast.makeText(getApplicationContext(),"내정보 탭 선택됨",Toast.LENGTH_LONG).show();
                        return true;
                    case R.id.mycamera:
                        scanCode();
                        return true;

                }
                return false;
            }
        });

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
    private void scanCode(){
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(CaptureAct.class);
        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scanning Code");
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents() != null){
                String barcode = result.getContents();
                startRecommendActivity(barcode);
                /*AlertDialog.Builder builder =new AlertDialog.Builder(this);
                String url = "https://www.wine21.com/13_search/beer_view.html?Idx=";
                String url1 = url.concat(barcode);
                builder.setMessage(url1);
                builder.setTitle("Scanning Result");
                builder.setPositiveButton("Scan Again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        scanCode();
                    }
                }).setNegativeButton("finish", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();*/
            }
            else{
                Toast.makeText(this, "No Results", Toast.LENGTH_LONG).show();
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    private void startMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    private void startRecommendActivity(String barcode){
        Intent intent = new Intent(this, RecommendBeer.class);
        intent.putExtra("barcode", barcode);
        //intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
