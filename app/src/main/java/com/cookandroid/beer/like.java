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
import com.google.firebase.auth.FirebaseAuth;
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


public class like extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Beer> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference rDatabase;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like);

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
                        Toast.makeText(getApplicationContext(),"?????? ??? ?????????",Toast.LENGTH_LONG).show();
                        return true;
                    case R.id.ranking:
                        startActivity(new Intent(getApplicationContext(), RankingActivity.class));
                        overridePendingTransition(0,0);
                        Toast.makeText(getApplicationContext(),"?????? ??? ?????????",Toast.LENGTH_LONG).show();
                        return true;
                    case R.id.myinfo:
                        startActivity(new Intent(getApplicationContext(), MyInfo.class));
                        overridePendingTransition(0,0);
                        Toast.makeText(getApplicationContext(),"????????? ??? ?????????",Toast.LENGTH_LONG).show();
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
        rDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String userId = mAuth.getUid();
                    Query query3 = FirebaseDatabase.getInstance().getReference("Beer")
                            .orderByChild(userId)
                            .equalTo("1");


                    query3.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            // ????????????????????? ???????????? ???????????? ???
                            arrayList.clear(); //?????? ?????????????????? ?????????????????? ?????????
                            for(DataSnapshot Snapshot: snapshot.getChildren()){  //??????????????? ????????? List??? ??????
                                Beer beer=Snapshot.getValue(Beer.class); // ??????????????? Beer ?????? ???????????? ?????????
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
                                            //????????? ?????? ??? ???????????????
                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                                arrayList.add(beer); //?????? ??????????????? ?????????????????? ?????? ????????????????????? ?????? ??????
                            }
                            adapter.notifyDataSetChanged(); //????????? ?????? ??? ????????????
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // ????????? ??????????????? ?????? ?????? ???
                            Log.e("KindsRecommend", String.valueOf(error.toException())); //????????? ??????
                        }
                    });

                    adapter=new BeerAdapter(arrayList);
                    recyclerView.setAdapter(adapter); //????????????????????? ????????? ??????
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
        startActivity(intent);
    }

    private void startDBexampleActivity(){
        Intent intent = new Intent(this, DBexample.class);
        intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void showDialog(){
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setMessage("?????? ???????????? ?????? ?????????????????? ?????? ???????????? ?????? ????????? ?????????????????????????");
        builder.setTitle("????????????!");
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