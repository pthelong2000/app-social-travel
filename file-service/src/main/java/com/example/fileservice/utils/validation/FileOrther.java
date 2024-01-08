package com.example.fileservice.utils.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = {FileOrtherValidator.class})
public @interface FileOrther {

    String message() default "{common.message.validation.email.invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}