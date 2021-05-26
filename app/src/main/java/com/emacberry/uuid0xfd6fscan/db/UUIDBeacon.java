package com.emacberry.uuid0xfd6fscan.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

@Entity(tableName = "UUIDBeacon")
public class UUIDBeacon {

    @PrimaryKey
    @NonNull
    public String uuid;
    @ColumnInfo(name = "last_ts")
    public long lastTs;
    @ColumnInfo(name = "last_signal_strength")
    public int lastSignalStrength;
    @ColumnInfo(name = "is_enf")
    public boolean isENF;
    @ColumnInfo(name = "tx_power_level")
    public int txPowerLevel;
    @ColumnInfo(name = "tx_power")
    public int txPower;
    @ColumnInfo(name = "last_scanned")
    public LocalDateTime lastScanned;

    public UUIDBeacon(String uuid, long lastTs, int lastSignalStrength, boolean isENF, int txPowerLevel, int txPower) {
        this.uuid = uuid;
        this.lastTs = lastTs;
        this.lastSignalStrength = lastSignalStrength;
        this.isENF = isENF;
        this.txPowerLevel = txPowerLevel;
        this.txPower = txPower;
        this.lastScanned = LocalDateTime.now();
    }

    public static String randomMACAddress() {
        byte[] macAddr = new byte[6];
        ThreadLocalRandom.current().nextBytes(macAddr);

        macAddr[0] = (byte) (macAddr[0] & (byte) 254);  //zeroing last 2 bytes to make it unicast and locally adminstrated

        StringBuilder sb = new StringBuilder(18);
        for (byte b : macAddr) {

            if (sb.length() > 0)
                sb.append(":");

            sb.append(String.format("%02x", b));
        }

        return sb.toString().toUpperCase();
    }

    public static UUIDBeacon random() {
        return new UUIDBeacon(randomMACAddress(), -1, -1, false, -1, -1);
    }

    public void insert() {
        AppDatabase.getInstance().beaconDao().upsert(this);
    }

}
