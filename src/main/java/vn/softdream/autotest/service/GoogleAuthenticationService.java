package vn.softdream.autotest.service;

import vn.softdream.autotest.dto.response.BaseResponseDTO;
import vn.softdream.autotest.security.model.OAuth2UserInfoResponse;

@SuppressWarnings("unused")
public interface GoogleAuthenticationService {

    BaseResponseDTO authorize(String code);
    BaseResponseDTO authorize(OAuth2UserInfoResponse userInfo);
}
