package vn.softdream.autotest.config.properties;

import vn.softdream.autotest.constants.PropertiesConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Contains configuration properties related to OAuth2 via Google in the application<p>
 * When the application starts, Spring will automatically create an instance of this class
 * and load the values from configuration files like application.properties or application.yml <p>
 *
 * {@link ConfigurationProperties} helps Spring map config properties to fields,
 * instead of using @{@link Value} for each property individually <p>
 * {@link PropertiesConstants#GOOGLE_OAUTH2_PROPERTIES} decides the prefix for the configurations that will be mapped<p>
 *
 * {@link ConditionalOnProperty} mark this class to be initialized only if the property OAUTH2_ACTIVE_STATUS is "true"<p>
 * {@link PropertiesConstants#OAUTH2_ACTIVE_STATUS} helps turn on/off the OAuth2 feature based on the configuration <p>
 *
 * See <a href="">application-dev.yml</a> for detail
 *
 * @author thoaidc
 */
@Configuration
@ConditionalOnProperty(name = PropertiesConstants.OAUTH2_ACTIVE_STATUS, havingValue = "true")
@ConfigurationProperties(prefix = PropertiesConstants.GOOGLE_OAUTH2_PROPERTIES)
public class GoogleOAuth2Config {

    private String clientRegistrationId;
    private String clientID;
    private String clientName;
    private String clientSecret;
    private String authorizationUri;
    private String tokenUri;
    private String userInfoUri;
    private String usernameAttributeName;
    private List<String> scope;

    public String getClientRegistrationId() {
        return clientRegistrationId;
    }

    public void setClientRegistrationId(String clientRegistrationId) {
        this.clientRegistrationId = clientRegistrationId;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getAuthorizationUri() {
        return authorizationUri;
    }

    public void setAuthorizationUri(String authorizationUri) {
        this.authorizationUri = authorizationUri;
    }

    public String getTokenUri() {
        return tokenUri;
    }

    public void setTokenUri(String tokenUri) {
        this.tokenUri = tokenUri;
    }

    public String getUserInfoUri() {
        return userInfoUri;
    }

    public void setUserInfoUri(String userInfoUri) {
        this.userInfoUri = userInfoUri;
    }

    public String getUsernameAttributeName() {
        return usernameAttributeName;
    }

    public void setUsernameAttributeName(String usernameAttributeName) {
        this.usernameAttributeName = usernameAttributeName;
    }

    public List<String> getScope() {
        return scope;
    }

    public void setScope(List<String> scope) {
        this.scope = scope;
    }
}
