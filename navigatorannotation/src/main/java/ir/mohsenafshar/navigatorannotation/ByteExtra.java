package ir.mohsenafshar.navigatorannotation;


public @interface ByteExtra {
    String key() default "";
    byte value() default 0;
}
