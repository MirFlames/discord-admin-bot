package ru.ebanievolki.kataplusbot.Handlers.annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface CommandMapping {
    String[] value() default "";
    String description() default "";
    boolean referenceRequired() default false;
}
