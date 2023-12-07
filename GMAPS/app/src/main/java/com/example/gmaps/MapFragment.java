package com.example.gmaps;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends SupportMapFragment implements OnMapReadyCallback {
    private GoogleMap googleMap;
    private boolean mapReady = false;
    private Marker currentMarker;

    @Override
    @NonNull
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        getMapAsync(this);

        return view;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        googleMap = map;
        mapReady = true;

        currentMarker = googleMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Current Position"));

        googleMap.setOnMapClickListener(latLng -> {
            currentMarker.setPosition(latLng);
            Toast.makeText(requireContext(), "Marker Position: " + latLng.latitude + ", " + latLng.longitude, Toast.LENGTH_SHORT).show();
        });

        LatLng ITS = new LatLng(-7.2819705, 112.795323);
        googleMap.addMarker(new MarkerOptions().position(ITS).title("Marker in ITS"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ITS, 15));
    }

    public void updateMap(double lat, double lng, float zoom) {
        if (mapReady) {
            LatLng location = new LatLng(lat, lng);
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, zoom));
        } else {
            Toast.makeText(requireContext(), "Map is not ready yet. Please wait a moment.", Toast.LENGTH_LONG).show();
        }
    }

    public void updateMarker(double lat, double lng) {
        if (mapReady && currentMarker != null) {
            currentMarker.setPosition(new LatLng(lat, lng));
        }
    }

    public void updateMapLocation(double lat, double lng, float zoom) {
        if (googleMap != null) {
            LatLng location = new LatLng(lat, lng);
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, zoom));
        }
    }


}
