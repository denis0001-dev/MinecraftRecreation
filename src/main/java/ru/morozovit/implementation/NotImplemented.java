package ru.morozovit.implementation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({
        ElementType.METHOD,
        ElementType.CONSTRUCTOR,
        ElementType.TYPE
})
public @interface NotImplemented {
    boolean value() default true;
}
