package app.postservice.utils.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = {ContentValidator.class})
public @interface VideoSize {

    String message() default "{common.message.validation.video_size.invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}