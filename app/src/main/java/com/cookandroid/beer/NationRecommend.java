package com.cookandroid.beer;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class NationRecommend extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Beer> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nation_recommend);

        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList=new ArrayList<>();

        database=FirebaseDatabase.getInstance();

        Query query3 = FirebaseDatabase.getInstance().getReference("Beer")
                .orderByChild("beerCountry")
                .equalTo("독일");


        query3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // 파이어베이스의 데이터를 받아오는 곳
                arrayList.clear(); //기존 배열리스트가 존재하지않게 초기화
                for(DataSnapshot Snapshot: snapshot.getChildren()){  //반복문으로 데이터 List를 추출
                    Beer beer=Snapshot.getValue(Beer.class); // 만들어뒀던 Beer 객체 데이터를 담는다
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

        adapter=new BeerAdapter(arrayList,this);
        recyclerView.setAdapter(adapter); //리사이클러뷰에 어뎁터 연결
    }

}