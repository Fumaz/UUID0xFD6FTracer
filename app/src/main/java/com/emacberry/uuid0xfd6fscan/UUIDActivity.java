package com.emacberry.uuid0xfd6fscan;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.emacberry.uuid0xfd6fscan.db.AppDatabase;
import com.emacberry.uuid0xfd6fscan.db.UUIDBeacon;

import java.util.List;

public class UUIDActivity extends AppCompatActivity {

    private UUIDViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uuid);

        List<UUIDBeacon> beacons = AppDatabase.getInstance().beaconDao().getAll();
        RecyclerView recyclerView = findViewById(R.id.uuidsView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new UUIDViewAdapter(this, beacons);
        recyclerView.setAdapter(adapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), RecyclerView.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

}