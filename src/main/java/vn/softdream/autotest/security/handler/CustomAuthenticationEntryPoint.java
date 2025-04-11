package vn.softdream.autotest.security.handler;

import vn.softdream.autotest.common.JsonUtils;
import vn.softdream.autotest.common.MessageUtils;
import vn.softdream.autotest.constants.ExceptionConstants;
import vn.softdream.autotest.constants.HttpStatusConstants;
import vn.softdream.autotest.dto.response.BaseResponseDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Handles unauthenticated requests in a Spring Security application,
 * such as providing valid credentials, handling expired credentials, and managing authentication exceptions
 *
 * @author thoaidc
 */
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final Logger log = LoggerFactory.getLogger(CustomAuthenticationEntryPoint.class);
    private final MessageUtils messageUtils;

    public CustomAuthenticationEntryPoint(MessageUtils messageUtils) {
        this.messageUtils = messageUtils;
        log.debug("AuthenticationEntryPoint 'CustomAuthenticationEntryPoint' is configured for use");
    }

    /**
     * Directly responds to the client in case of authentication errors without passing the request to further filters <p>
     * In this case, a custom JSON response is sent back <p>
     * You can add additional business logic here, such as sending a redirect or logging failed login attempts, etc.
     *
     * @param request that resulted in an <code>AuthenticationException</code>
     * @param response so that the user agent can begin authentication
     * @param authException that caused the invocation
     */
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        log.error("Authentication entry point is active. {}: {}", authException.getMessage(), request.getRequestURL());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE); // Convert response body to JSON
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setStatus(HttpStatusConstants.UNAUTHORIZED);

        BaseResponseDTO responseDTO = BaseResponseDTO.builder()
            .code(HttpStatusConstants.UNAUTHORIZED)
            .success(HttpStatusConstants.STATUS.FAILED)
            .message(messageUtils.getMessageI18n(ExceptionConstants.UNAUTHORIZED))
            .build();

        response.getWriter().write(JsonUtils.toJsonString(responseDTO));
        response.flushBuffer();
    }
}
