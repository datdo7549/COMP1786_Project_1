package com.example.comp1786project.feature.homepage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.comp1786project.databinding.FragmentHomeBinding;
import com.example.comp1786project.feature.add_trip.AddTripFragment;
import com.example.comp1786project.feature.base.BaseFragment;
import com.example.comp1786project.feature.homepage.adapter.TripAdapter;
import com.example.comp1786project.feature.trip_detail.TripDetailFragment;
import com.example.comp1786project.model.Trip;
import com.example.comp1786project.util.listener.ItemTripClickListener;

import java.util.ArrayList;
import java.util.Objects;

public class HomeFragment extends BaseFragment<FragmentHomeBinding, HomeViewModel> implements ItemTripClickListener {
    private HomeViewModel viewModel;
    private TripAdapter tripAdapter;
    private ArrayList<Trip> trips = new ArrayList<>();
    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    protected HomeViewModel viewModel() {
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        return viewModel;
    }

    @Override
    public FragmentHomeBinding onCreateViewBinding(LayoutInflater inflater) {
        return FragmentHomeBinding.inflate(inflater);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initViewModel();
    }

    private void initView() {
        tripAdapter = new TripAdapter(trips, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        viewBinding.rvTrips.setAdapter(tripAdapter);
        viewBinding.rvTrips.setLayoutManager(layoutManager);
        viewBinding.tvUsername.setText(viewModel.getUsername());
        viewBinding.lnAddNewTrip.setOnClickListener(v -> {
            navigate(AddTripFragment.newInstance(), false);
        });
        viewBinding.btnReset.setOnClickListener(v -> {
            viewModel.resetDb();
            trips.clear();
            trips.addAll(viewModel.getTrips());
            tripAdapter.notifyDataSetChanged();
        });

        viewBinding.btnSearch.setOnClickListener(v -> {
            String tripName = Objects.requireNonNull(viewBinding.edtSearch.getText()).toString();
            if (tripName.isEmpty()) {
                trips.clear();
                trips.addAll(viewModel.getTrips());
            } else {
                ArrayList<Trip> searchTrip = (ArrayList<Trip>) viewModel.searchTrip(tripName);
                if (searchTrip.isEmpty()) {
                    Toast.makeText(requireContext(), "Trip is not exists", Toast.LENGTH_SHORT).show();
                } else {
                    trips.clear();
                    trips.addAll(viewModel.searchTrip(tripName));
                }
            }
            tripAdapter.notifyDataSetChanged();

        });
    }

    private void initViewModel() {
        trips.clear();
        trips.addAll(viewModel.getTrips());
        tripAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClicked(String tripId) {
        navigate(TripDetailFragment.newInstance(tripId), false);
    }
}