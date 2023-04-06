package com.example.comp1786project.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Trip")
public class Trip {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "user_name")
    public String username;

    @ColumnInfo(name = "trip_name")
    public String tripName;

    @ColumnInfo(name = "destination")
    public String destination;

    @ColumnInfo(name = "date_trip")
    public String dateTrip;

    @ColumnInfo(name = "risk")
    public String risk;

    @ColumnInfo(name = "description")
    public String description;

    public Trip(String username, String tripName, String destination, String dateTrip, String risk, String description) {
        this.username = username;
        this.tripName = tripName;
        this.destination = destination;
        this.dateTrip = dateTrip;
        this.risk = risk;
        this.description = description;
    }
}
