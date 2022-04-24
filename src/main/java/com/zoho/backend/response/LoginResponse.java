package com.zoho.backend.response;

public class LoginResponse {
    private Long id;
    private String authToken;

    public LoginResponse(Long id, String authToken) {
        this.authToken = authToken;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
