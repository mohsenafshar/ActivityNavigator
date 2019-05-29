package ir.mohsenafshar.navigatorannotation;


public @interface CharExtra {
    String key() default "";
    char value() default 0;
}
