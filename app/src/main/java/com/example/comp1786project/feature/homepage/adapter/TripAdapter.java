package com.example.comp1786project.feature.homepage.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comp1786project.R;
import com.example.comp1786project.model.Trip;
import com.example.comp1786project.util.listener.ItemTripClickListener;

import java.util.ArrayList;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.ViewHolder> {

    private ArrayList<Trip> mData;
    private ItemTripClickListener listener;

    public TripAdapter(ArrayList<Trip> data, ItemTripClickListener listener) {
        this.mData = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_trip, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Trip item = mData.get(position);
        holder.tripName.setText(item.tripName);
        holder.dateOfTrip.setText(item.dateTrip);
        holder.itemView.setOnClickListener(v -> {
            listener.onItemClicked(String.valueOf(item.uid));
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public AppCompatTextView tripName;
        public AppCompatTextView dateOfTrip;

        public ViewHolder(View view) {
            super(view);
            tripName = view.findViewById(R.id.tvTripName);
            dateOfTrip = view.findViewById(R.id.dateOfTrip);
        }
    }
}