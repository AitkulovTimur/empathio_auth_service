package com.empathio.auth.model;

import lombok.Builder;

@Builder
public class RefreshAccessTokenDTO {
    private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer";
}
