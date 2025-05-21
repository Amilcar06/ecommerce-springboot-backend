package com.plataforma.ecommerce.validation;

import com.plataforma.ecommerce.model.MetodoPago;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class MetodoPagoValidator implements ConstraintValidator<MetodoPagoValido, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return false;
        return Arrays.stream(MetodoPago.values())
                .anyMatch(m -> m.name().equalsIgnoreCase(value));
    }
}
