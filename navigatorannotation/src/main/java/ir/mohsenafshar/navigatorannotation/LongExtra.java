package ir.mohsenafshar.navigatorannotation;


public @interface LongExtra {
    String key() default "";
    long value() default 0;
}
