package vn.softdream.autotest.security.config;

import vn.softdream.autotest.config.properties.GoogleOAuth2Config;
import vn.softdream.autotest.config.properties.OAuth2Config;
import vn.softdream.autotest.constants.PropertiesConstants;
import vn.softdream.autotest.security.handler.OAuth2AuthenticationFailureHandler;
import vn.softdream.autotest.security.handler.OAuth2AuthenticationSuccessHandler;
import vn.softdream.autotest.security.service.CustomOAuth2AuthorizationRequestResolver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.web.DefaultSecurityFilterChain;

/**
 * Configure OAuth2 Client integration into Spring Security, supporting login via Google OAuth2 <p>
 * {@link PropertiesConstants#OAUTH2_ACTIVE_STATUS} helps turn on/off the OAuth2 feature based on the configuration <p>
 * {@link ConditionalOnProperty} mark this class to be initialized only if the property OAUTH2_ACTIVE_STATUS is "true"
 *
 * @author thoaidc
 */
@Configuration
@ConditionalOnProperty(name = PropertiesConstants.OAUTH2_ACTIVE_STATUS, havingValue = "true")
public class OAuth2ClientConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private static final Logger log = LoggerFactory.getLogger(OAuth2ClientConfig.class);
    private final OAuth2Config oAuth2Configs;
    private final GoogleOAuth2Config googleOAuth2Config;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

    public OAuth2ClientConfig(@Qualifier("OAuth2Config") OAuth2Config oAuth2Configs,
                              @Qualifier("googleOAuth2Config") GoogleOAuth2Config googleOAuth2Config,
                              OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler,
                              OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler) {
        this.oAuth2Configs = oAuth2Configs;
        this.googleOAuth2Config = googleOAuth2Config;
        this.oAuth2AuthenticationSuccessHandler = oAuth2AuthenticationSuccessHandler;
        this.oAuth2AuthenticationFailureHandler = oAuth2AuthenticationFailureHandler;
    }

    /**
     * Used to apply the configuration to activate the OAuth2 feature in {@link SecurityConfig#securityFilterChain} <p>
     * Apply {@link OAuth2AuthenticationSuccessHandler} to handle successful authentication via OAuth2 <p>
     * Apply {@link OAuth2AuthenticationFailureHandler} to handle failure authentication via OAuth2
     *
     * @param http httpSecurity
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.oauth2Login(oAuth2Config -> oAuth2Config
            .successHandler(oAuth2AuthenticationSuccessHandler)
            .failureHandler(oAuth2AuthenticationFailureHandler)
            .authorizationEndpoint(config -> config
                .baseUri(oAuth2Configs.getBaseAuthorizeUri())
                .authorizationRequestResolver(oAuth2AuthorizationRequestResolver())
            )
        );
    }

    /**
     * Register {@link CustomOAuth2AuthorizationRequestResolver} to adjust the parameters of the OAuth2 request
     * before sending it to the provider (such as Google)
     */
    private CustomOAuth2AuthorizationRequestResolver oAuth2AuthorizationRequestResolver() {
        return new CustomOAuth2AuthorizationRequestResolver(clientRegistrationRepository(), oAuth2Configs);
    }

    /**
     * Returns a {@link ClientRegistrationRepository} to manage the configuration details of OAuth2 providers <p>
     * {@link InMemoryClientRegistrationRepository} is used to store the client registration information in memory
     */
    @Bean
    protected ClientRegistrationRepository clientRegistrationRepository() {
        log.debug("Configuring OAuth2Client with Google info");

        // Create additional ClientRegistration and use the code below if you want to register using multiple providers
        // return new InMemoryClientRegistrationRepository(googleClientRegistration(), facebookClientRegistration());

        return new InMemoryClientRegistrationRepository(googleClientRegistration());
    }

    /**
     * Create a {@link ClientRegistration} object for Google OAuth2 <p>
     * Contains all the necessary information to register OAuth2 with Google (such as clientId, clientSecret, etc.) <p>
     * See {@link GoogleOAuth2Config} for details
     */
    private ClientRegistration googleClientRegistration() {
        String redirectUri = oAuth2Configs.getRedirectUri() + googleOAuth2Config.getClientRegistrationId();
        log.debug("Google OAuth2 redirect URI: {}", redirectUri);

        return ClientRegistration
                .withRegistrationId(googleOAuth2Config.getClientRegistrationId())
                .clientId(googleOAuth2Config.getClientID())
                .clientName(googleOAuth2Config.getClientName())
                .clientSecret(googleOAuth2Config.getClientSecret())
                .scope(googleOAuth2Config.getScope())
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationUri(googleOAuth2Config.getAuthorizationUri())
                .redirectUri(redirectUri)
                .tokenUri(googleOAuth2Config.getTokenUri())
                .userInfoUri(googleOAuth2Config.getUserInfoUri())
                .userNameAttributeName(googleOAuth2Config.getUsernameAttributeName())
                .build();
    }
}
