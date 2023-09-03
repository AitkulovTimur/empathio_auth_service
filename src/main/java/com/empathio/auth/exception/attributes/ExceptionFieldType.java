package com.empathio.auth.exception.attributes;

public enum ExceptionFieldType {
    ID("id"),
    USERNAME("username"),
    NAME("name"),
    EMAIL("email");

    public final String value;
    ExceptionFieldType(String value) {
        this.value = value;
    }
}
