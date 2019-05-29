package ir.mohsenafshar.activitynavigator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import ir.mohsenafshar.navigatorannotation.Navigate;

@Navigate
public class SecondActivity extends AppCompatActivity {

    public static final String TAG = "SECONDACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Log.d(TAG, "onCreate: " + extras.get("KEY"));
        }
    }
}
