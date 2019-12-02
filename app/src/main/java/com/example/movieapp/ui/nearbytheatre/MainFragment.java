package com.example.movieapp.ui.nearbytheatre;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;
import com.example.movieapp.utils.DataMovieParser;
import com.example.movieapp.utils.DownloadMovieAppUrl;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener {

     MainViewModel mViewModel;
    View root;
    RecyclerView recyclerView;
     Location mLocation;
     LocationManager mLocationManager;
     LocationRequest mLocationRequest;
     long UPDATE_INTERVAL = 2 * 1000;  /* 10 secs */
     long FASTEST_INTERVAL = 20000; /* 20 sec */

     LocationManager locationManager;

    private GoogleApiClient mGoogleApiClient;
    private boolean isPermission;
    double latitude;
    double longitude;


    public static MainFragment newInstance() {
        return new MainFragment();
    }

    ArrayList<ResponseTheatre> responseTheatres = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.main_fragment, container, false);
        recyclerView = root.findViewById(R.id.recyclerView);

        checkLocation();
        if (requestSinglePermission()) {
            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();

            mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

            checkLocation(); //check whether location service is enable or not in your  phone
        }


        return root;
    }

    private void ShowNearbyPlaces(List<HashMap<String, String>> nearbyPlacesList) {

        responseTheatres.clear();
        if (nearbyPlacesList.size() != 0) {
            for (int i = 0; i < nearbyPlacesList.size(); i++) {
                Log.d("onPostExecute", "Entered into showing locations");
                HashMap<String, String> googlePlace = nearbyPlacesList.get(i);
                double lat = Double.parseDouble(googlePlace.get("lat"));
                double lng = Double.parseDouble(googlePlace.get("lng"));
                String placeName = googlePlace.get("place_name");
                String vicinity = googlePlace.get("vicinity");
                String ratting = googlePlace.get("rating");
                String peopleRatting = googlePlace.get("user_ratings_total");
                String imageUrl = googlePlace.get("icon");
                ResponseTheatre responseTheatre = new ResponseTheatre();
                responseTheatre.setAddress(vicinity);
                responseTheatre.setPlaceName(placeName);
                responseTheatre.setRatting(ratting);
                responseTheatre.setPeopleRatting(peopleRatting);
                responseTheatre.setImageUrl(imageUrl);
                responseTheatres.add(responseTheatre);
            }
            TheatreOnlineAdapter adapter = new TheatreOnlineAdapter(getActivity(), responseTheatres);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(adapter);
        } else {
            String jsonColors = getTheatreJson(getActivity());
            DataMovieParser dataParser = new DataMovieParser();
            nearbyPlacesList = dataParser.parse(jsonColors);
            ShowNearbyPlaces(nearbyPlacesList);
        }
    }

    private static String getTheatreJson(Context context) {
        String json;
        try {
            InputStream is;
            boolean jsonPresentOrNot = localJsonPresentOrNot(context);
            Log.e("jsonPresentOrNot:", "" + jsonPresentOrNot);
            if (jsonPresentOrNot) {
                is = context.getAssets().open("nearbymovietheatre.json");
            } else {
                is = context.getAssets().open("nearbymovietheatre.json");
            }

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static boolean localJsonPresentOrNot(Context context) {


        try {
            InputStream is = context.getAssets().open("nearbymovietheatre.json");
            int size = is.available();
            if (size != 0) {
                return true;
            } else {
                return false;
            }

        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        // TODO: Use the ViewModel
    }


    private boolean checkLocation() {
        if (!isLocationEnabled())
            showAlert();
        return isLocationEnabled();
    }

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("Enable Location")
                .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " +
                        "use this app")
                .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                    }
                });
        dialog.show();
    }

    private boolean isLocationEnabled() {
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    protected void startLocationUpdates() {
        // Create the location request
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL);
        // Request location updates
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                mLocationRequest, this);
        Log.d("reque", "--->>>>");
    }
    @Override
    public void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }
    private boolean requestSinglePermission() {

        Dexter.withActivity(getActivity())
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        //Single Permission is granted
                        Toast.makeText(getActivity(), "Single permission is granted!", Toast.LENGTH_SHORT).show();
                        isPermission = true;
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        // check for permanent denial of permission
                        if (response.isPermanentlyDenied()) {
                            isPermission = false;
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();

        return isPermission;

    }

    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        startLocationUpdates();

        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (mLocation == null) {
            startLocationUpdates();
        }
        if (mLocation != null) {
            String theater = "movie_theater";
            latitude = mLocation.getLatitude();
            longitude = mLocation.getLongitude();
            String url = getUrl(latitude, longitude, theater);
            Object[] DataTransfer = new Object[2];
            DataTransfer[0] = "";
            DataTransfer[1] = url;
            Log.d("onClick", url);
            GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
            getNearbyPlacesData.execute(DataTransfer);

            // mLatitudeTextView.setText(String.valueOf(mLocation.getLatitude()));
            //mLongitudeTextView.setText(String.valueOf(mLocation.getLongitude()));
        } else {
            Toast.makeText(getActivity(), "Location not Detected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i("", "Connection Suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i("", "Connection failed. Error: " + connectionResult.getErrorCode());
    }

    @Override
    public void onLocationChanged(Location location) {
        String theater = "movie_theater";
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        String url = getUrl(latitude, longitude, theater);
        Object[] DataTransfer = new Object[2];
        DataTransfer[0] = "";
        DataTransfer[1] = url;
        Log.d("onClick", url);
        GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
        getNearbyPlacesData.execute(DataTransfer);
    }

    private String getUrl(double latitude, double longitude, String nearbyPlace) {

        StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlacesUrl.append("location=").append(latitude).append(",").append(longitude);
        int PROXIMITY_RADIUS = 50000;
        googlePlacesUrl.append("&radius=").append(PROXIMITY_RADIUS);
        googlePlacesUrl.append("&type=").append(nearbyPlace);
        googlePlacesUrl.append("&sensor=true");
        googlePlacesUrl.append("&key=" + "AIzaSyATuUiZUkEc_UgHuqsBJa1oqaODI-3mLs0");
        Log.d("getUrl", googlePlacesUrl.toString());
        return (googlePlacesUrl.toString());
    }

    @SuppressLint("StaticFieldLeak")
    public class GetNearbyPlacesData extends AsyncTask<Object, String, String> {

        String googlePlacesData;
        GoogleMap mMap;
        String url;

        @Override
        protected String doInBackground(Object... params) {
            try {
                Log.d("GetNearbyPlacesData", "doInBackground entered");
                mMap = (GoogleMap) params[0];
                url = (String) params[1];
                DownloadMovieAppUrl downloadUrl = new DownloadMovieAppUrl();
                googlePlacesData = downloadUrl.readUrl(url);
                Log.d("GooglePlacesReadTask", "doInBackground Exit");
            } catch (Exception e) {
                Log.d("GooglePlacesReadTask", e.toString());
            }
            return googlePlacesData;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d("GooglePlacesReadTask", "onPostExecute Entered");
            List<HashMap<String, String>> nearbyPlacesList = new ArrayList<>();
            DataMovieParser dataParser = new DataMovieParser();
            if(result!=null){
                nearbyPlacesList = dataParser.parse(result);
                ShowNearbyPlaces(nearbyPlacesList);
            }
            else{
                ShowNearbyPlaces(nearbyPlacesList);
            }

            Log.d("GooglePlacesReadTask", "onPostExecute Exit");
        }

    }

}
