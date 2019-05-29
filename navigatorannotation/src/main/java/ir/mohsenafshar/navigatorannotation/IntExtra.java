package ir.mohsenafshar.navigatorannotation;


public @interface IntExtra {
    String key() default "";
    int value() default 0;
}
