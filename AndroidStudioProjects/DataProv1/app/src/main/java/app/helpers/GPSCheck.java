package app.helpers;

import android.content.Context;
import android.location.LocationManager;

public class GPSCheck {
    //this class checks if GPS is ON or OFF
    LocationManager locationManager ;
    boolean GpsStatus ;
    Context context;
    public GPSCheck(Context c){
        this.context = c;

    }
    public boolean CheckGpsStatus(){

        locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);

        GpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        return GpsStatus;//returns a boolean - TRUE/FALSE
    }//end
}//end
