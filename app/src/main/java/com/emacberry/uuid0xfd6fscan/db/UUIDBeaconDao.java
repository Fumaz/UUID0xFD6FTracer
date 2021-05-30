package com.emacberry.uuid0xfd6fscan.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UUIDBeaconDao {

    @Query("SELECT * FROM UUIDBeacon")
    List<UUIDBeacon> getAll();

    @Query("DELETE FROM UUIDBeacon")
    void deleteAll();

    @Query("SELECT COUNT(uuid) FROM UUIDBeacon")
    int count();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(UUIDBeacon beacon);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void update(UUIDBeacon beacon);

    @Transaction
    default void upsert(UUIDBeacon beacon) {
        long id = insert(beacon);

        if (id == -1) {
            update(beacon);
        }
    }

}
