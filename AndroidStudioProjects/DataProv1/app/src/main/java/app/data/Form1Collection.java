package app.data;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.util.Calendar;

import app.el.re.data.dataprov1.R;
public class Form1Collection extends AppCompatActivity implements
        View.OnClickListener{

    Button btnDatePicker, btnTimePicker, btnTimeStop;
    EditText txtDate, txtTime, txtStop;
    private int mYear, mMonth, mDay, mHour, mMinute;
    Spinner spinarea, spinfacilty;
    String [] areas = {"HIV","AYSRH","TB"};
    String [] facilities = {"HOME","SCHOOL","HEALTH"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form1_collection);
        ActionBar bar = getSupportActionBar();
        //This form will be uded to collect other data to SQLite or API
        //This is the next MOVE=============........

        //change title bar colr
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffc107")));
        //find views
        btnDatePicker=(Button)findViewById(R.id.btn_date);
        btnTimePicker=(Button)findViewById(R.id.btn_time);
        btnTimeStop=(Button)findViewById(R.id.btn_stoptime);
        txtDate=(EditText)findViewById(R.id.in_date);
        txtTime=(EditText)findViewById(R.id.in_time);
        txtStop=(EditText)findViewById(R.id.in_stoptime);

        //area select list
        spinarea=(Spinner) findViewById(R.id.area);
        ArrayAdapter<String> aa = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,areas);
        spinarea.setAdapter(aa);

        //facility spinner - select list
        spinfacilty=(Spinner) findViewById(R.id.facilitytype);
        ArrayAdapter<String> aafacility = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,facilities);
        spinfacilty.setAdapter(aafacility);


        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);
        btnTimeStop.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if (v == btnDatePicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == btnTimePicker) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            txtTime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }

        if (v == btnTimeStop) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            txtStop.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }//end pickers

    }
}

//TO PROCEED WITH SAVING ----REVIEW THE INTERFACE FIRST
