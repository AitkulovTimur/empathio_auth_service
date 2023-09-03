package com.empathio.auth.exception.attributes;

import lombok.Builder;
import lombok.Value;

import java.time.ZonedDateTime;

@Value
@Builder
public class ApiResponse {
    //or OffsetDateTime?
    //ZonedDateTime may be useful if client will pass timezone through the cookie
    ZonedDateTime catchTime;
    String initiatorId;
    String message;
    String status;
    SeverityLevel severityLevel;
}
