package com.example.comp1786project.feature.add_trip;

import static org.koin.java.KoinJavaComponent.inject;

import com.example.comp1786project.feature.base.BaseViewModel;
import com.example.comp1786project.local.database.TripDao;
import com.example.comp1786project.local.prefs.AppPrefs;
import com.example.comp1786project.model.Trip;

import kotlin.Lazy;

public class AddTripViewModel extends BaseViewModel {
    private final Lazy<AppPrefs> appPrefs = inject(AppPrefs.class);
    private final Lazy<TripDao> tripDao = inject(TripDao.class);
    public void saveTrip(String tripName, String destination, String dateOfTrip, String description, String requiredRisk) {
        String username = appPrefs.getValue().getUsername();
        Trip trip = new Trip(username, tripName, destination, dateOfTrip, requiredRisk, description);
        tripDao.getValue().insertTrip(trip);
    }
}
