package vn.softdream.autotest.web.rest.common;

import vn.softdream.autotest.constants.ExceptionConstants;
import vn.softdream.autotest.constants.HttpStatusConstants;
import vn.softdream.autotest.constants.ResultConstants;
import vn.softdream.autotest.dto.request.AuthRequestDTO;
import vn.softdream.autotest.dto.request.CreateAccountRequestDTO;
import vn.softdream.autotest.dto.request.RegisterRequestDTO;
import vn.softdream.autotest.dto.response.BaseResponseDTO;
import vn.softdream.autotest.entity.Account;
import vn.softdream.autotest.exception.BaseBadRequestException;
import vn.softdream.autotest.service.AccountService;
import vn.softdream.autotest.service.AuthenticationService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/api/p/common/auth")
public class AuthResource {

    private final AuthenticationService authService;
    private final AccountService accountService;
    private static final String ENTITY_NAME = "AuthResource";

    public AuthResource(AuthenticationService authService, AccountService accountService) {
        this.authService = authService;
        this.accountService = accountService;
    }

    @PostMapping("/register")
    public BaseResponseDTO register(@Valid @RequestBody RegisterRequestDTO requestDTO) {
        CreateAccountRequestDTO accountRequestDTO = new CreateAccountRequestDTO();
        accountRequestDTO.setUsername(requestDTO.getUsername());
        accountRequestDTO.setPassword(requestDTO.getPassword());
        accountRequestDTO.setPassword(requestDTO.getEmail());
        Account account = accountService.createNewAccount(accountRequestDTO);

        if (Objects.isNull(account) || Objects.isNull(account.getId()) || account.getId() < 1)
            throw new BaseBadRequestException(ENTITY_NAME, ExceptionConstants.REGISTER_FAILED);

        return BaseResponseDTO.builder()
            .code(HttpStatusConstants.CREATED)
            .success(HttpStatusConstants.STATUS.SUCCESS)
            .message(ResultConstants.REGISTER_SUCCESS)
            .result(account)
            .build();
    }

    @PostMapping("/login")
    public BaseResponseDTO login(@Valid @RequestBody AuthRequestDTO requestDTO, HttpServletResponse response) {
        BaseResponseDTO responseDTO = authService.authenticate(requestDTO);
        String jwt = (String) responseDTO.getResult();
        Cookie secureCookie = authService.createSecureCookie(jwt, requestDTO.getRememberMe());

        response.addCookie(secureCookie); // Send secure cookie with token to client in HttpOnly
        responseDTO.setResult(null); // Clear token in response body

        return responseDTO;
    }

    @PostMapping("/logout")
    public BaseResponseDTO logout(HttpServletResponse response) {
        SecurityContextHolder.getContext().setAuthentication(null);

        // Create cookie with token is null
        Cookie secureCookie = authService.createSecureCookie(null, false);
        secureCookie.setMaxAge(0); // Delete cookies immediately
        response.addCookie(secureCookie); // Send new cookie to client to overwrite old cookie

        return BaseResponseDTO.builder().ok();
    }
}
