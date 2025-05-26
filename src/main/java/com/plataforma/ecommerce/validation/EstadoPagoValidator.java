package com.plataforma.ecommerce.validation;

import com.plataforma.ecommerce.model.enums.EstadoPago;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class EstadoPagoValidator implements ConstraintValidator<EstadoPagoValido, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return false;
        return Arrays.stream(EstadoPago.values())
                .anyMatch(e -> e.name().equalsIgnoreCase(value));
    }
}
