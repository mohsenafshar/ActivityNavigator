package ir.mohsenafshar.activitynavigator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ir.mohsenafshar.Navigator;
import ir.mohsenafshar.navigatorannotation.NavigatorAnnotation;

@NavigatorAnnotation
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
