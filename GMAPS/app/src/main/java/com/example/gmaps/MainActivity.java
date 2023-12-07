package com.example.gmaps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import android.Manifest;
import com.google.android.gms.location.LocationRequest;
import java.io.IOException;
import java.util.List;

import android.os.Bundle;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {
    private MapFragment mapFragment;
    private EditText editTextLat;
    private EditText editTextLong;
    private EditText editTextZoom;

    private LocationManager locationManager;

    private boolean isLocationUpdatesEnabled = false;


    private LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            if (locationResult != null) {
                Location location = locationResult.getLastLocation();
                if (location != null) {
                    double lat = location.getLatitude();
                    double lng = location.getLongitude();

                    if (mapFragment != null) {
                        mapFragment.updateMarker(lat, lng);
                        mapFragment.updateMapLocation(lat, lng, 15f);
                    }
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            }
        }
        LocationRequest locationRequest = LocationRequest.create()
                .setInterval(5000)         // 5 seconds
                .setFastestInterval(1000)  // 1 second
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        checkLocationPermission();
        mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.my_map);

        if (mapFragment == null)
        {
            mapFragment = new MapFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);

        editTextLat = findViewById(R.id.editText_lat);
        editTextLong = findViewById(R.id.editText_long);
        editTextZoom = findViewById(R.id.idZoom);

        Button go = findViewById(R.id.btnGo);
        go.setOnClickListener(view -> goToLocation());

        Button myLocationButton = findViewById(R.id.btnGoToMyLocation);
        myLocationButton.setOnClickListener(v -> checkLocationPermission());

        EditText editTextLocationName = findViewById(R.id.editText_location_name);
        Button btnUpdateMap = findViewById(R.id.btnUpdateMap);

        btnUpdateMap.setOnClickListener(view -> updateMapByLocationName(editTextLocationName.getText().toString()));
    }

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            double lat = location.getLatitude();
            double lng = location.getLongitude();

            if (mapFragment != null) {
                mapFragment.updateMarker(lat, lng);
                mapFragment.updateMap(lat, lng, 15f);
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}

        @Override
        public void onProviderEnabled(String provider) {}

        @Override
        public void onProviderDisabled(String provider) {}
    };

    private void updateMapByLocationName(String locationName) {
        Geocoder geocoder = new Geocoder(this);
        try {
            List<Address> addresses = geocoder.getFromLocationName(locationName, 1);
            if (!addresses.isEmpty()) {
                double lat = addresses.get(0).getLatitude();
                double lng = addresses.get(0).getLongitude();

                if (mapFragment != null) {
                    mapFragment.updateMap(lat, lng, 15f);
                }
            } else {
                Toast.makeText(this, "Location not found", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error getting location", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isLocationUpdatesEnabled) {
            locationManager.removeUpdates(locationListener);
            isLocationUpdatesEnabled = false;
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {}

    private void goToLocation() {
        String latString = editTextLat.getText().toString();
        String longString = editTextLong.getText().toString();
        String zoomString = editTextZoom.getText().toString();

        try {
            double lat = Double.parseDouble(latString);
            double lng = Double.parseDouble(longString);
            float zoom = Float.parseFloat(zoomString);

            if (mapFragment != null) {
                mapFragment.updateMap(lat, lng, zoom);
            }
            else {
                Toast.makeText(this, "Map is noy ready", Toast.LENGTH_LONG).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            isLocationUpdatesEnabled = true;
        }
        else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private void goToMyLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            if (locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER) != null) {
                Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                double lat = lastLocation.getLatitude();
                double lng = lastLocation.getLongitude();

                if (mapFragment != null) {
                    mapFragment.updateMap(lat, lng, 15f);
                }
            }
            else {
                Toast.makeText(this, "Current location not available", Toast.LENGTH_SHORT).show();
            }

        }
        else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                goToMyLocation();
            }
            else {
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}