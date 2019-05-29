package ir.mohsenafshar.activitynavigator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ir.mohsenafshar.Navigator;
import ir.mohsenafshar.navigatorannotation.IntExtra;
import ir.mohsenafshar.navigatorannotation.Navigate;
import ir.mohsenafshar.navigatorannotation.StringExtra;

@Navigate
        (
                stringExtra = {
                        @StringExtra(key = "STRING_KEY", value = "STRING_VALUE"),
                        @StringExtra(key = "STRING_KEY", value = "STRING_VALUE")
                },
                intExtra = {
                        @IntExtra(key = "INTEGER_KEY", value = 10),
                        @IntExtra(key = "INTEGER_KEY", value = 20)
                }
        )
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle bundle = new Bundle();
        bundle.putString("KEY", "VALUE");
        Navigator.startSecondActivity(this, bundle);
    }
}
