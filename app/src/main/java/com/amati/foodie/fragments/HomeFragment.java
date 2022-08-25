package com.amati.foodie.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amati.foodie.R;
import com.amati.foodie.adapter.ImageAdapter;
import com.amati.foodie.adapter.recomAdapter;
import com.amati.foodie.location.AppLocationService;
import com.amati.foodie.location.LocationAddress;
import com.amati.foodie.models.ImageModel;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class HomeFragment extends Fragment {

    TextView tvAddress;
    AppLocationService appLocationService;
    RecyclerView recyclerView, recommendRecycler, popularRecycler;
    DatabaseReference databaseReference;
    private Context mContext;
    private Activity mActivity;
    private ArrayList<ImageModel> imagesList;
    private ImageAdapter imageAdapter = null;
    private recomAdapter recomAdapter = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);


        mActivity = getActivity();
        mContext = getContext();
        FirebaseApp.initializeApp(getContext().getApplicationContext());
        recyclerView = view.findViewById(R.id.layoutTop);
        recommendRecycler =view.findViewById(R.id.recommand);
        popularRecycler =view.findViewById(R.id.popular);

        tvAddress =  view.findViewById(R.id.txtLocation);
        appLocationService = new AppLocationService(
                getContext().getApplicationContext());

        Location location = appLocationService
                .getLocation(LocationManager.GPS_PROVIDER);

        if (location != null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            LocationAddress locationAddress = new LocationAddress();
            locationAddress.getAddressFromLocation(latitude, longitude,
                    getContext().getApplicationContext(), new GeocoderHandler());
        } else {
            showSettingsAlert();
        }

        tvAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Location location = appLocationService
                        .getLocation(LocationManager.GPS_PROVIDER);

                if (location != null) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    LocationAddress locationAddress = new LocationAddress();
                    locationAddress.getAddressFromLocation(latitude, longitude,
                            getContext().getApplicationContext(), new GeocoderHandler());
                } else {
                    showSettingsAlert();
                }
            }
        });

        //TopImages Fetch
        recyclerView.setHasFixedSize(true);
        recommendRecycler.setHasFixedSize(true);
        popularRecycler.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new GridLayoutManager(mActivity, 2, GridLayoutManager.VERTICAL, false));
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setNestedScrollingEnabled(false);
        recommendRecycler.setLayoutManager(new GridLayoutManager(mContext,2, GridLayoutManager.HORIZONTAL, false));
//        recommendRecycler.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        recommendRecycler.setNestedScrollingEnabled(false);
        popularRecycler.setLayoutManager(new GridLayoutManager(mContext,2, RecyclerView.HORIZONTAL, false));
        popularRecycler.setNestedScrollingEnabled(false);
        imagesList = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("TP_Images");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                imagesList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    ImageModel imagemodel = dataSnapshot.getValue(ImageModel.class);
                    imagesList.add(imagemodel);
                }
                imageAdapter = new ImageAdapter(mContext,mActivity, (ArrayList<ImageModel>) imagesList);
                recomAdapter = new recomAdapter(mContext,mActivity, (ArrayList<ImageModel>) imagesList);
                recyclerView.setAdapter(imageAdapter);
                recommendRecycler.setAdapter(recomAdapter);
                popularRecycler.setAdapter(imageAdapter);
                imageAdapter.notifyDataSetChanged();
                recomAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext().getApplicationContext(),"Error:" + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


        return view;
    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
        getActivity());
        alertDialog.setTitle("SETTINGS");
        alertDialog.setMessage("Enable Location Provider! Go to settings menu?");
        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        getContext().startActivity(intent);
                    }
                });
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();
    }

    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }
            tvAddress.setText(locationAddress);
        }
}
}