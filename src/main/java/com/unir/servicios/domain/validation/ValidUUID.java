package com.unir.servicios.domain.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UUIDValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUUID {
  String message() default "El parámetro debe ser un UUID válido.";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
