package com.jash.listmediademo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity implements Callback<Entity> {

    private VideoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        adapter = new VideoAdapter(this, new ArrayList<Entity.ItemsEntity>());
        recyclerView.setAdapter(adapter);
        HttpUtils.getService().getVideo(1).enqueue(this);
    }

    @Override
    public void onResponse(Response<Entity> response, Retrofit retrofit) {
        adapter.addAll(response.body().getItems());
    }

    @Override
    public void onFailure(Throwable t) {
        t.printStackTrace();
        Toast.makeText(this, "网络问题", Toast.LENGTH_SHORT).show();
    }
}
