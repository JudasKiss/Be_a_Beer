package com.cookandroid.beer;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
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

import static java.lang.Thread.sleep;

public class MainbeerActivity extends AppCompatActivity implements View.OnClickListener{
    RecyclerView recyclerView,recyclerView2,recyclerView3,recyclerView4;
    MainbeerAdapter adapter,adapter2,adapter3,adapter4;
    private DatabaseReference rDatabase;
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

        //미국
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new MainbeerAdapter(getApplicationContext());
        adapter.addItem(new MainbeerItem("ABInbev","버드와이저","2.8",R.drawable.bud,"00001801828"));
        adapter.addItem(new MainbeerItem("시에라 네바다 브루잉","시에라 네바다 페일 에일","3.9",R.drawable.nevada,"083783375251"));
        adapter.addItem(new MainbeerItem("밸러스트 포인트 브루잉 컴퍼니","스컬핀 IPA","4.6",R.drawable.sculpin,"672438000237"));
        adapter.addItem(new MainbeerItem("배어리퍼블릭 브루잉 컴퍼니","레이서 5","4.9",R.drawable.racer,"722130468085"));
        adapter.addItem(new MainbeerItem("구스 아일랜드 비어","구스 아일랜스 소피","3.7",R.drawable.sofie,"736920112850"));
        adapter.addItem(new MainbeerItem("뉴 벨지움 브루잉 컴퍼니","뉴 벨지움 팻 타이어","4.2",R.drawable.fat,"754527000219"));
        adapter.addItem(new MainbeerItem("보스톤 비어 컴퍼니","사무엘 아담스 보스턴 라거","3.5",R.drawable.samuel,"87692004066"));
        recyclerView.setAdapter(adapter);

        //벨기에
        recyclerView2 = findViewById(R.id.recyclerView2);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        recyclerView2.setLayoutManager(layoutManager2);
        adapter2 = new MainbeerAdapter(getApplicationContext());
        adapter2.addItem(new MainbeerItem("ABInbev","스텔라 아르투아","3.5",R.drawable.stella,"5410228141327"));
        adapter2.addItem(new MainbeerItem("브라세리 듀퐁","본 뵈","4.4",R.drawable.bon,"5410702000010"));
        adapter2.addItem(new MainbeerItem("듀벨 무르트가트","듀벨","4.0",R.drawable.duvel,"5411681014005"));
        adapter2.addItem(new MainbeerItem("분 브루어리","괴즈 분","4.5",R.drawable.boon,"5412783052025"));
        adapter2.addItem(new MainbeerItem("브로어리 디 아슈프","라 쇼페","3.6",R.drawable.la,"5410769100081"));
        adapter2.addItem(new MainbeerItem("페어해게","듀체스 드 브르고뉴","4.0",R.drawable.duchess,"5411364151355"));
        adapter2.addItem(new MainbeerItem("베스트말러 브루어리","베스트말러 트리펠","3.8",R.drawable.tripel,"5412343201337"));
        recyclerView2.setAdapter(adapter2);

        //대한민국
        recyclerView3 = findViewById(R.id.recyclerView3);
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        recyclerView3.setLayoutManager(layoutManager3);
        adapter3 = new MainbeerAdapter(getApplicationContext());
        adapter3.addItem(new MainbeerItem("플레이그라운드 브루어리","미스트레스 사워 에일","3.7",R.drawable.sour,"8809592060018"));
        adapter3.addItem(new MainbeerItem("세븐브로이맥주","강서 마일드 에일","3.2",R.drawable.gangseo,"8809363880437"));
        adapter3.addItem(new MainbeerItem("제주맥주","제주 펠롱 에일","3.4",R.drawable.jeju,"8809556110223"));
        adapter3.addItem(new MainbeerItem("화수브루어리","화수 브루어리 바이젠","3.9",R.drawable.whasoo,"8809617080076"));
        adapter3.addItem(new MainbeerItem("코리아크래프트브루어리","아크 브라운","3.8",R.drawable.ark,"8809546060095"));
        adapter3.addItem(new MainbeerItem("스퀴즈 브루어리","말표 맥주","3.2",R.drawable.malpyo,"8809706850108"));
        adapter3.addItem(new MainbeerItem("플래티넘 크래프트 비어","플래티넘 페일 에일","3.4",R.drawable.platinum,"8809556440009"));
        recyclerView3.setAdapter(adapter3);

        //독일
        recyclerView4 = findViewById(R.id.recyclerView4);
        LinearLayoutManager layoutManager4 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        recyclerView4.setLayoutManager(layoutManager4);
        adapter4 = new MainbeerAdapter(getApplicationContext());
        adapter4.addItem(new MainbeerItem("레벤브로이","레벤브로이 오리지널","3.3",R.drawable.brau,"000040786179"));
        adapter4.addItem(new MainbeerItem("프라이빗 브루어리","가펠 쾰쉬","3.9",R.drawable.kolsch,"4002208017404"));
        adapter4.addItem(new MainbeerItem("헬러브라우 트럼 GMBH","슈렝케를라 메르첸","4.3",R.drawable.marzen,"4037458000012"));
        adapter4.addItem(new MainbeerItem("슈나이더 바이스","슈나이더 탭5 호펜바이세","4.7",R.drawable.tap5,"4003669016104"));
        adapter4.addItem(new MainbeerItem("바이엔슈테판","바이엔슈테판 헤페바이스 비어","4.6",R.drawable.hefeweisen,"4105120573949"));
        adapter4.addItem(new MainbeerItem("바이엔슈테판","바이엔슈테판 비투스","5.0",R.drawable.vitus,"4105120004108"));
        adapter4.addItem(new MainbeerItem("파울라너","파울라너 헤페 바이스비어","3.8",R.drawable.paul,"4066600601920"));
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