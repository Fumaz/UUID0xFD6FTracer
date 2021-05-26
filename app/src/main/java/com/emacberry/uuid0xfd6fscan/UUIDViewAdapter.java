package com.emacberry.uuid0xfd6fscan;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.emacberry.uuid0xfd6fscan.db.UUIDBeacon;

import org.jetbrains.annotations.NotNull;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class UUIDViewAdapter extends RecyclerView.Adapter<UUIDViewAdapter.ViewHolder> {

    private final List<UUIDBeacon> beacons;
    private final LayoutInflater inflater;

    public UUIDViewAdapter(Context context, List<UUIDBeacon> beacons) {
        this.inflater = LayoutInflater.from(context);
        this.beacons = beacons;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.uuid_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.beacon = beacons.get(position);
        holder.uuidView.setText(holder.beacon.uuid);

        if (ScannerService.isNear(holder.beacon.uuid)) {
            holder.uuidView.setTextColor(Color.MAGENTA);
        }
    }

    @Override
    public int getItemCount() {
        return beacons.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected UUIDBeacon beacon;
        protected TextView uuidView;

        ViewHolder(View itemView) {
            super(itemView);
            uuidView = itemView.findViewById(R.id.uuidAddress);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            Toast.makeText(v.getContext(), beacon.lastScanned.format(formatter), Toast.LENGTH_SHORT).show();
        }
    }

}
