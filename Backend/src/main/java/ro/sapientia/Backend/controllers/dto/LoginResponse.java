package ro.sapientia.Backend.controllers.dto;

import lombok.Data;

import java.util.Date;

@Data
public class LoginResponse  {
    //private Long userId;
    private String token;

    /*public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }*/

    public String getToken() {
        return token;
    }

    public void setToken(String accessToken) {
        this.token = accessToken;
    }
}
