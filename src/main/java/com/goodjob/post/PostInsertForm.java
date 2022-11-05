package com.goodjob.post;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PostInsertFormValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PostInsertForm {
    String message() default "PostInsert form is invalid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
