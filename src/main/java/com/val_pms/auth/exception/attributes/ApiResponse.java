package com.val_pms.auth.exception.attributes;

import lombok.Builder;
import lombok.Value;
import org.springframework.http.HttpStatus;

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
