package ro.sapientia.Backend.controllers.dto;

import lombok.Data;

import java.util.Date;

@Data
public class LoginResponse  {
    private String token;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String accessToken) {
        this.token = accessToken;
    }
}
