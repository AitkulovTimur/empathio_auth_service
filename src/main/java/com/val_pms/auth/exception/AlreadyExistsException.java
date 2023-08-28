package com.val_pms.auth.exception;

import com.val_pms.auth.exception.attributes.ExceptionFieldType;

import static java.lang.String.format;

public class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException(Class<?> typeOfEntity, String value, ExceptionFieldType type) {
        super(format("%s with %s: %s already exists", typeOfEntity.getName(), type.value, value));
    }
}
