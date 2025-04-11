package vn.softdream.autotest.service;

import vn.softdream.autotest.dto.request.AuthRequestDTO;
import vn.softdream.autotest.dto.response.BaseResponseDTO;
import jakarta.servlet.http.Cookie;

public interface AuthenticationService {

    BaseResponseDTO authenticate(AuthRequestDTO authRequestDTO);
    Cookie createSecureCookie(String token, boolean isRememberMe);
}
