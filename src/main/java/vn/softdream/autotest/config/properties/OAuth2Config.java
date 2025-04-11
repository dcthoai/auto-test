package vn.softdream.autotest.config.properties;

import vn.softdream.autotest.constants.PropertiesConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Contains base configuration properties related to OAuth2 in the application<p>
 * When the application starts, Spring will automatically create an instance of this class
 * and load the values from configuration files like application.properties or application.yml <p>
 *
 * {@link ConfigurationProperties} helps Spring map config properties to fields,
 * instead of using @{@link Value} for each property individually <p>
 * {@link PropertiesConstants#OAUTH2_CONFIG_PROPERTIES} decides the prefix for the configurations that will be mapped<p>
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
@ConfigurationProperties(prefix = PropertiesConstants.OAUTH2_CONFIG_PROPERTIES)
public class OAuth2Config {

    private boolean enabled;
    private String baseAuthorizeUri;
    private String defaultRedirectUri;
    private String redirectUri;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getBaseAuthorizeUri() {
        return baseAuthorizeUri;
    }

    public void setBaseAuthorizeUri(String baseAuthorizeUri) {
        this.baseAuthorizeUri = baseAuthorizeUri;
    }

    public String getDefaultRedirectUri() {
        return defaultRedirectUri;
    }

    public void setDefaultRedirectUri(String defaultRedirectUri) {
        this.defaultRedirectUri = defaultRedirectUri;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }
}
