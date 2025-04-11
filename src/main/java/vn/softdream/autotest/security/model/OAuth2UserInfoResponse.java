package vn.softdream.autotest.security.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class OAuth2UserInfoResponse {

    @SerializedName("sub")
    @JsonProperty("sub")
    private String sub;

    @SerializedName("name")
    @JsonProperty("name")
    private String name;

    @SerializedName("given_name")
    @JsonProperty("given_name")
    private String givenName;

    @SerializedName("family_name")
    @JsonProperty("family_name")
    private String familyName;

    @SerializedName("picture")
    @JsonProperty("picture")
    private String picture;

    @SerializedName("email")
    @JsonProperty("email")
    private String email;

    @SerializedName("email_verified")
    @JsonProperty("email_verified")
    private boolean emailVerified;

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    @Override
    public String toString() {
        return "\n\n UserInfoResponse: " +
               "\n\t sub='" + sub +
               "\n\t name='" + name +
               "\n\t givenName='" + givenName +
               "\n\t familyName='" + familyName +
               "\n\t picture='" + picture +
               "\n\t email='" + email +
               "\n\t emailVerified=" + emailVerified + "\n";
    }
}
