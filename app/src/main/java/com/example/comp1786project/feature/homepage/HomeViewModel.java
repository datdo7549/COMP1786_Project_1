package com.example.comp1786project.feature.homepage;

import static org.koin.java.KoinJavaComponent.inject;

import com.example.comp1786project.feature.base.BaseViewModel;
import com.example.comp1786project.local.database.ExpenseDao;
import com.example.comp1786project.local.database.TripDao;
import com.example.comp1786project.local.prefs.AppPrefs;
import com.example.comp1786project.model.Trip;

import java.util.List;

import kotlin.Lazy;

public class HomeViewModel extends BaseViewModel {
    private final Lazy<AppPrefs> appPrefs = inject(AppPrefs.class);
    private final Lazy<TripDao> tripDao = inject(TripDao.class);
    private final Lazy<ExpenseDao> expenseDao = inject(ExpenseDao.class);

    public String getUsername() {
        return appPrefs.getValue().getUsername();
    }
    public List<Trip> getTrips() {
        return tripDao.getValue().getTrips(appPrefs.getValue().getUsername());
    }

    public List<Trip> searchTrip(String trip) {
        return tripDao.getValue().searchTrip(trip);
    }

    public void resetDb() {
        tripDao.getValue().deleteAllTrip();
        expenseDao.getValue().deleteAllExpense();
    }
}
