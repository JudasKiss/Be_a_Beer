package com.cookandroid.beer;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainbeerActivity extends AppCompatActivity implements View.OnClickListener{
    RecyclerView recyclerView,recyclerView2,recyclerView3,recyclerView4;
    MainbeerAdapter adapter,adapter2,adapter3,adapter4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainbeer);




        //Initialize And Assign Variable
        BottomNavigationView bottomNabvigationView = findViewById(R.id.bottom_navigation);
        bottomNabvigationView.setSelectedItemId(R.id.mainbeer);

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

        //1번
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new MainbeerAdapter(getApplicationContext());
        adapter.addItem(new MainbeerItem("하이네켄","하이네켄","3.1",R.drawable.heineken));
        adapter.addItem(new MainbeerItem("하이네켄","하이네켄","3.1",R.drawable.heineken));
        adapter.addItem(new MainbeerItem("하이네켄","하이네켄","3.1",R.drawable.heineken));
        adapter.addItem(new MainbeerItem("하이네켄","하이네켄","3.1",R.drawable.heineken));
        adapter.addItem(new MainbeerItem("하이네켄","하이네켄","3.1",R.drawable.heineken));
        adapter.addItem(new MainbeerItem("하이네켄","하이네켄","3.1",R.drawable.heineken));
        adapter.addItem(new MainbeerItem("하이네켄","하이네켄","3.1",R.drawable.heineken));
        recyclerView.setAdapter(adapter);

        //2번
        recyclerView2 = findViewById(R.id.recyclerView2);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        recyclerView2.setLayoutManager(layoutManager2);
        adapter2 = new MainbeerAdapter(getApplicationContext());
        adapter2.addItem(new MainbeerItem("버드와이저","버드와이저","2.5",R.drawable.bud));
        adapter2.addItem(new MainbeerItem("버드와이저","버드와이저","2.5",R.drawable.bud));
        adapter2.addItem(new MainbeerItem("버드와이저","버드와이저","2.5",R.drawable.bud));
        adapter2.addItem(new MainbeerItem("버드와이저","버드와이저","2.5",R.drawable.bud));
        adapter2.addItem(new MainbeerItem("버드와이저","버드와이저","2.5",R.drawable.bud));
        adapter2.addItem(new MainbeerItem("버드와이저","버드와이저","2.5",R.drawable.bud));
        adapter2.addItem(new MainbeerItem("버드와이저","버드와이저","2.5",R.drawable.bud));
        adapter2.addItem(new MainbeerItem("버드와이저","버드와이저","2.5",R.drawable.bud));
        adapter2.addItem(new MainbeerItem("버드와이저","버드와이저","2.5",R.drawable.bud));
        recyclerView2.setAdapter(adapter2);

        //3번
        recyclerView3 = findViewById(R.id.recyclerView3);
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        recyclerView3.setLayoutManager(layoutManager3);
        adapter3 = new MainbeerAdapter(getApplicationContext());
        adapter3.addItem(new MainbeerItem("파울라너","파울라너","4.8",R.drawable.paul));
        adapter3.addItem(new MainbeerItem("파울라너","파울라너","4.8",R.drawable.paul));
        adapter3.addItem(new MainbeerItem("파울라너","파울라너","4.8",R.drawable.paul));
        adapter3.addItem(new MainbeerItem("파울라너","파울라너","4.8",R.drawable.paul));
        adapter3.addItem(new MainbeerItem("파울라너","파울라너","4.8",R.drawable.paul));
        adapter3.addItem(new MainbeerItem("파울라너","파울라너","4.8",R.drawable.paul));
        adapter3.addItem(new MainbeerItem("파울라너","파울라너","4.8",R.drawable.paul));
        adapter3.addItem(new MainbeerItem("파울라너","파울라너","4.8",R.drawable.paul));
        recyclerView3.setAdapter(adapter3);

        //4번
        recyclerView4 = findViewById(R.id.recyclerView4);
        LinearLayoutManager layoutManager4 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        recyclerView4.setLayoutManager(layoutManager4);
        adapter4 = new MainbeerAdapter(getApplicationContext());
        adapter4.addItem(new MainbeerItem("스텔라","스텔라 아르투아","4.3",R.drawable.stella));
        adapter4.addItem(new MainbeerItem("스텔라","스텔라 아르투아","4.3",R.drawable.stella));
        adapter4.addItem(new MainbeerItem("스텔라","스텔라 아르투아","4.3",R.drawable.stella));
        adapter4.addItem(new MainbeerItem("스텔라","스텔라 아르투아","4.3",R.drawable.stella));
        adapter4.addItem(new MainbeerItem("스텔라","스텔라 아르투아","4.3",R.drawable.stella));
        adapter4.addItem(new MainbeerItem("스텔라","스텔라 아르투아","4.3",R.drawable.stella));
        adapter4.addItem(new MainbeerItem("스텔라","스텔라 아르투아","4.3",R.drawable.stella));
        adapter4.addItem(new MainbeerItem("스텔라","스텔라 아르투아","4.3",R.drawable.stella));
        adapter4.addItem(new MainbeerItem("스텔라","스텔라 아르투아","4.3",R.drawable.stella));
        adapter4.addItem(new MainbeerItem("스텔라","스텔라 아르투아","4.3",R.drawable.stella));
        adapter4.addItem(new MainbeerItem("스텔라","스텔라 아르투아","4.3",R.drawable.stella));
        recyclerView4.setAdapter(adapter4);
    }



    @Override
    public void onClick(View view) {
        scanCode();
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