package vn.softdream.autotest.config.properties;

import vn.softdream.autotest.constants.PropertiesConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Contains security configurations such as the secret key<p>
 * When the application starts, Spring will automatically create an instance of this class
 * and load the values from configuration files like application.properties or application.yml <p>
 *
 * {@link ConfigurationProperties} helps Spring map config properties to fields,
 * instead of using @{@link Value} for each property individually <p>
 *
 * {@link PropertiesConstants#SECURITY_CONFIG} decides the prefix for the configurations that will be mapped <p>
 *
 * See <a href="">application-dev.yml</a> for detail
 *
 * @author thoaidc
 */
@Configuration
@ConfigurationProperties(prefix = PropertiesConstants.SECURITY_CONFIG)
public class Security {

    private boolean enabledTls;
    private String base64SecretKey;
    private Long tokenValidity;
    private Long tokenValidityForRememberMe;

    public boolean isEnabledTls() {
        return enabledTls;
    }

    public void setEnabledTls(boolean enabledTls) {
        this.enabledTls = enabledTls;
    }

    public String getBase64SecretKey() {
        return base64SecretKey;
    }

    public void setBase64SecretKey(String base64SecretKey) {
        this.base64SecretKey = base64SecretKey;
    }

    public Long getTokenValidity() {
        return tokenValidity;
    }

    public void setTokenValidity(Long tokenValidity) {
        this.tokenValidity = tokenValidity;
    }

    public Long getTokenValidityForRememberMe() {
        return tokenValidityForRememberMe;
    }

    public void setTokenValidityForRememberMe(Long tokenValidityForRememberMe) {
        this.tokenValidityForRememberMe = tokenValidityForRememberMe;
    }
}
