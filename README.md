# ActivityNavigator
Create Intent Objects For Simple Navigation

### Install

```
    annotationProcessor 'ir.mohsenafshar.activitynavigator:navigator-compiler:1.0.1'
    implementation 'ir.mohsenafshar.activitynavigator:navigator-annotation:1.0.1'
```

### Usage

MainActivity:
```
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Navigator.startSecondActivity(this);
    }
}
```

SecondActivity:
```
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
```
For more information look at generated navigator class.
