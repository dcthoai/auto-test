package vn.softdream.autotest.security.service;

import vn.softdream.autotest.config.properties.OAuth2Config;
import vn.softdream.autotest.constants.PropertiesConstants;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Adjust the parameters of the OAuth2 authorization request before sending it to the OAuth2 provider (such as Google)
 * @author thoaidc
 */
@Component
@ConditionalOnProperty(name = PropertiesConstants.OAUTH2_ACTIVE_STATUS, havingValue = "true")
public class CustomOAuth2AuthorizationRequestResolver implements OAuth2AuthorizationRequestResolver {

    private static final Logger log = LoggerFactory.getLogger(CustomOAuth2AuthorizationRequestResolver.class);
    private final DefaultOAuth2AuthorizationRequestResolver delegate;
    private final OAuth2Config oAuth2Configs;

    public CustomOAuth2AuthorizationRequestResolver(ClientRegistrationRepository client,
                                                    @Qualifier("OAuth2Config") OAuth2Config oAuth2Configs) {
        this.oAuth2Configs = oAuth2Configs;
        this.delegate = new DefaultOAuth2AuthorizationRequestResolver(client, this.oAuth2Configs.getBaseAuthorizeUri());
        log.debug("'CustomOAuth2AuthorizationRequestResolver' is configured for use");
        log.debug("Use URI: {} for authenticate via OAuth2 provider", this.oAuth2Configs.getBaseAuthorizeUri());
    }

    @Override
    public OAuth2AuthorizationRequest resolve(HttpServletRequest request) {
        if (!request.getRequestURI().startsWith(oAuth2Configs.getBaseAuthorizeUri()))
            return null;

        log.debug("Authenticating via default OAuth2 provider from: {}", request.getRequestURI());
        OAuth2AuthorizationRequest authorizationRequest = delegate.resolve(request);

        return requestAdditionalRefreshToken(authorizationRequest);
    }

    @Override
    public OAuth2AuthorizationRequest resolve(HttpServletRequest request, String clientRegistrationId) {
        if (!request.getRequestURI().startsWith(oAuth2Configs.getBaseAuthorizeUri()))
            return null;

        log.debug("Authenticating via {} from: {}", clientRegistrationId, request.getRequestURI());
        OAuth2AuthorizationRequest authorizationRequest = delegate.resolve(request, clientRegistrationId);

        return requestAdditionalRefreshToken(authorizationRequest);
    }

    private OAuth2AuthorizationRequest requestAdditionalRefreshToken(OAuth2AuthorizationRequest authorizationRequest) {
        if (Objects.isNull(authorizationRequest))
            return null;

        log.debug("Modifying request, require issue additional refresh token after successful authentication");

        return OAuth2AuthorizationRequest.from(authorizationRequest)
            .additionalParameters(params -> {
                params.put("access_type", "offline");
                params.put("display", "popup");
                params.put("width", 500);
                params.put("height", 400);
            })
            .build();
    }
}
