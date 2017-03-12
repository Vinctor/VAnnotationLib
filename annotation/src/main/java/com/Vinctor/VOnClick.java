package com.Vinctor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Vinctor on 2017/3/12.
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface VOnClick {
    int[] value();
}
