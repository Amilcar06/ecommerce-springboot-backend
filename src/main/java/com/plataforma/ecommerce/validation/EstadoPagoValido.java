package com.plataforma.ecommerce.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EstadoPagoValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface EstadoPagoValido {
    String message() default "Estado de pago no válido. Debe ser PENDIENTE, PAGADO, FALLIDO o REEMBOLSADO.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
