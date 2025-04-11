package vn.softdream.autotest.dto.auth;

import vn.softdream.autotest.security.jwt.JwtProvider;
import org.springframework.security.core.Authentication;

/**
 * User information after successful authentication, used to generate the access token
 * Used in {@link JwtProvider#createToken(BaseAuthTokenDTO)}
 *
 * @author thoaidc
 */
@SuppressWarnings("unused")
public class BaseAuthTokenDTO {

    private Authentication authentication; // Contains user authorities information
    private String deviceId;
    private Integer userId;
    private Boolean isRememberMe = false;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final BaseAuthTokenDTO instance = new BaseAuthTokenDTO();

        public Builder authentication(Authentication authentication) {
            instance.authentication = authentication;
            return this;
        }

        public Builder deviceId(String deviceId) {
            instance.deviceId = deviceId;
            return this;
        }

        public Builder userId(Integer userId) {
            instance.userId = userId;
            return this;
        }

        public Builder rememberMe(boolean rememberMe) {
            instance.isRememberMe = rememberMe;
            return this;
        }

        public BaseAuthTokenDTO build() {
            return instance;
        }
    }

    public BaseAuthTokenDTO() {}

    public Authentication getAuthentication() {
        return authentication;
    }

    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Boolean getRememberMe() {
        return isRememberMe;
    }

    public Boolean isRememberMe() {
        return isRememberMe;
    }

    public void setRememberMe(Boolean rememberMe) {
        isRememberMe = rememberMe;
    }
}
