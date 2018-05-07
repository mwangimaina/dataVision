package app.data;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import app.el.re.data.dataprov1.About;
import app.el.re.data.dataprov1.R;
import app.helpers.GPSCheck;

public class GetLocation extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    //declare object to be used in the class
    GoogleMap mMap;
    Marker mylocation;
    LatLng latLng;
    LocationManager locationManager;
    private static final int PERMISSION_LOCATION_REQUEST_CODE = 0;
    SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_location);
        pref= this.getSharedPreferences("mypref", MODE_PRIVATE);

        //we check the status for GPS
        GPSCheck gps = new GPSCheck(getApplicationContext());
        boolean check_gps= gps.CheckGpsStatus();

        if (check_gps==true){
            Toast.makeText(this, "GPS ON. Welcome !", Toast.LENGTH_SHORT).show();
            //continue to line 67

        }

        else {
            Toast.makeText(this, "GPS OFF. Please Activate to proceed", Toast.LENGTH_LONG).show();

            //take user to GPS settings
            Intent gpsoptionsintent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(gpsoptionsintent);
            finish();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //enable location button
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        //we have 2 ways of how user can get current location
        //OPTION: user can click on a Map and pick a location directly, IF THE MAP SHOWS
        //CHECK line 129 for option 2
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("lat", String.valueOf(latLng.latitude));
                editor.putString("lon", String.valueOf(latLng.longitude));
                editor.commit();
                //proceed to next activity
                startActivity(new Intent(getApplicationContext(), DataForm1.class));
                finish();//exit intent
            }
        });

// mMap.addMarker(new MarkerOptions().position(jkuat).title("Marker Jkuat"));
        //check if permission is set
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
// TODO: Consider calling
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},PERMISSION_LOCATION_REQUEST_CODE);
            return;
        }
        //allows the app to use your phones location
        mMap.setMyLocationEnabled(true);

        //start of getting my current location
        //get location service,manages location even in background
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        }
        //GET LAST KNOWN LOCATION, IF ANY
        Location location = locationManager.getLastKnownLocation(provider);
        //SOME LAST KNOWN LOCATION IS PRESENT
        if(location!=null){
            //SHOW THE USER THE LAST KNOWN LOCATION
            onLocationChanged(location);
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(latLng) // Sets the center of the map to Mountain View
                    .zoom(13) // Sets the zoom
                    .bearing(45) // Sets the orientation of the camera to east
                    .tilt(45) // Sets the tilt of the camera to 30 degrees
                    .build(); // Creates a CameraPosition from the builder




        }

        //if GPS is on request location updates, GET USER NEW LOCATION - minTime: next update time in milliseconds
        locationManager.requestLocationUpdates(provider,10000000,0, this);

    }
    //GET THE LOCATION, ON LOCATION CHANGED, automatically
    @Override
    public void onLocationChanged(Location location) {
        //remove updates once we a get location
        locationManager.removeUpdates(this);

        // Getting latitude of the current location
        double latitude = location.getLatitude();
        //Getting longitude of the current location
        double longitude = location.getLongitude();



         //save the latitudes and longitudes in the shared preferences
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("lat", String.valueOf(latitude));
        editor.putString("lon", String.valueOf(longitude));
        editor.commit();

        //Move to another activity
        startActivity(new Intent(getApplicationContext(), DataForm1.class));
        finish();//exit intent
        // Creating a LatLng object for the current location
        // THIS YOUR CURRENT LOCATION, UPDATED
        //UPDATE LAST KNOWN, TO CURRENT ONE
        latLng = new LatLng(latitude, longitude);


       // Showing the current location in Google Map
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        if (mylocation != null) {
            mylocation.remove();
        }

// mylocation in the Google Map
        mylocation = mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title("Your Current Location"));

        mMap.animateCamera(CameraUpdateFactory.zoomTo(13));

        //if (mylocation != null) {

        //}
       ;


// mMap.
    }

    //Overridden methods follows
    //below alerts on Location changed
    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        Toast.makeText(this, "Location has Changed!", Toast.LENGTH_SHORT).show();
    }

    //prints if GPS enabled
    @Override
    public void onProviderEnabled(String s) {
        Toast.makeText(this, "Your GPS is enabled!", Toast.LENGTH_SHORT).show();
    }

    //prints if GPS disabled
    @Override
    public void onProviderDisabled(String s) {
        Toast.makeText(this, "Your GPS is not enabled!, Activate yor GPS in your phone settings", Toast.LENGTH_SHORT).show();
    }


    //refreshes intent
    @Override
    protected void onRestart() {
        super.onRestart();
        Intent refresh=getIntent();
        finish();
        overridePendingTransition(0,0);
        startActivity(refresh);
        overridePendingTransition(0,0);
    }


}

