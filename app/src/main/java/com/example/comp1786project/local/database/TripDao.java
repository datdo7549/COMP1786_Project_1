package com.example.comp1786project.local.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.comp1786project.model.Trip;

import java.util.List;

@Dao
public interface TripDao {
    @Query("SELECT * FROM trip WHERE uid like :tripId")
    Trip getTrip(String tripId);

    @Insert
    void insertTrip(Trip trip);


    @Query("SELECT * FROM trip WHERE user_name like :username")
    List<Trip> getTrips(String username);

    @Delete
    void deleteTrip(Trip trip);

    @Query("DELETE FROM trip")
    void deleteAllTrip();

    @Query("SELECT * FROM trip WHERE trip_name LIKE '%' || :tripName || '%'")
    List<Trip> searchTrip(String tripName);
}
