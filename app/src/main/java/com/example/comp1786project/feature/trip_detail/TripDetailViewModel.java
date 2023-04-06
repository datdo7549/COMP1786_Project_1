package com.example.comp1786project.feature.trip_detail;

import static org.koin.java.KoinJavaComponent.inject;

import com.example.comp1786project.feature.base.BaseViewModel;
import com.example.comp1786project.local.database.ExpenseDao;
import com.example.comp1786project.local.database.TripDao;
import com.example.comp1786project.local.prefs.AppPrefs;
import com.example.comp1786project.model.Expense;
import com.example.comp1786project.model.Trip;

import java.util.ArrayList;
import java.util.List;

import kotlin.Lazy;

public class TripDetailViewModel extends BaseViewModel {
    private final Lazy<AppPrefs> appPrefs = inject(AppPrefs.class);
    private final Lazy<TripDao> tripDao = inject(TripDao.class);
    private final Lazy<ExpenseDao> expenseDao = inject(ExpenseDao.class);

    public String tripId;

    public Trip getTripDetail() {
        return tripDao.getValue().getTrip(tripId);
    }

    public void deleteTrip() {
        tripDao.getValue().deleteTrip(tripDao.getValue().getTrip(tripId));
    }

    public void addExpenseToTrip(Expense expense) {
        expenseDao.getValue().insertExpense(expense);
    }

    public List<Expense> getExpenses() {
        return expenseDao.getValue().getExpenses(tripId);
    }
}
