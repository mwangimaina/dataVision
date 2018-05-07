package app.data;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import app.el.re.data.dataprov1.About;
import app.el.re.data.dataprov1.MainActivity;
import app.el.re.data.dataprov1.R;
import app.helpers.GPSCheck;
import app.helpers.IMEICapture;

public class DataForm1 extends AppCompatActivity {
String street;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_form1);
        //set title bar background for empty Activity
        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffc107")));

        //get device IMEI before the survey
        IMEICapture imei = new IMEICapture(getApplicationContext());
        String dev_imei = imei.getDeviceIMEI();


        //Get shared preeferences
        SharedPreferences pref= this.getSharedPreferences("mypref", MODE_PRIVATE);
        String lat = pref.getString("lat","NULL");
        String lon =pref.getString("lon","NULL");

        //convert to double
        double con_lat = Double.parseDouble(lat);
        double con_lon = Double.parseDouble(lon);


        //==USE cordinated to get the street name
        List<Address> addresses = null;
        Geocoder geocoder = new Geocoder(getApplicationContext());


        try {
            //get street names from cordinates
            addresses = geocoder.getFromLocation(con_lat, con_lon,1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(addresses != null && addresses.size() > 0 )
        {
            Address address = addresses.get(0);
        // Thoroughfare seems to be the street name without numbers
            street = address.getThoroughfare();
        }

        //find the views from this class layout
        EditText latitude = findViewById(R.id.lat);
        EditText longitude = findViewById(R.id.lon);
        EditText yourstreet = findViewById(R.id.street);


        //add the cordinates and street name(if found)
        latitude.setText(lat);
        longitude.setText(lon);
        if (street==""){
            Toast.makeText(this, "Street Not Defined. Retry!", Toast.LENGTH_SHORT).show();
            yourstreet.setText("NO STREET");
        }
        else {

            yourstreet.setText(street);
        }


        //check if user accepts the location or declines
        Button accept = findViewById(R.id.accept);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Form1Collection.class));
                finish();
            }
        });//end
        Button deny = findViewById(R.id.deny);
        deny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });//end



    }//end




}
