package vn.softdream.autotest.security.service;

import vn.softdream.autotest.common.JsonUtils;
import vn.softdream.autotest.config.properties.GoogleOAuth2Config;
import vn.softdream.autotest.config.properties.OAuth2Config;
import vn.softdream.autotest.constants.ExceptionConstants;
import vn.softdream.autotest.constants.PropertiesConstants;
import vn.softdream.autotest.exception.BaseBadRequestException;
import vn.softdream.autotest.security.model.OAuth2TokenResponse;
import vn.softdream.autotest.security.model.OAuth2UserInfoResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@ConditionalOnProperty(name = PropertiesConstants.OAUTH2_ACTIVE_STATUS, havingValue = "true")
public class GoogleOAuth2Service {

    private static final Logger log = LoggerFactory.getLogger(GoogleOAuth2Service.class);
    private static final String ENTITY_NAME = "GoogleOAuth2Service";
    private final OAuth2Config oAuth2Configs;
    private final GoogleOAuth2Config googleOAuth2Config;
    private final RestTemplate restTemplate;

    public GoogleOAuth2Service(@Qualifier("OAuth2Config") OAuth2Config oAuth2Config,
                               @Qualifier("googleOAuth2Config") GoogleOAuth2Config googleOAuth2Config,
                               RestTemplate restTemplate) {
        this.oAuth2Configs = oAuth2Config;
        this.googleOAuth2Config = googleOAuth2Config;
        this.restTemplate = restTemplate;
    }

    private MultiValueMap<String, String> getGoogleOAuth2Config(String authorizationCode) {
        MultiValueMap<String, String> config = new LinkedMultiValueMap<>();

        config.add("code", authorizationCode);
        config.add("client_id", googleOAuth2Config.getClientID());
        config.add("client_secret", googleOAuth2Config.getClientSecret());
        config.add("redirect_uri", oAuth2Configs.getRedirectUri() + googleOAuth2Config.getClientRegistrationId());
        config.add("grant_type", AuthorizationGrantType.AUTHORIZATION_CODE.getValue());

        return config;
    }

    public OAuth2TokenResponse getAccessToken(String code) {
        log.debug("Get access token from Google");
        String errorKey;

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(getGoogleOAuth2Config(code), headers);
            ResponseEntity<String> response = restTemplate.postForEntity(googleOAuth2Config.getTokenUri(), request, String.class);

            if (response.getStatusCode().isError()) {
                log.error("Failed to get access token. Status: {} - {}", response.getStatusCode(), response.getBody());
                throw new BaseBadRequestException(ENTITY_NAME, ExceptionConstants.OAUTH2_AUTHORIZATION_CODE_NOT_FOUND);
            }

            String responseBody = response.getBody();

            if (!StringUtils.hasText(responseBody)) {
                log.error("Empty response received while get access token from google. {}", response.getStatusCode());
                throw new BaseBadRequestException(ENTITY_NAME, ExceptionConstants.OAUTH2_AUTHORIZATION_CODE_NOT_FOUND);
            }

            return JsonUtils.parseJson(responseBody, OAuth2TokenResponse.class);
        } catch (HttpClientErrorException e) {
            errorKey = ExceptionConstants.OAUTH2_AUTHORIZATION_CODE_CLIENT_ERROR;
            log.error("Client error while get access token: {}, {}", e.getStatusCode(), e.getMessage());
        } catch (HttpServerErrorException e) {
            errorKey = ExceptionConstants.OAUTH2_AUTHORIZATION_CODE_SERVER_ERROR;
            log.error("Server error while get access token: {}, {}", e.getStatusCode(), e.getMessage());
        } catch (Exception e) {
            errorKey = ExceptionConstants.OAUTH2_AUTHORIZATION_CODE_EXCEPTION;
            log.error("Unexpected error occurred while get access token: {}", e.getMessage());
        }

        throw new BaseBadRequestException(ENTITY_NAME, errorKey);
    }

    public OAuth2UserInfoResponse getUserInfo(String accessToken) {
        String errorKey;

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(accessToken);
            String url = googleOAuth2Config.getUserInfoUri();
            HttpEntity<Void> request = new HttpEntity<>(headers);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);

            if (response.getStatusCode().isError()) {
                log.error("Failed to get userinfo from Google. {} - {}", response.getStatusCode(), response.getBody());
                throw new BaseBadRequestException(ENTITY_NAME, ExceptionConstants.OAUTH2_USER_INFO_NOT_FOUND);
            }

            String responseBody = response.getBody();

            if (!StringUtils.hasText(responseBody)) {
                log.error("Empty response received while get userinfo from Google. {}", response.getStatusCode());
                throw new BaseBadRequestException(ENTITY_NAME, ExceptionConstants.OAUTH2_USER_INFO_NOT_FOUND);
            }

            return JsonUtils.parseJson(response.getBody(), OAuth2UserInfoResponse.class);
        } catch (HttpClientErrorException e) {
            errorKey = ExceptionConstants.OAUTH2_USER_INFO_CLIENT_ERROR;
            log.error("Client error while get userinfo from Google: {}, {}", e.getStatusCode(), e.getMessage());
        } catch (HttpServerErrorException e) {
            errorKey = ExceptionConstants.OAUTH2_USER_INFO_SERVER_ERROR;
            log.error("Server error while get userinfo from Google: {}, {}", e.getStatusCode(), e.getMessage());
        } catch (Exception e) {
            errorKey = ExceptionConstants.OAUTH2_USER_INFO_EXCEPTION;
            log.error("Unexpected error occurred while get userinfo from Google: {}", e.getMessage());
        }

        throw new BaseBadRequestException(ENTITY_NAME, errorKey);
    }
}
