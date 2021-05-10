package com.cookandroid.beer;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;


public class KindsRecommend extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Beer> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference rDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kinds_recommend);

        Intent intent = getIntent();
        String barcode = intent.getStringExtra("barcode");
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList=new ArrayList<>();

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

        database=FirebaseDatabase.getInstance();
        rDatabase = FirebaseDatabase.getInstance().getReference().child("Beer");

        rDatabase.child(barcode).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String data = String.valueOf(snapshot.child("style").getValue());
                    Query query3 = FirebaseDatabase.getInstance().getReference("Beer")
                            .orderByChild("style")
                            .equalTo(data);


                    query3.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            // 파이어베이스의 데이터를 받아오는 곳
                            arrayList.clear(); //기존 배열리스트가 존재하지않게 초기화
                            for(DataSnapshot Snapshot: snapshot.getChildren()){  //반복문으로 데이터 List를 추출
                                Beer beer=Snapshot.getValue(Beer.class); // 만들어뒀던 Beer 객체 데이터를 담는다
                                beer.setCode(Snapshot.getKey());
                                String barcode = Snapshot.getKey();
                                rDatabase = FirebaseDatabase.getInstance().getReference().child("Beer");
                                rDatabase.child(barcode).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if(snapshot.exists()){
                                            String data = String.valueOf(snapshot.child("Code").getValue());
                                            String beerUrltest = "https://www.wine21.com/13_search/beer_view.html?Idx=";
                                            String beerUrl = beerUrltest.concat(data);
                                            new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    try{
                                                        Document doc = Jsoup.connect(beerUrl).timeout(6000).get();
                                                        Elements image = doc.select(".column_detail1").select("div.thumb");
                                                        String url = image.select("img").attr("src");
                                                        beer.setUrl(url);
                                                    }catch (Exception ex){}
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
                                arrayList.add(beer); //담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보낼 준비

                            }
                            adapter.notifyDataSetChanged(); //리스트 저장 및 새로고침
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // 디비를 가져오던중 에러 발생 시
                            Log.e("KindsRecommend", String.valueOf(error.toException())); //에러문 출력
                        }
                    });

                    adapter=new BeerAdapter(arrayList);
                    recyclerView.setAdapter(adapter); //리사이클러뷰에 어뎁터 연결
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
}