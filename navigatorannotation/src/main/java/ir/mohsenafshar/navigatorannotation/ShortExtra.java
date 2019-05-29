package ir.mohsenafshar.navigatorannotation;


public @interface ShortExtra {
    String key() default "";
    short value() default 0;
}
