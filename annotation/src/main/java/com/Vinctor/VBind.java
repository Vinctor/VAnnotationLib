package com.Vinctor;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.CLASS)
//@Target(ElementType.FIELD)
public @interface VBind {
    int value();
}
