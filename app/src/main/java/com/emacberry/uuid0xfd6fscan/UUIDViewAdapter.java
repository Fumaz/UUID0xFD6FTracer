package com.emacberry.uuid0xfd6fscan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.emacberry.uuid0xfd6fscan.db.UUIDBeacon;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class UUIDViewAdapter extends RecyclerView.Adapter<UUIDViewAdapter.ViewHolder> {

    private List<UUIDBeacon> beacons;
    private LayoutInflater inflater;

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
        String address = beacons.get(position).uuid;
        holder.uuidView.setText(address);
    }

    @Override
    public int getItemCount() {
        return beacons.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView uuidView;

        ViewHolder(View itemView) {
            super(itemView);
            uuidView = itemView.findViewById(R.id.uuidAddress);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // TODO
        }
    }
}
