package com.example.comp1786project.feature.trip_detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.comp1786project.databinding.FragmentTripDetailBinding;
import com.example.comp1786project.feature.base.BaseFragment;
import com.example.comp1786project.feature.trip_detail.adapter.ExpenseAdapter;
import com.example.comp1786project.model.Expense;
import com.example.comp1786project.model.Trip;
import com.example.comp1786project.util.listener.AddExpenseDialogListener;
import com.example.comp1786project.util.views.AddExpenseDialog;

import java.util.ArrayList;

public class TripDetailFragment extends BaseFragment<FragmentTripDetailBinding, TripDetailViewModel> implements AddExpenseDialogListener {
    private TripDetailViewModel viewModel;

    private ExpenseAdapter expenseAdapter;

    private ArrayList<Expense> expenses = new ArrayList<>();

    private static final String KEY_1  = "KEY_1";
    public static TripDetailFragment newInstance(String tripId) {
        Bundle args = new Bundle();
        args.putString(KEY_1, tripId);
        TripDetailFragment fragment = new TripDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected TripDetailViewModel viewModel() {
        viewModel = new ViewModelProvider(this).get(TripDetailViewModel.class);
        return viewModel;
    }

    @Override
    public FragmentTripDetailBinding onCreateViewBinding(LayoutInflater inflater) {
        return FragmentTripDetailBinding.inflate(inflater);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.tripId = getArguments().getString(KEY_1, "");
        initView();
        initViewModel();
    }

    private void initView() {
        viewBinding.btnDeleteTrip.setOnClickListener(v -> {
            viewModel.deleteTrip();
            navigateUp();
        });

        viewBinding.lnAddNewExpense.setOnClickListener(v -> {
            AddExpenseDialog addExpenseDialog = new AddExpenseDialog(viewModel.tripId, this);
            addExpenseDialog.setCancelable(false);
            addExpenseDialog.show(getParentFragmentManager(), TripDetailFragment.class.getName());
        });

        expenseAdapter = new ExpenseAdapter(expenses);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        viewBinding.rvExpense.setAdapter(expenseAdapter);
        viewBinding.rvExpense.setLayoutManager(layoutManager);
    }

    private void initViewModel() {
        Trip trip = viewModel.getTripDetail();
        viewBinding.edtTripName.setText(trip.tripName);
        viewBinding.edtDestination.setText(trip.destination);
        viewBinding.edtDateOfTrip.setText(trip.dateTrip);
        viewBinding.edtDescription.setText(trip.description);
        viewBinding.cbRequiredRisk.setChecked(trip.risk.equals("Yes"));

        expenses.clear();
        expenses.addAll((ArrayList<Expense>) viewModel.getExpenses());
        expenseAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAddClicked(Expense expense) {
        viewModel.addExpenseToTrip(expense);
        expenses.clear();
        expenses.addAll((ArrayList<Expense>) viewModel.getExpenses());
        expenseAdapter.notifyDataSetChanged();
    }
}