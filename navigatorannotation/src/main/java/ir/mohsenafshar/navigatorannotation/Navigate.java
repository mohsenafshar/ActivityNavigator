package ir.mohsenafshar.navigatorannotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface Navigate {
    StringExtra[] stringExtra() default @StringExtra();

    IntExtra[] intExtra() default @IntExtra();

    BooleanExtra[] booleanExtra() default @BooleanExtra();

    CharExtra[] charExtra() default @CharExtra();

    DoubleExtra[] doubleExtra() default @DoubleExtra();

    FloatExtra[] floatExtra() default @FloatExtra();

    LongExtra[] longExtra() default @LongExtra();

    ShortExtra[] shortExtra() default @ShortExtra();
}
