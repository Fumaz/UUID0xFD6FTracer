package com.emacberry.uuid0xfd6fscan;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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
    private TextView emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uuid);

        List<UUIDBeacon> beacons = AppDatabase.getInstance().beaconDao().getAll();
        Comparator<UUIDBeacon> comparator = Comparator.comparing(beacon -> ScannerService.isNear(beacon.uuid));
        comparator = comparator.thenComparing(beacon -> beacon.lastScanned);
        comparator = comparator.reversed();

        beacons = beacons.stream()
                .sorted(comparator)
                .collect(Collectors.toList());

        RecyclerView recyclerView = findViewById(R.id.uuidsView);
        emptyView = findViewById(R.id.emptyView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new UUIDViewAdapter(this, beacons);
        recyclerView.setAdapter(adapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), RecyclerView.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        if (beacons.isEmpty()) {
            emptyView.setVisibility(View.VISIBLE);
        }
    }

}