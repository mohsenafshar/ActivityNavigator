package ir.mohsenafshar.navigatorannotation;


public @interface BooleanExtra {
    String key() default "";
    boolean value() default false;
}
