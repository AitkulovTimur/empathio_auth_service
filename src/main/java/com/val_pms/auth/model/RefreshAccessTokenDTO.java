package com.val_pms.auth.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Value;
import lombok.experimental.Accessors;

@Builder
public class RefreshAccessTokenDTO {
    private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer";
}
