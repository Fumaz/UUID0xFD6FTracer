package com.emacberry.uuid0xfd6fscan.db;

import android.app.AlertDialog;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    @ColumnInfo(name = "alias")
    @Nullable
    public String alias;
    @ColumnInfo(name = "bond_state")
    public int bondState;
    @ColumnInfo(name = "name")
    @Nullable
    public String name;
    @ColumnInfo(name = "type")
    public int type;
    @ColumnInfo(name = "bluetooth_class_contents")
    public int bluetoothClassContents;
    @ColumnInfo(name = "device_class")
    public int deviceClass;
    @ColumnInfo(name = "major_device_class")
    public int majorDeviceClass;
    @ColumnInfo(name = "last_scanned")
    public LocalDateTime lastScanned;

    public UUIDBeacon(@NonNull String uuid, long lastTs, int lastSignalStrength, boolean isENF, int txPowerLevel, int txPower, @Nullable String alias, int bondState, @Nullable String name, int type, int bluetoothClassContents, int deviceClass, int majorDeviceClass) {
        this.uuid = uuid;
        this.lastTs = lastTs;
        this.lastSignalStrength = lastSignalStrength;
        this.isENF = isENF;
        this.txPowerLevel = txPowerLevel;
        this.txPower = txPower;
        this.alias = alias;
        this.bondState = bondState;
        this.name = name;
        this.type = type;
        this.bluetoothClassContents = bluetoothClassContents;
        this.deviceClass = deviceClass;
        this.majorDeviceClass = majorDeviceClass;
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
        return new UUIDBeacon(randomMACAddress(), -1, -1, false, -1, -1, null, -1, null, -1, -1, -1, -1);
    }

    public void insert() {
        AppDatabase.getInstance().beaconDao().upsert(this);
    }

    public AlertDialog createDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(uuid)
                .setMessage("MAC Address: " + uuid + "\n" +
                        "Last TS: " + lastTs + "\n" +
                        "Last S.Strength: " + lastSignalStrength + "\n" +
                        "ENF: " + isENF + "\n" +
                        "TX Power Level: " + txPowerLevel + "\n" +
                        "TX Power: " + txPower + "\n" +
                        "Alias: " + alias + "\n" +
                        "Name: " + name + "\n" +
                        "Bond State: " + bondState + "\n" +
                        "Type: " + type + "\n" +
                        "BT Class Contents: " + bluetoothClassContents + "\n" +
                        "Device Class: " + deviceClass + "\n" +
                        "Major Device Class: " + majorDeviceClass + "\n" +
                        "Last Scanned: " + lastScanned.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .setCancelable(true)
                .setNegativeButton("Close", (d, a) -> {
                    d.dismiss();
                });

        return builder.create();
    }
}
