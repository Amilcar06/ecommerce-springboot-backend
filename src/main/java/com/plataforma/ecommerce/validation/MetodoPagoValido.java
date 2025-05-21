package com.plataforma.ecommerce.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MetodoPagoValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface MetodoPagoValido {
    String message() default "Método de pago no válido. Debe ser QR, TARJETA o TRANSFERENCIA.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
