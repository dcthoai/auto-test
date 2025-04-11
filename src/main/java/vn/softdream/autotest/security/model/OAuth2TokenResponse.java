package vn.softdream.autotest.security.model;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class OAuth2TokenResponse {

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("refresh_token")
    private String refreshToken;

    @SerializedName("expires_in")
    private int expiresIn;

    @SerializedName("scope")
    private String scope;

    @SerializedName("token_type")
    private String tokenType;

    @SerializedName("id_token")
    private String idToken;

    // Getters and setters
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    @Override
    public String toString() {
        return "\n\n OAuth2TokenResponse:" +
               "\n\t accessToken: " + accessToken +
               "\n\t refreshToken: " + refreshToken +
               "\n\t expiresIn: " + expiresIn +
               "\n\t scope: " + scope +
               "\n\t tokenType: " + tokenType +
               "\n\t idToken: " + idToken + "\n";
    }
}
