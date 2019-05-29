package ir.mohsenafshar.navigatorannotation;


public @interface DoubleExtra {
    String key() default "";
    double value() default 0;
}
