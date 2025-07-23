package com.github.sparkedstudios.config.annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SparkFile {
    String name();
    FileType type() default FileType.GENERIC;
}
