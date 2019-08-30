package ir.mohsenafshar.activitynavigator;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import ir.mohsenafshar.navigatorannotation.IntExtra;
import ir.mohsenafshar.navigatorannotation.Navigate;
import ir.mohsenafshar.navigatorannotation.StringExtra;

@Navigate
        (
            stringExtra = {
                @StringExtra(key = "STRING_KEY_1", value = "STRING_VALUE_1"),
                @StringExtra(key = "STRING_KEY_2", value = "STRING_VALUE_2")
            },
            intExtra = {
                @IntExtra(key = "INTEGER_KEY_1", value = 10),
                @IntExtra(key = "INTEGER_KEY_2", value = 20)
            }
        )
public class SecondActivity extends AppCompatActivity {

    private static final String TAG = SecondActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Log.d(TAG, "onCreate: " + extras.get("STRING_KEY_1"));
            Log.d(TAG, "onCreate: " + extras.get("INTEGER_KEY_1"));
        }
    }
}
