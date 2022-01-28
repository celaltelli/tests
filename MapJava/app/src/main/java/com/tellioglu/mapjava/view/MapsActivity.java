package com.tellioglu.mapjava.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.room.Room;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.snackbar.Snackbar;
import com.tellioglu.mapjava.R;
import com.tellioglu.mapjava.databinding.ActivityMapsBinding;
import com.tellioglu.mapjava.model.MyPlace;
import com.tellioglu.mapjava.roomdb.PlaceDao;
import com.tellioglu.mapjava.roomdb.PlaceDataBase;

import java.util.Arrays;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private ActivityResultLauncher<String> permissionLauncher;
    LocationManager locationManager;
    LocationListener locationListener;
    PlaceDataBase dataBase;
    PlaceDao placeDao;
    LatLng latLng;
    MyPlace selectedPlace;
    private CompositeDisposable compositeDisposable;
    AutocompleteSupportFragment autocompleteSupportFragment;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedPreferences = getSharedPreferences("info",MODE_PRIVATE);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        autocompleteSupportFragment = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        registerLauncher();
        dataBase = Room.databaseBuilder(this.getApplicationContext(), PlaceDataBase.class, "Places")
                //.allowMainThreadQueries()
                .build();
        placeDao = dataBase.placeDao();
        compositeDisposable = new CompositeDisposable();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        binding.saveButton.setEnabled(false);
        mMap = googleMap;
        mMap.setOnMapLongClickListener(this);
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), String.valueOf(R.string.apikey));
        }
        autocompleteSupportFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));
        autocompleteSupportFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                LatLng latLng = new LatLng(place.getLatLng().latitude, place.getLatLng().longitude);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
            }

            @Override
            public void onError(Status status) {
                System.out.println("Status: "+status.toString());

            }
        });

        Intent intent = getIntent();
        if (intent.getStringExtra("info").equals("new")) {

            binding.saveButton.setVisibility(View.VISIBLE);
            binding.deleteButton.setVisibility(View.GONE);

            locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15));
                }
            };

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {

                    Snackbar.make(binding.getRoot(), "Permession needed for maps", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Give Permission", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
                                }
                            }).show();

                } else {
                    permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);

                }
            } else {
             String isNew =    sharedPreferences.getString("info","new");
             if(isNew.equals("new")) {
                 locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 1, locationListener);
                 Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                 if (lastLocation != null)
                     mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude()), 15));
                 sharedPreferences.edit().putString("info","old");
             }
                mMap.setMyLocationEnabled(true);
            }


        } else {
            mMap.clear();
            selectedPlace = (MyPlace) intent.getSerializableExtra("place");
            LatLng selectedLatLng = new LatLng(selectedPlace.latitude, selectedPlace.longitude);
            mMap.addMarker(new MarkerOptions().position(selectedLatLng).title(selectedPlace.name));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(selectedLatLng, 15));
            binding.placeNameTextView.setText(selectedPlace.name);
            binding.saveButton.setVisibility(View.GONE);
            binding.deleteButton.setVisibility(View.VISIBLE);
        }


        // locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,1000,locationListener);


        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    public void registerLauncher() {
        permissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @SuppressLint("MissingPermission")
            @Override
            public void onActivityResult(Boolean result) {
                if (result) {

                    String isNew =    sharedPreferences.getString("info","new");
                    if(isNew.equals("new")) {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 1, locationListener);
                        Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (lastLocation != null)
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude()), 15));
                        sharedPreferences.edit().putString("info","old");
                    }
                    mMap.setMyLocationEnabled(true);

                } else {
                    Toast.makeText(MapsActivity.this, "Permission needed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onMapLongClick(@NonNull LatLng latLng) {
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(latLng));
        this.latLng = latLng;
        binding.saveButton.setEnabled(true);
    }

    public void save(View view) {
        MyPlace myPlace = new MyPlace(binding.placeNameTextView.getText().toString(), latLng.latitude, latLng.longitude);
        compositeDisposable.add(placeDao.insert(myPlace)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(MapsActivity.this::handleResponse)
        );
    }

    private void handleResponse() {
        Intent intent = new Intent(MapsActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void delete(View view) {
        compositeDisposable.add(placeDao.delete(selectedPlace)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(MapsActivity.this::handleResponse)
        );
    }
}