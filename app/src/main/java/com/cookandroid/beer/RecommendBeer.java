package com.cookandroid.beer;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecommendBeer extends AppCompatActivity implements View.OnClickListener {
    Button likeButton;
    Button button;
    Button button2;
    private TextView test;
    private DatabaseReference rDatabase;
    private DatabaseReference mDatabase;
    float sum = 0;
    private String uId;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    String beerUrltest = "https://www.wine21.com/13_search/beer_view.html?Idx=";
    String data;
    String beerUrl;
    boolean likeState = true;

    private RecyclerView mPostRecyclerView;
    private PostAdapter mAdapter;
    private List<Post> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_beer);

        mPostRecyclerView = findViewById(R.id.main_recyclerview);
        //test=findViewById(R.id.jebaltext);



        findViewById(R.id.main_post_edit).setOnClickListener(this);
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
        TextView beerRemarkText = findViewById(R.id.beerRemarkText);
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
                                Element Remark = doc.select(".column_detail2").select(".wine_info").select("dd").get(13);

                                String url = image.select("img").attr("src");
                                product.setBeerTitle(name.text());
                                product.setBeerCompany(company.text());
                                product.setBeerABV(ABV.text());
                                product.setBeerIBU(IBU.text());
                                product.setBeerRemark(Remark.text());
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
                                    beerRemarkText.setText(product.getBeerRemark());
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

        DatabaseReference rankRef = FirebaseDatabase.getInstance().getReference();
        String userId = mAuth.getUid();
        RatingBar ratingBar = (RatingBar)findViewById(R.id.ratingBar);
        TextView ratingTextView= (TextView)findViewById(R.id.ratingTextView);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingTextView.setText(""+rating);
                rankRef.child("User").child(userId).child("rating").child(barcode).setValue(rating);
            }
        });


        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

        likeButton = (Button)findViewById(R.id.likeButton);
        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(likeState){
                    likeButton.setBackgroundResource(R.drawable.ic_thumb_up_selected);
                    rootRef.child("Beer").child(barcode).child(userId).setValue("1");
                }else{
                    likeButton.setBackgroundResource(R.drawable.ic_thumb_up);
                    rootRef.child("Beer").child(barcode).child(userId).setValue("0");
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
    private void startPostActivity(String barcode){
        Intent intent = new Intent(this, PostActivity.class);
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
                String b = "Beer/";
                String temp = b.concat(barcode);
                rDatabase = FirebaseDatabase.getInstance().getReference(temp);
                rDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        try{
                            String value = dataSnapshot.getValue().toString();
                            startRecommendActivity(barcode);
                        }catch(NullPointerException e){
                            showDialog();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w("Database", "Failed to read value.", error.toException());
                    }
                });
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

    private void startDBexampleActivity(){
        Intent intent = new Intent(this, DBexample.class);
        intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void showDialog(){
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setMessage("저희 데이터에 없는 맥주네요ㅠㅠ 다른 사용자를 위해 맥주를 추가해주시겠어요?");
        builder.setTitle("죄송해요!");
        builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startDBexampleActivity();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mDatas = new ArrayList<>();
        uId = mAuth.getUid();
        Intent intent = getIntent();
        String barcode = intent.getStringExtra("barcode");

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Beer");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mDatas.clear();
                //String barcode="4066600601920";
                for(DataSnapshot ds: snapshot.getChildren()){
                    if(ds.getKey().equals(barcode)){
                        //test.setText(ds.getKey());
                        rDatabase=mDatabase.child(barcode).child("post");
                        rDatabase.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for(DataSnapshot ds2: snapshot.getChildren()){
                                    Post data = ds2.getValue(Post.class);
                                    mDatas.add(data);
                                }
                                mAdapter = new PostAdapter(mDatas);
                                mPostRecyclerView.setAdapter(mAdapter);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        Intent intent = getIntent();
        String barcode = intent.getStringExtra("barcode");
        startPostActivity(barcode);
    }


}
