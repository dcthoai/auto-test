package vn.softdream.autotest.service.impl;

import vn.softdream.autotest.common.CredentialGenerator;
import vn.softdream.autotest.constants.ExceptionConstants;
import vn.softdream.autotest.constants.HttpStatusConstants;
import vn.softdream.autotest.constants.PropertiesConstants;
import vn.softdream.autotest.constants.ResultConstants;
import vn.softdream.autotest.dto.auth.BaseAuthTokenDTO;
import vn.softdream.autotest.dto.mapping.IAuthenticationDTO;
import vn.softdream.autotest.dto.request.CreateAccountRequestDTO;
import vn.softdream.autotest.dto.response.BaseResponseDTO;
import vn.softdream.autotest.entity.Account;
import vn.softdream.autotest.exception.BaseAuthenticationException;
import vn.softdream.autotest.repositories.AccountRepository;
import vn.softdream.autotest.security.jwt.JwtProvider;
import vn.softdream.autotest.security.model.OAuth2TokenResponse;
import vn.softdream.autotest.security.model.OAuth2UserInfoResponse;
import vn.softdream.autotest.security.service.GoogleOAuth2Service;
import vn.softdream.autotest.service.AccountService;
import vn.softdream.autotest.service.GoogleAuthenticationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

import static org.hibernate.id.IdentifierGenerator.ENTITY_NAME;

@Service
@ConditionalOnProperty(name = PropertiesConstants.OAUTH2_ACTIVE_STATUS, havingValue = "true")
public class GoogleAuthenticationServiceImpl implements GoogleAuthenticationService {

    private static final Logger log = LoggerFactory.getLogger(GoogleAuthenticationServiceImpl.class);
    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final JwtProvider tokenProvider;
    private final GoogleOAuth2Service googleOAuth2Service;

    public GoogleAuthenticationServiceImpl(AccountService accountService,
                                           AccountRepository accountRepository,
                                           JwtProvider tokenProvider,
                                           GoogleOAuth2Service googleOAuth2Service) {
        this.accountService = accountService;
        this.accountRepository = accountRepository;
        this.tokenProvider = tokenProvider;
        this.googleOAuth2Service = googleOAuth2Service;
    }

    @Override
    public BaseResponseDTO authorize(String code) {
        log.debug("Get authorization information from Google");

        if (!StringUtils.hasText(code))
            throw new BaseAuthenticationException(ENTITY_NAME, ExceptionConstants.BAD_CREDENTIALS);

        OAuth2TokenResponse tokenResponse = googleOAuth2Service.getAccessToken(code);
        OAuth2UserInfoResponse userInfo = googleOAuth2Service.getUserInfo(tokenResponse.getAccessToken());

        return authorize(userInfo);
    }

    @Override
    public BaseResponseDTO authorize(OAuth2UserInfoResponse userInfo) {
        log.debug("Authorize for user '{}'", userInfo.getEmail());
        Optional<IAuthenticationDTO> authentication = accountRepository.findAuthenticationByEmail(userInfo.getEmail());
        Account account = new Account();
        String username, password;

        if (authentication.isEmpty()) {
            username = CredentialGenerator.generateUsername(8);
            password = CredentialGenerator.generatePassword(8);
            log.debug("Authenticate for user '{}' via Google, no account yet", username);

            CreateAccountRequestDTO createAccountRequest = new CreateAccountRequestDTO();
            createAccountRequest.setUsername(username);
            createAccountRequest.setEmail(userInfo.getEmail());
            createAccountRequest.setPassword(password);

            account = accountService.createNewAccount(createAccountRequest);
        } else {
            BeanUtils.copyProperties(authentication.get(), account);
            username = account.getUsername();
        }

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, null);
        SecurityContextHolder.getContext().setAuthentication(token);
        BaseAuthTokenDTO authTokenDTO = BaseAuthTokenDTO.builder()
            .authentication(token)
            .userId(account.getId())
            .rememberMe(true)
            .build();

        log.debug("Authorize successful. Generating token for user '{}'", username);
        String jwtToken = tokenProvider.createToken(authTokenDTO);

        return BaseResponseDTO.builder()
            .code(HttpStatusConstants.ACCEPTED)
            .message(ResultConstants.LOGIN_SUCCESS)
            .success(HttpStatusConstants.STATUS.SUCCESS)
            .result(jwtToken)
            .build();
    }
}
