package ro.sapientia.Backend.controllers.dto;

import lombok.Data;

@Data
public class LoginResponse {
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
