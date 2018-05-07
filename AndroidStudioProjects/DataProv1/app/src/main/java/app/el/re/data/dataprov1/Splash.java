package app.el.re.data.dataprov1;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //create a handler Object for the time
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() { //runnable thread
                //Intent
                Intent x = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(x);
                finish();
            }
            //delay the INTENT for   5 Secs(5000 millis)
        }, 5000);

    }
}
