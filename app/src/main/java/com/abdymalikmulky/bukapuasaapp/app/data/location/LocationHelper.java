package com.abdymalikmulky.bukapuasaapp.app.data.location;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.abdymalikmulky.bukapuasaapp.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.content.ContentValues.TAG;

/**
 * Bismillahirrahmanirrahim
 * Created by abdymalikmulky on 6/9/17.
 */

public class LocationHelper {
    private Activity activity;
    private LocationListener locationListener;

    public static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    private FusedLocationProviderClient mFusedLocationClient;
    public Location mLastLocation;

    public LocationHelper(Activity activity, LocationListener locationListener) {
        this.activity = activity;
        this.locationListener = locationListener;
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);
    }


    /**
     * Return the current state of the permissions needed.
     */
    public boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    //TODO: Manage permission dengan baik
    public void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(activity,
                        Manifest.permission.ACCESS_FINE_LOCATION);


        if (shouldProvideRationale) {
        showSnackbar(R.string.allow_location_permission, android.R.string.ok,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Request permission
                        ActivityCompat.requestPermissions(activity,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                REQUEST_PERMISSIONS_REQUEST_CODE);
                    }
                });

        } else {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }


    public void showSnackbar(final int mainTextStringId, final int actionStringId,
                              View.OnClickListener listener) {
        Snackbar.make(activity.findViewById(android.R.id.content),
                activity.getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(activity.getString(actionStringId), listener).show();
    }

    //TODO: Buat intuitive callback
    @SuppressWarnings("MissingPermission")
    public void getAddress(){
        final String errorMessage = "";

        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(activity, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location == null) {
                            Log.w(TAG, "onSuccessLoadLocation:null");
                            return;
                        }

                        mLastLocation = location;

                        Geocoder geocoder = new Geocoder(activity.getApplicationContext(), Locale.getDefault());
                        List<Address> addresses = null;

                        try {
                            // Using getFromLocation() returns an array of Addresses for the area immediately
                            // surrounding the given latitude and longitude. The results are a best guess and are
                            // not guaranteed to be accurate.
                            addresses = geocoder.getFromLocation(
                                    location.getLatitude(),
                                    location.getLongitude(),
                                    // In this sample, we getCurrentCity just a single address.
                                    1);


                        } catch (IOException ioException) {
                            // Catch network or other I/O problems.
                            Log.e(TAG, errorMessage, ioException);
                        } catch (IllegalArgumentException illegalArgumentException) {
                            // Catch invalid latitude or longitude values.
                            Log.e(TAG, errorMessage + ". " +
                                    "Latitude = " + location.getLatitude() +
                                    ", Longitude = " + location.getLongitude(), illegalArgumentException);
                        }

                        // Handle case where no address was found.
                        if (addresses == null || addresses.size()  == 0) {
                            if (errorMessage.isEmpty()) {
                                Log.e(TAG, errorMessage);
                            }

                        } else {
                            Address address = addresses.get(0);
                            locationListener.onSuccessLoadLocation(address);

                        }
                        if (!Geocoder.isPresent()) {
                            Toast.makeText(activity, "asd", Toast.LENGTH_SHORT).show();
                            return;
                        }

                    }
                })
                .addOnFailureListener(activity, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "getLastLocation:onFailure", e);
                    }
                });

    }



}
