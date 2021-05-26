package com.emacberry.uuid0xfd6fscan;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.emacberry.uuid0xfd6fscan.db.AppDatabase;
import com.emacberry.uuid0xfd6fscan.db.UUIDBeacon;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class UUIDActivity extends AppCompatActivity {

    private UUIDViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uuid);

        List<UUIDBeacon> beacons = AppDatabase.getInstance().beaconDao().getAll();
        Comparator<UUIDBeacon> comparator = Comparator.comparing(beacon -> beacon.lastScanned);
        comparator = comparator.thenComparing(beacon -> ScannerService.isNear(beacon.uuid));

        beacons = beacons.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
        
        RecyclerView recyclerView = findViewById(R.id.uuidsView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new UUIDViewAdapter(this, beacons);
        recyclerView.setAdapter(adapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), RecyclerView.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

}