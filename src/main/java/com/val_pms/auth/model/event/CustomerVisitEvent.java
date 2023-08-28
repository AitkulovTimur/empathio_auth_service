package com.val_pms.auth.model.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerVisitEvent {
    private String customerId;

    private ZonedDateTime dateTime;
}
