package com.empathio.auth.exception;

import com.empathio.auth.exception.attributes.ExceptionFieldType;

import static java.lang.String.format;

public class NotFoundException extends RuntimeException {
    public NotFoundException(Class<?> typeOfEntity, String value, ExceptionFieldType type) {
        super(format("%s with %s: %s was not found", typeOfEntity.getName(), type.value, value));
    }

    public NotFoundException(Class<?> typeOfEntity) {
        super(format("Requested %s was not found", typeOfEntity.getName()));
    }
}
